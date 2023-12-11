package com.example.list_am.service;

import com.example.list_am.entity.Item;
import com.example.list_am.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ItemService {

    List<Item> findItemsByUser(User user);
    Optional<Item> findById(int id);

    void addItem(User currentUser, MultipartFile multipartFile, Item item) throws IOException;

    void deleteById(int id);
}
