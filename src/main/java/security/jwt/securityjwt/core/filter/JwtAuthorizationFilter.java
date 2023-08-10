package security.jwt.securityjwt.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import security.jwt.securityjwt.common.security.CurrentPrincipal;
import security.jwt.securityjwt.common.utils.jwt.JwtUtils;
import security.jwt.securityjwt.common.web.response.RestBean;
import security.jwt.securityjwt.common.web.response.ServiceCode;

import java.io.IOException;
import java.util.List;

import static security.jwt.securityjwt.common.consts.HttpConsts.HEADER_AUTHORIZATION;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final int JWT_MIN_LENGTH = 100;


    public JwtAuthorizationFilter() {
        log.info("创建过滤器对象：JwtAuthorizationFilter");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.debug("处理JWT的过滤器开始处理当前请求……");


        // 尝试接收客户端的请求中携带的JWT数据
        // 业内惯用的做法是：客户端会将JWT放在请求头中名为Authorization的属性中
        String jwt = request.getHeader(HEADER_AUTHORIZATION);
        log.debug("客户端携带的JWT：{}", jwt);


        // 判断JWT的基本有效性（没有必要尝试解析格式明显错误的JWT数据）
        if (!StringUtils.hasText(jwt) || jwt.length() < JWT_MIN_LENGTH) {
            // 对于无效的JWT，应该直接放行
            log.warn("当前请求中，客户端没有携带有效的JWT，将放行");
            filterChain.doFilter(request, response);
            return;
        }

        response.setContentType("application/json; charset=utf-8");

        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(jwt);
        } catch (ExpiredJwtException e) {
            log.warn("解析JWT时出现异常：ExpiredJwtException");
            String message = "操作失败，您的登录信息已经过期，请重新登录！";
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(ServiceCode.ERR_JWT_EXPIRED, message)));
            return;
        } catch (SignatureException e) {
            log.warn("解析JWT时出现异常：SignatureException");
            String message = "非法访问，你的本次操作已经被记录！";
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(ServiceCode.ERR_JWT_SIGNATURE, message)));
            return;
        } catch (MalformedJwtException e) {
            log.warn("解析JWT时出现异常：MalformedJwtException");
            String message = "非法访问，你的本次操作已经被记录！";
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(ServiceCode.ERR_JWT_MALFORMED, message)));
            return;
        } catch (Throwable e) {
            log.warn("解析JWT时出现异常：{}", e);
            String message = "服务器忙，请稍后再试！";
            response.getWriter().write(JSONObject.toJSONString(RestBean.failure(ServiceCode.ERROR_UNKNOWN, message)));
            return;
        }


        Long userID = claims.get("userID", Long.class);
        String username = claims.get("username", String.class);
        String authoritiesJsonString = claims.get("authoritiesJsonString", String.class);
        log.debug("JWT中的userID = {}", userID);
        log.debug("JWT中的用户名 = {}", username);
        CurrentPrincipal principal = new CurrentPrincipal();
        principal.setUserID(userID);
        principal.setUsername(username);
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesJsonString, SimpleGrantedAuthority.class);
        Object credentials = null; // 凭证：无需凭证

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, credentials, authorities
        );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
