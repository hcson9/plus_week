package com.example.demo.filter;

import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface CommonAuthFilter extends Filter {

    default HttpSession findHttpSession(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return Optional.ofNullable(httpServletRequest.getSession(false))
                .orElseThrow(() -> new UnauthorizedException(HttpStatus.UNAUTHORIZED, "로그인 필요"));
    }
}