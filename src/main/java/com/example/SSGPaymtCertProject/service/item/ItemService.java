package com.example.SSGPaymtCertProject.service.item;

import com.example.SSGPaymtCertProject.domain.Item;
import com.example.SSGPaymtCertProject.domain.dto.ItemDto;
import com.example.SSGPaymtCertProject.exception.ApiException;
import com.example.SSGPaymtCertProject.exception.ExceptionEnum;
import com.example.SSGPaymtCertProject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @since 2021. 09. 01
 * @author kwon-yong-il
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public List<ItemDto> getItems(Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(pageable);

        return itemPage.getContent()
                .stream()
                .map(Item::toItemDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        // 조회되는 회원이 없다면 예외 발생
        return item.orElseThrow(() -> new ApiException(ExceptionEnum.ERROR_ITEM_NOTFOUND)).toItemDto();
    }

    @Transactional
    public boolean deleteItem(long itemId) {
        //itemRepository.delete(item);
        itemRepository.deleteById(itemId);
        return true;
    }

    @Transactional
    public ItemDto updateItem(ItemDto itemDto) {
        Item item = Item.builder()
                .id(itemDto.getId())
                .itemNm(itemDto.getItemNm())
                .price(itemDto.getPrice())
                .regpeId(itemDto.getRegpeId())
                .modpeId(itemDto.getModpeId())
                .build();
        Item newItem = itemRepository.save(item);
        return newItem.toItemDto();
    }

    @Transactional
    public ItemDto createItem(ItemDto itemDto) {
        Item item = Item.builder()
                .itemNm(itemDto.getItemNm())
                .price(itemDto.getPrice())
                .regpeId(itemDto.getRegpeId())
                .modpeId(itemDto.getModpeId())
                .build();
        Item newItem = itemRepository.save(item);

        return newItem.toItemDto();
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    private void itemCRUDExample() {
        // EntityManager 를 통해 entity 를 저장, 수정, 삭제, 조회
        Item entity1 = Item.builder()
                .itemNm("egg")
                .price(135L)
                .build();
        EntityManager entityManager = emf.createEntityManager();
        entityManager.persist(entity1);

        Item entity2 = entityManager.find(Item.class, entity1.getId());
        entity2.setPrice(235L);
        entityManager.merge(entity2);
        // JPQL, Criteria API 를 이용해서 복잡한 쿼리 수행
        String jpql = "select item from Item item where item.itemNm like '%egg%'";
        List<Item> entities = entityManager.createQuery(jpql, Item.class)
                .getResultList();
    }
}
