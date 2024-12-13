package com.example.demo.filter;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.entity.Role;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 권한 관련 인터셉터. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class RoleFilter implements CommonAuthFilter {

    private final Role role;

    /**
     * 권한 체크.
     *
     * @param servletRequest {@code ServletRequest}
     * @param servletResponse {@code ServletResponse}
     * @param filterChain {@code FilterChain}
     * @throws IOException 발생가능
     * @throws ServletException 발생가능
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = findHttpSession(servletRequest);

        Authentication authentication = (Authentication) session.getAttribute(
                GlobalConstants.USER_AUTH);

        Role clientRole = authentication.getRole();
        if (clientRole != this.role) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, role.getName() + " 권한이 필요합니다.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}