package org.example.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

public class JavacvTest {

    /**
     * 获取视频时长，单位为秒
     *
     * @param video 源视频文件
     * @return 时长（s）
     */
    public static long getVideoDuration(File video) {
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    /**
     * 截取视频获得指定帧的图片
     *
     * @param video 源视频文件
     * @param picPath 截图存放路径
     */
    public static void getVideoPic(File video, String picPath) throws IOException {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();

            // 跳过前x帧
            int i = 0;
            int length = ff.getLengthInFrames();
            int skipFrames = 5;
            Frame frame = null;
            while (i < length) {
                frame = ff.grabFrame();
                if ((i > skipFrames) && (frame.image != null)) {
                    break;
                }
                i++;
            }

            // 视频的宽高
            System.out.println(String.format("Width: %s, Height: %s", ff.getImageWidth(), ff.getImageHeight()));

            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcImage = converter.getBufferedImage(frame);
            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();

            // 对截图进行等比例缩放(缩略图)
            int width = 800;
            int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
            BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,
                    null);

            File picFile = new File(picPath);
            ImageIO.write(thumbnailImage, "jpg", picFile);

            ff.stop();
        } finally {
            try {
                ff.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String userHome = SystemUtils.getUserHome().getAbsolutePath();
            File videofile = Paths.get(userHome, "Downloads", "xiaodu_1_mask_05_28.mp4").toFile();
            long duration = getVideoDuration(videofile);
            System.out.println("时长: " + duration + "秒");

            File imagefile = new File(videofile.getAbsolutePath() + "." + System.currentTimeMillis() + ".jpg");

            getVideoPic(videofile, imagefile.getAbsolutePath());

            java.awt.Desktop.getDesktop().open(imagefile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
