package com.cq.hwh.util;

import com.cq.hwh.domain.User;
import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * 令牌工具包
 *
 * @author 程崎
 * @date 2021/06/26
 */
public class TokenUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TokenUtil.class);

    public static String signToken(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("name",user.getName());
        claims.put("loginName",user.getLoginName());

        long now = System.currentTimeMillis();
        LOG.info("当前时间的时间戳{}",now);
        //设置一天后过期
        long expirationTime = now + 24*3600*1000;
        String format = new SimpleDateFormat("yyyy年MM月dd日   HH时mm分ss秒", Locale.CHINA).format(expirationTime);
        LOG.info("过期日期为：{}",format);

        return String.valueOf(Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expirationTime))
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256,"kejhfiwjkey87%&^GH*&UR%BJH")
                .compact()
        );
    }
    public static Boolean verifyToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey("kejhfiwjkey87%&^GH*&UR%BJH")
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 可根据request中请求头设置的token得到此时登录的用户id
     * @param request
     * @return
     */
    public static Object getUserByToken(HttpServletRequest request){
        String token = request.getHeader("token");
        Object userId;
        try {
            Claims claims1 = Jwts.parser()
                    .setSigningKey("kejhfiwjkey87%&^GH*&UR%BJH")
                    .parseClaimsJws(token)
                    .getBody();
            userId = claims1.get("id");
        }catch (Exception e){
            throw new BusinessException(BusinessExceptionCode.TOKEN_VERIFY_ERROR);
        }
        return userId;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(122112L);
        user.setLoginName("cq");
        user.setName("程崎");
        System.out.println("token为："+signToken(user));

        System.out.println(verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbk5hbWUiOiJjcSIsIm5hbWUiOiLnqIvltI4iLCJpZCI6NjI4MTAyMTc3Nzk0MywiZXhwIjoxNjI0NTQzMDMxLCJpYXQiOjE2MjQ1NDI5NDV9.xEF9_OfCSVvYUkqh75XABwNYMjugk80TK-xnNDFHR5Q"));
    }

}
