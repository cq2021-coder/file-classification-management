package com.cq.lovehwh;

import com.cq.hwh.util.CaptchaGenerator;
import com.cq.hwh.util.SnowFlake;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaptchaGeneratorTest {

    public static void main(String[] args) throws IOException {
        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        String vCode = captchaGenerator.generatorVCode();
        BufferedImage bufferedImage = captchaGenerator.generatorVCodeImage(vCode, true);
        File file = new File("D:\\web学习\\"+ new SnowFlake().nextId() +".png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
