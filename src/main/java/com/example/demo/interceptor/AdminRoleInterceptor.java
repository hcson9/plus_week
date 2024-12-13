package com.example.demo.interceptor;

import com.example.demo.entity.Role;
import org.springframework.stereotype.Component;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> Admin 관련 Interceptor. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see CommonAuthInterceptor
 * @since 1.0
 */
@Component
public class AdminRoleInterceptor extends RoleInterceptor {

    /**
     * 생성자.
     */
    public AdminRoleInterceptor() {
        super(Role.ADMIN);
    }
}
