package com.example.demo.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> passwordEncoder. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
public class PasswordEncoder {

    /**
     * 인코딩.
     *
     * @param rawPassword 유입 password
     * @return 인코딩된 data
     */
    public static String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 매치 여부 체크.
     *
     * @param rawPassword 기본 password
     * @param encodedPassword 인코딩된 passwowrd
     * @return 매치유무
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
