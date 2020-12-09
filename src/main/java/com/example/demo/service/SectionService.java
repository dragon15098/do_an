package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Section;
import com.example.demo.model.dto.SectionDTO;

import java.util.List;

public interface SectionService {
    List<SectionDTO> getCourseSection(Long courseId);

    SectionDTO getSectionDetail(Long sectionId);

    List<SectionDTO> insertOrUpdate(List<SectionDTO> sectionDTOs);

    SectionDTO insertOrUpdate(SectionDTO sectionDTO);
}
