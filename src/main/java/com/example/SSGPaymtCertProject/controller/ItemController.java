package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.dto.ItemDto;
import com.example.SSGPaymtCertProject.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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

    /**
     * @param itemDto itemDto
     * @return resMap
     *         <h2>item 등록</h2>
     */
    @PostMapping(value = "/create")
    public Object create(@RequestBody ItemDto itemDto) {
        ItemDto newItemDto = itemService.createItem(itemDto);
        HashMap<String, Object> resMap = new HashMap<>();

        resMap.put("code", "0000");
        resMap.put("msg", newItemDto.getItemNm() + " ITEM 등록 성공");
        resMap.put("id", newItemDto.getId());
        resMap.put("itemNm", newItemDto.getItemNm());

        return resMap;
    }

    /**
     * @param id 상품 ID
     * @return
     *  <h2>item 제거(GET)</h2>
     */
    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Long id, @PageableDefault(size = 20) Pageable pageable) {
        ModelAndView model = new ModelAndView();
        ItemDto itemDto = itemService.findById(id);
        boolean status = itemService.deleteItem(id);

        if (status) {
            model.addObject("msg", itemDto.getItemNm() + "을(를) 제거 되었습니다.");
        } else {
            model.addObject("msg", "상품을 제거하는데 실패하였습니다.");
        }

        // Pageable pageable = PageRequest.of(0, 20); -- 'PageRequest' 직접 page 설정
        List<ItemDto> itemDtoList = itemService.getItems(pageable);
        model.addObject("itemList", itemDtoList);
        model.setViewName("/item/index");
        return model;
    }
}
