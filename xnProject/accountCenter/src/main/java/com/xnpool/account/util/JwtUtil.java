package com.xnpool.account.util;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */
@Component
public class JwtUtil {
/*

    private static UserRepository userRepository;

    @Autowired
    public JwtUtil(UserRepository userRepository) {
        JwtUtil.userRepository = userRepository;
    }
*/




    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 hour
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static String generateToken(Integer id,String username,Date generateTime) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("id",id);
        map.put("username", username);
        map.put("generateTime",generateTime);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(generateTime.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt;
    }

    /**
     *
     * @param token
     * @return
     */
    public static Map<String,Object> validateToken(String token) {
        Map<String,Object> resp = new HashMap<String,Object>();
        if (token != null) {
            // parse the token.

            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                Integer id = (Integer) body.get("id");
                String username = (String) (body.get("username"));
                Date generateTime = new Date((Long)body.get("generateTime"));



                if(username == null || username.isEmpty()){
                    resp.put("ERR_MSG",Constants.ERR_MSG_USERNAME_EMPTY);
                    return resp;
                }
               /* //账号在别处登录
                if(userRepository.findByUsername(username).getLastLoginTime().after(generateTime)){
                    resp.put("ERR_MSG",Constants.ERR_MSG_LOGIN_DOU);
                    return resp;
                }*/
                resp.put("id",id);
                resp.put("username",username);
                resp.put("generateTime",generateTime);

                return resp;
            }catch (SignatureException | MalformedJwtException e) {
                // TODO: handle exception
                // don't trust the JWT!
                // jwt 解析错误
                resp.put("ERR_MSG",Constants.ERR_MSG_TOKEN_ERR);
                return resp;

            } catch (ExpiredJwtException e) {
                // TODO: handle exception
                // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
                resp.put("ERR_MSG",Constants.ERR_MSG_TOKEN_EXP);
                return resp;
            }

        }else {
            resp.put("ERR_MSG",Constants.ERR_MSG_TOKEN_EMPTY);
            return resp;
        }



    }

    public static void main(String[] args) {
        String string = JwtUtil.generateToken(1,"test", new Date());
        //System.err.println(string);
        Map<String, Object> map = JwtUtil.validateToken(string);
        //System.err.println(map);
    }

}
