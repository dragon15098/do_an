package com.example.demo.service.impl;

import com.example.demo.model.UserCart;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.UserCartDTO;
import com.example.demo.repository.UserCartRepository;
import com.example.demo.service.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCartServiceImpl implements UserCartService {
    private final UserCartRepository userCartRepository;

    @Override
    public UserCart createUserCart(UserCart userCart) {
        userCart = userCartRepository.save(userCart);
        return userCart;
    }

    @Override
    public List<UserCartDTO> getUserCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            return userCartRepository.getUserCartByUserId(userId)
                    .stream()
                    .map(this::tupleToDTO)
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    @Override
    public UserCartDTO deleteUserCart(Long id) {
        userCartRepository.deleteById(id);
        return new UserCartDTO();
    }

    private UserCartDTO tupleToDTO(Tuple tuple) {
        UserCartDTO userCartDTO = new UserCartDTO();
        userCartDTO.setId(Long.parseLong(tuple.get("userCartId").toString()));

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(Long.parseLong(tuple.get("courseId").toString()));
        courseDTO.setTitle((String) tuple.get("title"));
        courseDTO.setDescription((String) tuple.get("description"));
        courseDTO.setImageDescriptionLink((String) tuple.get("imageDescriptionLink"));
        courseDTO.setPrice(Float.parseFloat(tuple.get("price").toString()));
        courseDTO.setCommentCount(Long.parseLong(tuple.get("courseComment").toString()));
        courseDTO.setRating(Float.parseFloat(tuple.get("rating").toString()));
        courseDTO.setRatingCount(Long.parseLong(tuple.get("courseRating").toString()));

        userCartDTO.setCourse(courseDTO);

        return userCartDTO;
    }
}
