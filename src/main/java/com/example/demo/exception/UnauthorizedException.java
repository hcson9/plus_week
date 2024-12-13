package com.example.demo.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 로그인 관련 Exception. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 지원하는 자바버전 (ex : 5+ 5이상)
 */
public class UnauthorizedException extends ResponseStatusException {

    /**
     * 생성자.
     *
     * @param status 상태코드
     * @param reason 이유
     */
    public UnauthorizedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
