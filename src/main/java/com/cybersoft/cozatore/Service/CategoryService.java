// Service xử lý logic nghiệp vụ liên quan đến danh mục sản phẩm
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

    // Tiêm CategoryRepository để tương tác với database
    @Autowired
    private CategoryRepository categoryRepository;

    // Tiêm RedisTemplate để lưu trữ cache
    @Autowired
    private RedisTemplate redisTemplate;

    // Tiêm Gson để chuyển đổi JSON
    @Autowired
    private Gson gson;

    // Đánh dấu phương thức này sẽ được cache
    @Cacheable("catory")
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        // Kiểm tra xem dữ liệu có trong cache không
        if (redisTemplate.hasKey("catory") != null) {
            System.out.println("CategoryService Unable Cache");
            // Nếu có, lấy dữ liệu từ cache và chuyển đổi thành List<CategoryDTO>
            Type listType = new TypeToken<ArrayList<CategoryDTO>>() {}.getType();
            String data = redisTemplate.opsForValue().get("catory").toString();
            categoryDTOList = gson.fromJson(data, listType);
        } else {
            System.out.println("CategoryService Enable Cache");
            // Nếu không có trong cache, lấy từ database
            List<CategoryEntity> list = categoryRepository.findAll();
            // Chuyển đổi từ Entity sang DTO
            for (CategoryEntity categoryEntity : list) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(categoryEntity.getId());
                categoryDTO.setCategoryName(categoryEntity.getName());
                categoryDTOList.add(categoryDTO);
            }
            // Lưu vào cache
            String data = gson.toJson(categoryDTOList);
            redisTemplate.opsForValue().set("categories", data);
        }
        return categoryDTOList;
    }
}
