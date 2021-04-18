package com.cuiyp.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpClient
 *
 * @author renxb
 * @version 1.0
 */
@Slf4j
public final class HttpClient implements Closeable {
    /**
     * 从连接池中获取连接的默认超时时间
     */
    private static final int TIMEOUT = 1000;

    /**
     * 建立连接的默认超时时间
     */
    private static final int CONNECT_TIMEOUT = 10 * 1000;

    /**
     * 读取数据的默认超时时间（大材料经常超时，暂时先改大点20min）
     */
//    private static final int SOCKET_TIMEOUT = 20 * 1000;
    private static final int SOCKET_TIMEOUT = 1200 * 1000;

    private static final int MAX_PER_ROUTE = 100;

    private static final int MAX_TOTAL = 1000;

    private static final CloseableHttpClient client;

    private static final int STATUS_OK = 200;

    private CloseableHttpResponse response;
    private HttpEntity entity;

    private static final int CONN_TIME_TO_LIVE = 5;

    static {
        // request请求相关配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                //连接超时时间
                .setConnectTimeout(CONNECT_TIMEOUT)
                //读超时时间（等待数据超时时间）
                .setSocketTimeout(SOCKET_TIMEOUT)
                //从池中获取连接超时时间
                .setConnectionRequestTimeout(TIMEOUT)
                .build();

        // 创建httpClient
        client = HttpClients.custom()
                // 默认请求配置
                .setDefaultRequestConfig(defaultRequestConfig)
                .setMaxConnPerRoute(MAX_PER_ROUTE)
                .setMaxConnTotal(MAX_TOTAL)
                .setConnectionTimeToLive(CONN_TIME_TO_LIVE, TimeUnit.MINUTES)
                .build();
    }

    private StatusLine statusLine;

    /**
     * 处理url中空格
     * @param url url
     * @return String
     */
    private String dealSpace(String url){
        return StringUtils.replace(url, " ", "%20");
    }
    /**
     * 获取新的实例
     *
     * @return 新的实例
     */
    public static HttpClient newInstance() {
        return new HttpClient();
    }

    /**
     * 发送get请求 请注意手动调用closeResponse 方法释放httpClient连接资源
     *
     * @param url url地址
     * @return 当前HttpClient
     * @throws IOException 异常啊
     */
    public HttpClient get(String url) throws IOException {
        execute(new HttpGet(dealSpace(url)));
        return this;
    }

    /**
     * 发送get请求 请注意手动调用closeResponse 方法释放httpClient连接资源
     *
     * @param url     url地址
     * @param headers header信息
     * @return 当前HttpClient
     * @throws IOException 异常啊
     */
    public HttpClient get(String url, Map<String, String> headers) throws IOException {
        HttpGet request = new HttpGet(dealSpace(url));
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(request::addHeader);
        }
        execute(request);
        return this;
    }
    /**
     * 发送post请求 请注意手动调用closeResponse 方法释放httpClient连接资源
     *
     * @param url url地址
     * @return 当前HttpClient
     * @throws IOException 异常啊
     */
    public HttpClient post(String url) throws IOException {
        execute(new HttpPost(url));
        return this;
    }
    /**
     * 发送post请求数据,请注意手动调用closeResponse 方法释放httpClient连接资源
     *
     * @param url  url地址
     * @param json json数据
     * @return 当前HttpClient
     * @throws IOException io异常
     */
    public HttpClient postJson(String url, String json) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        execute(post);
        return this;
    }

    /**
     * 发送post请求数据,携带header
     *
     * @param url        url地址
     * @param json       json数据
     * @param headerList header
     * @return 当前HttpClient
     * @throws IOException io异常
     */
    public HttpClient postJsonWithHeader(String url, String json, List<Header> headerList) throws IOException {
        HttpPost post = new HttpPost(url);
        for (Header header : headerList) {
            post.addHeader(header);
        }
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        execute(post);
        return this;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public int statusCode() {
        return this.statusLine.getStatusCode();
    }

    /**
     * 是否返回 200 ok 状态码
     *
     * @return 是否返回 200 ok 状态码
     */
    public boolean isOk() {
        return STATUS_OK == statusLine.getStatusCode();
    }


    /**
     * 获取响应内容
     *
     * @return 响应内容
     * @throws IOException io异常
     */
    public String contentUtf8Str() throws IOException {
        try (InputStream input = response.getEntity().getContent()) {
            return IOUtils.toString(input, StandardCharsets.UTF_8);
        }
    }

    /**
     * 获取响应内容
     *
     * @return 响应内容二进制流
     * @throws IOException io异常
     */
    public byte[] getByte() throws IOException {
        try (InputStream input = response.getEntity().getContent()) {
            return IOUtils.toByteArray(input);
        }
    }

    /**
     * 关闭 response
     */
    public void closeResponse() {
        this.close();
    }

    private void execute(HttpRequestBase post) throws IOException {
        response = client.execute(post);
        entity = response.getEntity();
        statusLine = response.getStatusLine();
    }

    @Override
    public void close() {
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            log.error("", e);
        }
        entity = null;
        response = null;
    }
}
