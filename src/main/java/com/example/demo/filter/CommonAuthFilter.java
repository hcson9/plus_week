package com.example.demo.filter;

import com.example.demo.exception.UnauthorizedException;
import com.example.demo.interceptor.CommonAuthInterceptor;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> User 관련 Filter. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
public interface CommonAuthFilter extends Filter {

    /**
     * http Session 체크.
     *
     * @param request {@code ServletRequest}
     * @return session
     */
    default HttpSession findHttpSession(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return Optional.ofNullable(httpServletRequest.getSession(false))
                .orElseThrow(() -> new UnauthorizedException(HttpStatus.UNAUTHORIZED, "로그인 필요"));
    }
}