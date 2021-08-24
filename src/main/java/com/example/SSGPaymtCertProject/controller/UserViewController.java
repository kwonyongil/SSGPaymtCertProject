package com.example.SSGPaymtCertProject.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @since 2021. 08. 19
 * @author kwon-yong-il
 *         <h1>유저와 관련된 View 컨트롤러</h1>
 */
@Controller
@RequiredArgsConstructor
public class UserViewController {

    /**
     * @param status 로그인 페이지 전달 상태값
     * @return model
     *         <h2>로그인 페이지</h2>
     */
    @GetMapping(value = "/login")
    public ModelAndView loginPage(@RequestParam(value = "status", required = false) String status) {
        ModelAndView model = new ModelAndView();
        // ModelAndView model, view 를 하나의 return value 로 사용할 수 있다.

        status = StringUtils.defaultString(status);
        if (status.equals("fail")) {
            model.addObject("msg", "로그인에 실패했습니다!");
        }
        if (status.equals("logout")) {
            model.addObject("msg", "로그아웃 하셨습니다.");
        }
        if (status.equals("denied")) {
            model.addObject("msg", "로그인 에러 (거절됨).");
        }

        model.setViewName("user/login");
        return model;
    }

    /**
     * @return model
     *         <h2>회원가입 페이지(사용 x)</h2>
     */
    @GetMapping(value = "/signup")
    public ModelAndView signupPage() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user/signup");
        return model;
    }

    /**
     * @param authentication 성공시 인증 정보
     * @return model
     *         <h2>로그인 성공시 포워딩되는 메소드, home 페이지</h2>
     */
    @GetMapping(value = "/login/success")
    public ModelAndView indexPage(Authentication authentication) {
        ModelAndView model = new ModelAndView();
        model.addObject(authentication);
        model.setViewName("/main");
        return model;
    }
}
