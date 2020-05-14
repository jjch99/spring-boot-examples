package org.example.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageTest {

    public static BufferedImage makeIcon(File src, File icon, int x, int y, float alpha) throws IOException {
        // 图标模板
        BufferedImage iconImg = ImageIO.read(icon);
        int iconImgWidth = iconImg.getWidth();
        int iconImgHeight = iconImg.getHeight();

        // 原图，先缩放到图标模板大小
        BufferedImage srcImg = ImageIO.read(src);
        Image scaledSrcImg = srcImg.getScaledInstance(iconImgWidth, iconImgHeight, Image.SCALE_SMOOTH);

        // 再合并图片
        Graphics2D g2d = iconImg.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2d.drawImage(scaledSrcImg, x, y, iconImgWidth, iconImgHeight, null);
        g2d.dispose();

        return iconImg;
    }

    public static void writeImageFile(BufferedImage image, String path) {
        int index = path.lastIndexOf(".") + 1;
        try {
            ImageIO.write(image, path.substring(index), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMakeIcon() throws IOException {
        String home = System.getenv("HOME");
        String base = home + "/Downloads/";
        // String sourceFilePath = base + "ee393642-36bf-6191-018d-0b46cb16d154.jpg";
        String sourceFilePath = base + "test1.jpg";
        String iconFilePath = base + "launchpad_icon_shape.png";
        String outputFilePath = base + "test1_out.png";

        BufferedImage logoImg = ImageTest.makeIcon(new File(sourceFilePath), new File(iconFilePath), 0, 0, 1.0f);
        ImageTest.writeImageFile(logoImg, outputFilePath);
    }

}
