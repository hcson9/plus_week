package com.example.demo.interceptor;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.entity.Role;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.bytecode.enhance.spi.interceptor.AbstractInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> User 관련 Interceptor. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see CommonAuthInterceptor
 * @since 1.0
 */
@Component
public class UserRoleInterceptor extends RoleInterceptor {

    /**
     * 생성자
     */
    public UserRoleInterceptor() {
        super(Role.USER);
    }
}
