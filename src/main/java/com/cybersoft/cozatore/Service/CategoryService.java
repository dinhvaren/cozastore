package com.cybersoft.cozatore.Service;

import com.cybersoft.cozatore.DTO.CategoryDTO;
import com.cybersoft.cozatore.Entity.CategoryEntity;
import com.cybersoft.cozatore.Repository.CategoryRepository;
import com.cybersoft.cozatore.Service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    @Cacheable("catory")
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        if (redisTemplate.hasKey("catory") != null) {
            System.out.println("CategoryService Unable Cache");
            Type listType = new TypeToken<ArrayList<CategoryDTO>>() {}.getType();
            String data = redisTemplate.opsForValue().get("catory").toString();

            categoryDTOList = gson.fromJson(data, listType);
        }else {
            System.out.println("CategoryService Enable Cache");
            List<CategoryEntity> list = categoryRepository.findAll();
            for (CategoryEntity categoryEntity : list) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(categoryEntity.getId());
                categoryDTO.setCategoryName(categoryEntity.getName());
                categoryDTOList.add(categoryDTO);

            }
            String data = gson.toJson(categoryDTOList);
            redisTemplate.opsForValue().set("categories", data);
        }

        return categoryDTOList;
    }
}
