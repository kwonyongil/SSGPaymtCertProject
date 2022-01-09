package com.example.SSGPaymtCertProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @since 2021. 08. 19
 * @author kwon-yong-il
 *         <h1>공통 뷰 컨트롤러</h1>
 */
@Controller
public class CommViewController
{
    /**
     * @return model
     *         <h2>도메인 루트 페이지</h2>
     */
    @GetMapping(value = "/")
    public ModelAndView rootPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/main");
        return model;
    }

    /**
     * @return model
     *         <h2>로그인 성공시 포워딩되는 메소드, 메인 페이지</h2>
     */
    @GetMapping(value = "/main")
    public ModelAndView mainPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/main");
        return model;
    }

    /**
     * @return model
     *         <h2>404 페이지</h2>
     */
    @GetMapping(value = "/error")
    public ModelAndView buttonsPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/404");
        return model;
    }
}
