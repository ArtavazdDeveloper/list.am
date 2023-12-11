package com.example.list_am.service.impl;

import com.example.list_am.entity.Hashtag;
import com.example.list_am.entity.Item;
import com.example.list_am.entity.User;
import com.example.list_am.entity.UserType;
import com.example.list_am.repository.HashtagRepository;
import com.example.list_am.repository.ItemRepository;
import com.example.list_am.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final HashtagRepository hashtagRepository;

    @Value("${listam.upload.image.path}")
    private String imageUploadPath;

    @Override
    public List<Item> findItemsByUser(User user) {
        List<Item> items;
        if (user.getUserType() == UserType.ADMIN){
            items = itemRepository.findAll();
        } else {
            items = itemRepository.findAllByUser_Id(user.getId());
        }
        return items;
    }

    @Override
    public Optional<Item> findById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public void addItem(User currentUser, MultipartFile multipartFile, Item item) throws IOException {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            item.setImgName(fileName);
        }
        item.setUser((org.apache.catalina.User) currentUser);
        if(tags != null && !tags.isEmpty()){
            List<Hashtag> hashtags = new ArrayList<>();
            for (Integer tagId : tags) {
                Optional<Hashtag> byId = hashtagRepository.findById(tagId);
                if(byId.isPresent()){
                    hashtags.add(byId.get());
                }
            }
            item.setHashtagList(hashtags);
        }
        itemRepository.save(item);
    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }
}
