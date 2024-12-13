package com.example.demo.interceptor;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 로그인 관련 인터셉터. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Component
public class AuthInterceptor implements HandlerInterceptor, CommonAuthInterceptor {

    /**
     * 로그인 유무 체크.
     *
     * @param request {@code HttpServletRequest}
     * @param response {@code HttpServletResponse}
     * @param handler {@code handler}
     * @return 통과 여부
     * @throws UnauthorizedException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        HttpSession session = findHttpSession(request);
        if (session.getAttribute(GlobalConstants.USER_AUTH) == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        return true;
    }
}
