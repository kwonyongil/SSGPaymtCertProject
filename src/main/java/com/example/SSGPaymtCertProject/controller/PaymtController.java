package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.service.ord.OrdService;
import com.example.SSGPaymtCertProject.service.paymt.PaymtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2022. 02. 25
 * @author kwon-yong-il
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/pg")
public class PaymtController {
    private static final Logger logger = LoggerFactory.getLogger(PaymtController.class);

    private final PaymtService paymtService;

    private final OrdService ordService;


}
