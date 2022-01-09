package com.example.SSGPaymtCertProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @since 2021. 10. 14
 * @author kwon-yong-il
 *         <h1>테스트 뷰 컨트롤러</h1>
 */
@Controller
@RequestMapping("/test")
public class TestViewController {

    /**
     * @return model
     *         <h2>테스트 인덱스</h2>
     */
    @GetMapping(value = "/index")
    public ModelAndView indexPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/index");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 블랭크</h2>
     */
    @GetMapping(value = "/blank")
    public ModelAndView blankPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/blank");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 버튼</h2>
     */
    @GetMapping(value = "/buttons")
    public ModelAndView buttonsPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/buttons");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 카드</h2>
     */
    @GetMapping(value = "/cards")
    public ModelAndView cardsPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/cards");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 인덱스</h2>
     */
    @GetMapping(value = "/charts")
    public ModelAndView chartsPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/charts");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 비밀번호 찾기</h2>
     */
    @GetMapping(value = "/forgot")
    public ModelAndView forgotPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/forgot-password");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 login</h2>
     */
    @GetMapping(value = "/login")
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/login");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 register</h2>
     */
    @GetMapping(value = "/register")
    public ModelAndView registerPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/register");
        return model;
    }

    /**
     * @return model
     *         <h2>테스트 tables</h2>
     */
    @GetMapping(value = "/tables")
    public ModelAndView tablesPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/bootstrap/tables");
        return model;
    }
}
