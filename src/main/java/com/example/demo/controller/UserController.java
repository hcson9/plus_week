package com.example.demo.controller;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> User 관련 컨트롤러.. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입.
     *
     * @param userRequestDto 가입요청
     * @return 성공 유무
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<Void>> signupWithEmail(@RequestBody UserRequestDto userRequestDto) {
        userService.signupWithEmail(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseBody<>("success"));
    }

    /**
     * 로그인.
     *
     * @param loginRequestDto 로그인 요청
     * @param request {@code HttpServletRequest}
     * @return 성공유무
     */
    @PostMapping("/login")
    public ResponseEntity<CommonResponseBody<Void>> loginWithEmail(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Authentication authentication = userService.loginUser(loginRequestDto);
        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.USER_AUTH, authentication);
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }

    /**
     * 로그아웃.
     *
     * @param request {@code HttpServletRequest}
     * @return 성공유무
     */
    @PostMapping("/logout")
    public ResponseEntity<CommonResponseBody<Void>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 이미 필터에서 걸러지기에 필요없는 로직
//        if (session != null) {
//            session.invalidate();
//        }
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }
}
