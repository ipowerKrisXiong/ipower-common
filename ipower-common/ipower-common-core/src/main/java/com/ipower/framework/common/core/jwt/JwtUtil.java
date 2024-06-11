package com.ipower.framework.common.core.jwt;

import com.ipower.framework.common.core.session.JwtUser;
import com.ipower.framework.common.core.session.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * jwt工具类
 * </p>
 *
 * @author hcs
 */
public class JwtUtil {

    /**
     * 生成jwt token
     *
     * @param jwtUser   jwt用户信息负载数据
     * @param jwtSecret 密钥
     * @return 生成的token
     */
    public static String generateToken(JwtUser jwtUser, String jwtSecret) {
        return Jwts.builder().subject(jwtUser.getUsername())
            .claim(Users.KEY_USER, jwtUser)
            .claim(Users.KEY_USER_ID, jwtUser.getId())
            .claim(Users.KEY_NAME, jwtUser.getName())
            .issuedAt(DateTime.now().toDate())
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    /**
     * 解析jwt，获取负载数据
     *
     * @param jwt       jwt
     * @param jwtSecret 密钥
     * @return Claims
     */
    public static Claims parseToken(String jwt, String jwtSecret) {
        return Jwts.parser()
            .json(new JacksonDeserializer(Maps.of(Users.KEY_USER, JwtUser.class).build()))
            .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
    }

}
