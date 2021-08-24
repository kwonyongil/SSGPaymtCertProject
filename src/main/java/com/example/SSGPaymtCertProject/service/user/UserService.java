package com.example.SSGPaymtCertProject.service.user;

import com.example.SSGPaymtCertProject.domain.User;
import com.example.SSGPaymtCertProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long join(User user) {

        validateDuplicateMember(user); // 중복 회원 검증

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param user
     *        <p>
     *        user 중복 회원 검사
     *        </p>
     */
    private void validateDuplicateMember(User user) {
        User findUser = userRepository.findByMbrLoginId(user.getMbrLoginId());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
