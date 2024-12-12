package com.example.demo.interceptor;

import com.example.demo.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class AdminRoleInterceptor extends RoleInterceptor {
    public AdminRoleInterceptor() {
        super(Role.ADMIN);
    }
}
