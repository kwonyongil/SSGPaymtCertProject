package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.dto.ItemDto;
import com.example.SSGPaymtCertProject.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @since 2021. 09. 05
 * @author kwon-yong-il
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @GetMapping("")
    public List<ItemDto> getItems(Pageable pageable) {
        return itemService.getItems(pageable);
    }
}
