/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.example.demo.interceptor;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.entity.Role;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * create on 2024. 12. 12..
 * create by IntelliJ IDEA.
 *
 * <p> Role 체크 인터셉터. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RoleInterceptor implements HandlerInterceptor, CommonAuthInterceptor {

  private final Role role;

  /**
   * 권한 체크.
   *
   * @param request {@code HttpServletRequest}
   * @param response {@code HttpServletResponse}
   * @param handler {@code handler}
   * @return 통과유무
   * @throws UnauthorizedException 발생가능
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
          throws UnauthorizedException {
    HttpSession session = findHttpSession(request);
    Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
    Role role = authentication.getRole();

    if (role != this.role) {
      throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, this.role.toString() +  " 권한이 필요합니다.");
    }

    return true;
  }
}
