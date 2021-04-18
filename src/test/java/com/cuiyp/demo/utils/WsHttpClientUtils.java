package com.cuiyp.demo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName WsHttpClientUtils
 * @Description 文书http请求工具,扩展了patch,put,delete请求
 * @Author wuyafu
 * @Date 2018-11-15 11:23
 * @Version 1.0
 **/
public class WsHttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(WsHttpClientUtils.class);

    private static final int DEFAULT_SO_TIMEOUT = 200000;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 100000;

    private WsHttpClientUtils() {}

    public static String get(String uri) throws IOException {
        return get(uri, null);
    }

    public static String get(String uri, Map<String, Object> params) throws IOException {
        return get(uri, params, null);
    }

    public static String get(String uri, Map<String, Object> params,
                             Map<String, String> headers) throws IOException{
        HttpClient client = getHttpClient();
        return get(client, uri, params, headers);
    }

    public static InputStream getInputStream(String uri) throws IOException{
        return getInputStream(uri, null);
    }

    public static InputStream getInputStream(String uri, Map<String, Object> params) throws IOException {
        return getInputStream(uri, params, null);
    }

    public static InputStream getInputStream(String uri, Map<String, Object> params,
                                             Map<String, String> headers) throws IOException {
        HttpClient client = getHttpClient();
        return getInputStream(client, uri, params, headers);
    }

    public static InputStream getInputStream(HttpClient client, String uri,
                                             Map<String, Object> params, Map<String, String> headers) throws IOException {
        String fullUrl = buildUrlWithParams(uri, params);
        HttpGet httpGet = new HttpGet(fullUrl);

        addHeader(headers, httpGet);
        HttpResponse httpResponse = client.execute(httpGet);
        return httpResponse.getEntity().getContent();
    }

    private static void addHeader(Map<String, String> headers, HttpRequestBase httpRequest) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpRequest.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public static String get(HttpClient client, String uri,
                             Map<String, Object> params, Map<String, String> headers) throws IOException {
        String fullUrl = buildUrlWithParams(uri, params);
        HttpGet httpGet = new HttpGet(fullUrl);

        addHeader(headers, httpGet);
        HttpResponse httpResponse = client.execute(httpGet);
        return getResult(uri, httpResponse);
    }

    /**
     * 获取文件流
     * @param client
     * @param uri
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    public static byte[] getByte(HttpClient client, String uri,
                                 Map<String, Object> params, Map<String, String> headers) throws IOException{
        String fullUrl = buildUrlWithParams(uri, params);
        HttpGet httpGet = new HttpGet(fullUrl);
        addHeader(headers, httpGet);
        HttpResponse httpResponse = client.execute(httpGet);
        return getByteResult(uri, httpResponse);
    }

    public static String post(String uri) throws IOException{
        return post(uri, null);
    }

    public static String post(String uri, Map<String, Object> params) throws IOException {
        return post(uri, params, null, null);
    }

    public static String post(String uri, Map<String, Object> params,
                              Map<String, String> headers) throws IOException{
        return post(uri, params, headers, null);
    }

    public static String post(String uri, Map<String, Object> params,
                              ContentType contentType) throws IOException{
        return post(uri, params, null, contentType);
    }

    public static String post(String uri, Map<String, Object> params,
                              Map<String, String> headers, ContentType contentType) throws IOException {
        HttpClient client = getHttpClient();
        return post(client, uri, params, headers, contentType);
    }

    public static String post(HttpClient client, String uri,
                              Map<String, Object> params, Map<String, String> headers,
                              ContentType contentType) throws IOException{
        HttpPost httpPost = new HttpPost(uri);
        addParams(params, contentType, httpPost);
        addHeader(headers, httpPost);
        HttpResponse httpResponse = client.execute(httpPost);
        return getResult(uri, httpResponse);
    }

    public static InputStream getInputStreamByPostJson(String uri) throws IOException {
        return getInputStreamByPostJson(uri, null);
    }

    public static InputStream getInputStreamByPostJson(String uri, Object params) throws IOException {
        return getInputStreamByPostJson(uri, params, null);
    }

    public static InputStream getInputStreamByPostJson(String uri, Object params,
                                                   Map<String, String> headers) throws IOException {
        HttpClient client = getHttpClient();
        return getInputStreamByPostJson(client, uri, params, headers);
    }

    public static InputStream getInputStreamByPostJson(HttpClient client, String uri,
                                                   Object params, Map<String, String> headers) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (null != params) {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON));
        }
        addHeader(headers, httpPost);

        HttpResponse httpResponse = client.execute(httpPost);
        return httpResponse.getEntity().getContent();
    }

    /**
     * 文件转换格式，请求头添加文件流
     * 获取文件流
     * @param client
     * @param uri
     * @param params
     * @param headers
     * @param data
     */
    public static String post(HttpClient client, String uri,
                              Map<String, Object> params, Map<String, String> headers,
                              byte[] data) throws IOException{
        String url = buildUrlWithParams(uri, params);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(data)));
        addHeader(headers, httpPost);
        HttpResponse httpResponse = client.execute(httpPost);
        return getResult(uri, httpResponse);
    }

    private static void addParams(Map<String, Object> params, ContentType contentType,
                                  HttpEntityEnclosingRequestBase httpRequest) {
        if (null != params && !params.isEmpty()) {
            if (contentType == null) {
                contentType = ContentType.APPLICATION_FORM_URLENCODED;
            }
            if (ContentType.APPLICATION_JSON.equals(contentType)) {
                httpRequest.setEntity(new StringEntity(JSON.toJSONString(params), contentType));
            } else if (ContentType.APPLICATION_FORM_URLENCODED
                    .equals(contentType)) {
                setUrlEncodedFormEntity(params, httpRequest);
            }
        }
    }

    public static String postJSON(String uri, Object params, Map<String, String> headers)
            throws IOException{
        HttpPost httpPost = new HttpPost(uri);
        if (null != params) {
            httpPost.setEntity(new StringEntity(JSON
                    .toJSONString(params), ContentType.APPLICATION_JSON));
        }
        addHeader(headers, httpPost);
        HttpResponse httpResponse = getHttpClient().execute(httpPost);
        return getResult(uri, httpResponse);
    }

    private static String buildUrlWithParams(String uri,
                                             Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(uri);
        if (null != params && !params.isEmpty()) {
            if (!uri.contains("?")) {
                urlBuilder.append("?");
            }
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String valueStr = null == value ? "" : value.toString();
                if (!urlBuilder.toString().endsWith("?")) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(key).append("=")
                        .append(URLEncoder.encode(valueStr, StandardCharsets.UTF_8.name()));
            }
        }
        return urlBuilder.toString();
    }

    public static HttpClient getHttpClient() {
        return getHttpClient(DEFAULT_SO_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
    }

    public static HttpClient getHttpClient(int soTimeout, int connectionTimeout) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BEST_MATCH);
        HttpParams myHttpParams =client.getParams();
        HttpConnectionParams.setConnectionTimeout(myHttpParams,
                connectionTimeout);
        HttpConnectionParams.setSoTimeout(myHttpParams, soTimeout);
        return client;
    }

    public static String patch(String uri) throws IOException{
        return patch(uri, null);
    }

    public static String patch(String uri, Map<String, Object> params) throws IOException
             {
        return patch(uri, params, null, null);
    }

    public static String patch(String uri, Map<String, Object> params, Map<String, String> headers)
            throws IOException{
        return patch(uri, params, headers, null);
    }

    public static String patch(String uri, Map<String, Object> params, ContentType contentType)
            throws IOException{
        return patch(uri, params, null, contentType);
    }

    public static String patch(String uri, Map<String, Object> params, Map<String, String> headers, ContentType contentType)
            throws IOException{
        HttpClient client = getHttpClient();
        return patch(client, uri, params, headers, contentType);
    }

    public static String patch(HttpClient client, String uri, Map<String, Object> params,
                               Map<String, String> headers, ContentType contentType)
            throws IOException{
        HttpPatch httpPatch = new HttpPatch(uri);
        addParams(params, contentType, httpPatch);
        addHeader(headers, httpPatch);

        HttpResponse httpResponse = client.execute(httpPatch);
        return getResult(uri, httpResponse);

    }

    public static String patchJSON(String uri, Object params, Map<String, String> headers)
            throws IOException{
        HttpPatch httpPatch = new HttpPatch(uri);
        if (null != params) {
            httpPatch.setEntity(new StringEntity(JSON
                    .toJSONString(params), ContentType.APPLICATION_JSON));
        }
        addHeader(headers, httpPatch);
        HttpResponse httpResponse = getHttpClient().execute(httpPatch);
        return getResult(uri, httpResponse);
    }

    public static String put(String uri) throws IOException{
        return put(uri, null);
    }

    public static String put(String uri, Map<String, Object> params) throws IOException{
        return put(uri, params, null, null);
    }

    public static String put(String uri, Map<String, Object> params, Map<String, String> headers)
            throws IOException{
        return put(uri, params, headers, null);
    }

    public static String put(String uri, Map<String, Object> params, ContentType contentType)
            throws IOException{
        return put(uri, params, null, contentType);
    }

    public static String put(String uri, Map<String, Object> params, Map<String, String> headers, ContentType contentType)
            throws IOException{
        HttpClient client = getHttpClient();
        return put(client, uri, params, headers, contentType);
    }

    public static String put(HttpClient client, String uri, Map<String, Object> params, Map<String, String> headers, ContentType contentType)
            throws IOException{
        HttpPut httpPut = new HttpPut(uri);
        addParams(params, contentType, httpPut);
        addHeader(headers, httpPut);
        HttpResponse httpResponse = client.execute(httpPut);
        return getResult(uri, httpResponse);

    }

    public static String delete(String uri) throws IOException{
        return delete(uri, null);
    }

    public static String delete(String uri, Map<String, Object> params)
            throws IOException{
        return delete(uri, params, null);
    }

    public static String delete(String uri, Map<String, Object> params, Map<String, String> headers)
            throws IOException{
        HttpClient client = getHttpClient();
        return delete(client, uri, params, headers);
    }

    public static String delete(HttpClient client, String uri, Map<String, Object> params, Map<String, String> headers)
            throws IOException{
        String fullUrl = buildUrlWithParams(uri, params);
        HttpDelete httpDelete = new HttpDelete(fullUrl);

        addHeader(headers, httpDelete);

        HttpResponse httpResponse = client.execute(httpDelete);
        return getResult(uri, httpResponse);

    }

    private static String getResult(String uri, HttpResponse httpResponse)
            throws IOException {
        String result = StringUtils.EMPTY;
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode == HttpStatus.SC_NO_CONTENT){
            return StringUtils.EMPTY;
        }
        InputStream input = httpResponse.getEntity().getContent();
        if (null != input) {
            try {
                result = IOUtils.toString(input, "UTF-8");
            } catch (IOException e) {
                logger.error("调用http接口【[{}]】出错!", uri, e);
                throw e;
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
        assertStatus(uri, statusCode, result);
        return result;
    }

    /**
     *
     * @param uri
     * @param httpResponse
     * @return
     * @throws IOException
     */
    private static byte[] getByteResult(String uri, HttpResponse httpResponse) throws IOException {
        byte[] result = null;
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode == HttpStatus.SC_NO_CONTENT){
            return new byte[0];
        }
        InputStream input = httpResponse.getEntity().getContent();
        if (null != input) {
            try {
                result = IOUtils.toByteArray(input);
            } catch (IOException e) {
                logger.error("提取文件流【[{}]】出错!", uri, e);
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
        return result;
    }

    private static void assertStatus(String uri, int statusCode, String result)  {
        int codePre = statusCode / 100;
//        switch (codePre) {
//            case 2:
//                break;
//            case 4:
//                if(statusCode == HttpStatus.SC_METHOD_NOT_ALLOWED){
//                    throw new MethodNotSupportedException(result);
//                }
//                throw new RequestClientException(result, uri, statusCode);
//            case 5:
//                throw new RequestServerException(result, uri, statusCode);
//            default:
//                throw new RequestException(result, uri, statusCode);
//        }
    }

    private static void setUrlEncodedFormEntity(Map<String, Object> params, HttpEntityEnclosingRequestBase httpEntity) {
        List<BasicNameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            String valueStr = null;
            if(value != null){
                valueStr = value.toString();
            }
            paramList.add(new BasicNameValuePair(entry.getKey(), valueStr));
        }
        httpEntity.setEntity(new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8));
    }

    /**
     * post Multipart数据，返回String
     * @param url 链接
     * @param params 参数
     * @param nameIsMap nameIsMap
     * @return 响应
     * @throws IOException io异常
     * @throws MethodNotSupportedException 未知方法
     */
    public static String postMultipartResultForString(String url, Map<String, String> params,
                                                      Map<String, String> headers, Map<String, File> nameIsMap)
            throws IOException{
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = getHttpPostByMultiFile(nameIsMap, url, params, headers);
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                return getResult(url, response);
            }
        }
    }

    /**
     * @param file 文件
     * @param url 地址
     * @param params 其他参数
     * @return HttpPost
     * @throws IOException io异常
     */
    private static HttpPost getHttpPostByOneFile(File file, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("file", file);
        return getHttpPostByMultiFile(fileMap, url, params, headers);
    }

    /**
     * @param fileMap fileMap
     * @param url 地址
     * @param params 其他参数
     * @return HttpPost
     * @throws IOException io异常
     */
    private static HttpPost getHttpPostByMultiFile(Map<String, File> fileMap, String url, Map<String, String> params,
                                        Map<String, String> headers) throws IOException {
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setCharset(StandardCharsets.UTF_8)
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for(Map.Entry<String, File> entry : fileMap.entrySet()){
            builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
        }
        return getHttpPost(params, headers, httppost, builder);
    }

    private static HttpPost getHttpPost(Map<String, String> params, Map<String, String> headers, HttpPost httppost, MultipartEntityBuilder builder) throws UnsupportedEncodingException {
        if(params!=null){
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for(Map.Entry<String, String> entry : entries){
                if(entry.getValue()==null){
                    continue;
                }
                StringBody value = new StringBody(entry.getValue(),  "text/plain", StandardCharsets.UTF_8);
                builder.addPart(entry.getKey(), value);
            }
        }
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);
        if(headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httppost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return httppost;
    }

    /**
     * post请求，获取数据
     * @param uri url
     * @param params  参数
     * @param headers 请求头
     * @return  响应的数据流
     * @throws IOException 异常
     */
    public static InputStream getInputStreamUsePost(String uri, Map<String, Object> params,
                                                    Map<String, String> headers) throws IOException {
        HttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(uri);
        if (null != params) {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON));
        }
        addHeader(headers, httpPost);
        HttpResponse httpResponse = client.execute(httpPost);
        return httpResponse.getEntity().getContent();
    }
}
