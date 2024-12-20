package com.example.demo.service;

import com.example.demo.dto.Authentication;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 유저관련 Service. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    /**
     * 이메일로 회원가입.
     *
     * @param userRequestDto 회원가입 정보
     */
    @Transactional
    public void signupWithEmail(UserRequestDto userRequestDto) {
        String encodedPassword = PasswordEncoder.encode(userRequestDto.getPassword());

        userRepository.save(new User(
                userRequestDto.getRole(),
                userRequestDto.getEmail(),
                userRequestDto.getNickname(),
                encodedPassword));
    }

    /**
     * 로그인.
     *
     * @param loginRequestDto 로그인 요청
     * @return 로그인 정보
     */
    public Authentication loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !PasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 이름 혹은 잘못된 비밀번호");
        }
        return new Authentication(user.getId(), user.getRole());
    }
}
