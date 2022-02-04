package com.example.SSGPaymtCertProject.controller;

import com.example.SSGPaymtCertProject.domain.dto.ItemDto;
import com.example.SSGPaymtCertProject.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

/**
 * @since 2021. 11. 19
 * @author kwon-yong-il
 *         <h1>아이템과 관련된 View 컨트롤러</h1>
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemViewController {

    //private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    /**
     * @param pageable 페이징정보
     * @return
     * <h2>상품목록페이지</h2>
     */
    @GetMapping("/index")
    public ModelAndView indexPage(@PageableDefault(size = 20) Pageable pageable) {
        List<ItemDto> itemDtoList = itemService.getItems(pageable);
        ModelAndView model = new ModelAndView();
        model.addObject("itemList", itemDtoList);
        model.setViewName("/item/index");
        return model;
    }

    /**
     * @return
     * <h2>item 등록 페이지</h2>
     */
    @GetMapping("/create")
    public ModelAndView createPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/item/create");
        return model;
    }

    /**
     * @param id 상품 id
     * @return
     * <h2>상품상세페이지</h2>
     */
    @GetMapping("/{id}")
    public ModelAndView showPage(@PathVariable("id") long id) {
        Optional<ItemDto> itemData = Optional.ofNullable(itemService.findById(id));

        ModelAndView model = new ModelAndView();

        model.addObject("item", itemData);
        model.setViewName("/item/show");
        return model;
    }

}
