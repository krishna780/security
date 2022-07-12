package com.spring.security.Config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationBeforeFilter implements Filter {
    private static final String AUTHENTICATION_BASIC="Basic";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;
        String header = req.getHeader(AUTHORIZATION);
        if(header!=null){
            header.trim();
            if(StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_BASIC)){
                byte[] bytes = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded=Base64.getDecoder().decode(bytes);
                String token= new String(decoded, StandardCharsets.UTF_8);
                int i = token.indexOf(":");
                if(i==-1){
                    throw new BadCredentialsException("invalid basic authentication token");
                }
                String email = token.substring(0, i);
                if(email.isEmpty()){
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
