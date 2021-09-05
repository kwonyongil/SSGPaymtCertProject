package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.User;
import com.example.SSGPaymtCertProject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @since 2021. 08. 19
 * @author kwon-yong-il
 *         <h1>유저와 관련된 REST 통신을 정의한 컨트롤러</h1>
 *         <h2>잘못된점</h2>
 *         <p>사실 UserDto를 받아서 처리해야하지만 편의상 User엔티티를 바로 사용한다. 그래서 @Setter를 User에 추가하였다.</p>
 *         <h2>@RequiredArgsConstructor</h2>
 *         <p>필드주입보다 좋은 생성자 주입을 사용할때 final로 선언된애들로 생성자를 만들어주는 lombok애노테이션</p>
 *         <p>생성자 주입을 사용해야 되는 이유 </p>
 *         <p>1. 의존성 주입되는 객체의 불변성을 확보</p>
 *         <p>2. 테스트 코드의 작성이 용이 (필드 주입은 테스트 코드 작성시 DI 이슈로 쉽지 않음)</p>
 *         <p>3. 순환 참조 문제를 빌드 단계에서 파악됨</p>
 *         <p>4. final + lombok 코드 간결화</p>
 *         <p>참고 링크 : https://mangkyu.tistory.com/125</p>
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService; // final 추가

    /**
     * @param user 유저
     * @return resMap
     *         <h2>회원가입</h2>
     */
    @PostMapping(value = "/signup")
    public Object signup(@RequestBody User user) {
        return userService.join(user);
    }

    @GetMapping(value = "/sentry/exception")
    public Object exception() throws Exception {
        throw new IllegalAccessException("Sentry Exception TEST");
    }

    @GetMapping(value = "/sentry/exception/service")
    public void exceptionService() {
        logger.error("어렵당 너무 ㅠㅠ ");
//        Sentry.captureException(new Exception("ssssss"));
    }

}