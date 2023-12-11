package com.example.list_am.repository;

import com.example.list_am.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Integer> {

    List<Item> findAllByUser_Id(int userId);

}
