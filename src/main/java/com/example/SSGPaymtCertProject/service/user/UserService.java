package com.example.SSGPaymtCertProject.service.user;

import com.example.SSGPaymtCertProject.domain.User;
import com.example.SSGPaymtCertProject.exception.ApiException;
import com.example.SSGPaymtCertProject.exception.ExceptionEnum;
import com.example.SSGPaymtCertProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @since 2021. 08. 18
 * @author kwon-yong-il
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = userRepository.findByMbrLoginId(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with userId" + username);
        }
        return user;
    }

    /**
     * @param user 회원
     * @return Long
     *         <p>
     *         id 회원 가입
     *         </p>
     */
    public Object join(User user) {
        HashMap<String, Object> resMap = new HashMap<>();

        // 중복 회원 검증
        if (!validateDuplicateMember(user)) {
            throw new ApiException(ExceptionEnum.ERROR_USER_REDUPLICATION);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        resMap.put("code", "0000");
        resMap.put("msg", "회원가입 성공");
        resMap.put("signupId", user.getMbrLoginId());
        resMap.put("id", user.getId());

        return resMap;
    }

    /**
     * @param user
     *        <p>
     *        user 중복 회원 검사
     *        </p>
     */
    private boolean validateDuplicateMember(User user) {
        User findUser = userRepository.findByMbrLoginId(user.getMbrLoginId());

        return findUser == null;
    }
}
