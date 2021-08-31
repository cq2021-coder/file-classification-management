package com.cq.hwh.controller;

import com.alibaba.fastjson.JSONObject;
import com.cq.hwh.util.CaptchaGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Api(tags = "验证码")
@RestController
@RequestMapping("/check")
public class CheckCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(CheckCodeController.class);

    @ApiOperation("生成验证码")
    @GetMapping("/getCheckCode")
    public void getCheckCode(HttpServletResponse response) throws IOException {

        JSONObject object = new JSONObject();

        CaptchaGenerator vcg = new CaptchaGenerator();

        String vCode = vcg.generatorVCode();

        LOG.info("验证码为：{}", vCode);

        BufferedImage vCodeImage = vcg.generatorRotateVCodeImage(vCode, true);

        response.setDateHeader("Expires", 0);

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(vCodeImage, "png", outputStream);

            BASE64Encoder encoder = new BASE64Encoder();

            String base64 = encoder.encodeBuffer(outputStream.toByteArray()).trim();

            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");

            object.put("codeImg", "data:image/jpg;base64," + base64);

            object.put("code", vCode);

            response.getWriter().write(object.toString());

        } catch (IOException e) {
            response.getWriter().write("");

        } finally {
            outputStream.flush();

            outputStream.close();

            response.getWriter().close();

        }

        //region 旧代码
        /*CheckCode checkCode = new CheckCode();

        CaptchaGenerator vcg = new CaptchaGenerator();

        String vCode = vcg.generatorVCode();

        checkCode.setCode(vCode);

        LOG.info("生成的验证码是：{}", vCode);

        BufferedImage vCodeImage = vcg.generatorVCodeImage(vCode, true);

        response.setDateHeader("Expires", 0);

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache");

        OutputStream outputStream = response.getOutputStream();

        ImageIO.write(vCodeImage, "png", outputStream);

        checkCode.setOutputStream(outputStream);

        return checkCode;*/
        //endregion
    }

    /*@ApiOperation("验证验证码")
    @GetMapping("/verifyCheckCode")
    public Boolean verifyCheckCode(HttpServletRequest request, String code) {
        String vCode = null;
        System.out.println(Arrays.toString(request.getCookies()));
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("code")) {
                vCode = cookie.getValue();
            }
        }

        assert vCode != null;
        return Objects.equals(code, vCode.toLowerCase());
    }*/

}