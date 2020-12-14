package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Java2DFrameUtils;

public class T {

    public static void main(String[] args) throws IOException, Exception {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber("F:/Resource/c.mp4");
        g.start();
        for (int i = 0; i < 50; i++) {
            BufferedImage bufferedImage = Java2DFrameUtils.toBufferedImage(g.grab());
            File outputfile = new File("image.jpg");
            ImageIO.write(bufferedImage, "jpg", outputfile);
        }
        g.stop();
    }
}
