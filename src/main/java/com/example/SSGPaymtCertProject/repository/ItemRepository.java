package com.example.SSGPaymtCertProject.repository;

import com.example.SSGPaymtCertProject.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // select * from Item where item_nm like '%{itemNm}%'
    Item findByItemNmLike(String itemNm);

    // select item_id from Item where item_nm = '{itemNm}'
    // and price = {price} limit 1
    boolean existsByItemNmAndPrice(String itemNm, Long price);

    // select count(*) from Item where item_nm like '%{itemNm}%'
    int countByItemNmLike(String itemNm);

    // delete from Item where price between {price1} and {price2}
    void deleteByPriceBetween(long price1, long price2);
}
