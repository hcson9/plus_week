/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

package com.example.demo.interceptor;

import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * create on 2024. 12. 13..
 * create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see
 * @since 지원하는 자바버전 (ex : 5+ 5이상)
 */
public interface CommonAuthInterceptor {
  default HttpSession findHttpSession(ServletRequest request) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    return Optional.ofNullable(httpServletRequest.getSession(false))
            .orElseThrow(() -> new UnauthorizedException(HttpStatus.UNAUTHORIZED, "세션이 끊어졌습니다."));
  }
}
