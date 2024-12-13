package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 로그인 관련 필터. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
public class AuthFilter implements CommonAuthFilter {

    /**
     * 세션체크.
     *
     * @param servletRequest {@code ServletRequest}
     * @param servletResponse {@code ServletResponse}
     * @param filterChain {@code FilterChain}
     * @throws IOException 파일관련 Exception
     * @throws ServletException 발생가능
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        findHttpSession(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
