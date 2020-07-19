package com.pearl.expensetracker.filters;

import com.pearl.expensetracker.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Header missing!");
            return;
        }

        String[] authHeaderArr = authHeader.split("Bearer ");
        if (authHeaderArr.length <= 1 || authHeaderArr[1] == null) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be in the format: Bearer [token]");
            return;
        }

        String token = authHeaderArr[1];
        try {
            Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
        } catch (Exception e) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Token Invalid/Expired!");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
