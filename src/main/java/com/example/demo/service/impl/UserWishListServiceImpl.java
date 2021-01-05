package com.example.demo.service.impl;

import com.example.demo.model.UserWishList;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.UserWishListDTO;
import com.example.demo.model.helper.UserWishListHelper;
import com.example.demo.repository.UserWishListRepository;
import com.example.demo.service.UserWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserWishListServiceImpl implements UserWishListService {
    @Autowired
    UserWishListRepository userWishListRepository;

    @Override
    public UserWishListDTO addNewUserWishList(UserWishListDTO userWishListDTO) {
        UserWishListHelper userWishListHelper = new UserWishListHelper(userWishListDTO);
        UserWishList userWishList = userWishListHelper.userWishListDTOToUserWishList();
        userWishList = userWishListRepository.save(userWishList);
        userWishListDTO.setId(userWishList.getId());
        return userWishListDTO;
    }

    @Override
    public List<UserWishListDTO> getUserWishListByUserId(Long id) {
        return userWishListRepository.getUserWishListCourse(id).stream().map(tuple -> {
            UserWishListDTO userWishListDTO = new UserWishListDTO();
            userWishListDTO.setCourse(getCourseDetail(tuple));
            return userWishListDTO;
        }).collect(Collectors.toList());
    }

    private CourseDTO getCourseDetail(Tuple tuple) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(Long.parseLong(tuple.get("id").toString()));
        courseDTO.setTitle(tuple.get("title").toString());
        courseDTO.setDescription(tuple.get("description").toString());
        courseDTO.setImageDescriptionLink(tuple.get("imageDescriptionLink").toString());
        courseDTO.setPrice(Float.parseFloat(tuple.get("price").toString()));
        courseDTO.setCommentCount(Long.parseLong(tuple.get("courseComment").toString()));
        courseDTO.setRating(Float.parseFloat(tuple.get("rating").toString()));
        courseDTO.setRatingCount(Long.parseLong(tuple.get("courseRating").toString()));
        return courseDTO;
    }
}
