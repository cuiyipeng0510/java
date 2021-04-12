package com.cuiyp.demo.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class InputStreamTest {
    private static final Logger log = LoggerFactory.getLogger(InputStreamTest.class);

    public static void main(String[] args) throws Exception {
        InputStream fis = new FileInputStream("");
//        BufferedImage read = ImageIO.read(new ByteArrayInputStream(imageByte));
//        FileUtils.
    }

    public static byte[] getImageByte(String str1, String str2, int x) throws IOException {
        /*
         * 1. 创建图片缓冲区
         * 2. 设置其宽高
         * 3. 得到这个图片的绘制环境（得到画笔）
         * 4. 添加字符
         */
        int xcoord;
        switch (x) {
            case 2:
                xcoord = 15;
                break;
            case 3:
                xcoord = 8;
                break;
            case 4:
                xcoord = 2;
                break;
            default:
                xcoord = 10;
        }
        BufferedImage bi = new BufferedImage(130, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();//得到绘制环境
        g.setColor(Color.white);//把环境设置为白色
        g.fillRect(0, 0, 130, 40);//填充矩形！填充矩形，从0,0点开始
        g.setColor(new Color(87, 174, 248));
        g.setFont(new Font("宋体", Font.BOLD, 12));
        g.drawString(str1, 20, 40 - 25);//向图片上写入字符串
        g.drawString(str2, xcoord, 40 - 8);

        // 设置边框颜色
        g.drawRect(0, 0, 130 - 1, 40 - 1);
        g.drawRect(1, 1, 130 - 1, 40 - 1);
        g.drawRect(0, 0, 130-2, 40- 2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "JPEG", baos);
        return transferImageBackground(baos.toByteArray());
    }

    public static byte[] transferImageBackground(byte[] byteArray) {
        try (InputStream is = new ByteArrayInputStream(byteArray); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            BufferedImage read = ImageIO.read(is);
            ImageIcon imageIcon = new ImageIcon(read);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            imageSetRGB(bufferedImage);
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            //转换成byte数组
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("图像透明化处理失败", e);
        }
        return byteArray;
    }

    /**
     * 设置图片背景
     * @param bufferedImage bufferedImage
     */
    private static void imageSetRGB(BufferedImage bufferedImage) {
        for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
            for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                int rgb = bufferedImage.getRGB(j2, j1);
                int r = (rgb & 0xff0000) >> 16;
                int g = (rgb & 0xff00) >> 8;
                int b = (rgb & 0xff);
                if (((255 - r) < 30) && ((255 - g) < 30) && ((255 - b) < 30)) {
                    rgb = (1 << 24) | (rgb & 0x00ffffff);
                }
                bufferedImage.setRGB(j2, j1, rgb);
            }
        }
    }

}
