package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T extends BaseDTO> {
    List<T> data;
    Integer pageSize;
    Integer pageNumber;
}
