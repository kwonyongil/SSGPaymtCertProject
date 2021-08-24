package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.User;
import com.example.SSGPaymtCertProject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @since 2021. 08. 19
 * @author kwon-yong-il
 *         <h1>유저와 관련된 REST 통신을 정의한 컨트롤러</h1>
 *         <h2>잘못된점</h2>
 *         <p>사실 UserDto를 받아서 처리해야하지만 편의상 User엔티티를 바로 사용한다. 그래서 @Setter를 User에 추가하였다.</p>
 *         <h2>@RequiredArgsConstructor</h2>
 *         <p>필드주입보다 좋은 생성자 주입을 사용할때 final로 선언된애들로 생성자를 만들어주는 lombok애노테이션</p>
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    // private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService; // final 추가

    /**
     * @param user 유저
     * @return resMap
     *         <h2>회원가입</h2>
     */
    @PostMapping(value = "/signup")
    public HashMap<String, String> signup(@RequestBody User user) {

        HashMap<String, String> resMap = new HashMap<>();
        try {
            Long id = userService.join(user);
            resMap.put("code", "0000");
            resMap.put("msg", "회원가입 성공");
            resMap.put("signupId", String.valueOf(id));
        } catch (IllegalStateException e) {
            resMap.put("code", "5000");
            resMap.put("msg", e.getMessage());
        } catch (Exception e) {
            resMap.put("code", "9999");
            resMap.put("msg", e.getMessage());
        }
        return resMap;
    }

}