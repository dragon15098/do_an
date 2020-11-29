package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.Section;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.SectionDTO;
import com.example.demo.model.helper.CourseHelper;
import com.example.demo.model.helper.SectionHelper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Cache;
import javax.persistence.Tuple;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final UserService userService;

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = createCourse(courseDTO);
        courseDTO.setId(course.getId());
        createSections(courseDTO);
        return courseDTO;
    }

    private Course createCourse(CourseDTO courseDTO) {
        CourseHelper courseHelper = new CourseHelper(courseDTO);
        Course course = courseHelper.courseDTOToCourse();
        course.setStatus(Course.CourseStatus.WAIT);
        course.setCreateTime(new Date());
        course = courseRepository.save(course);
        return course;
    }

    private void createSections(CourseDTO course) {
        for (SectionDTO sectionDTO : course.getSections()) {
            SectionHelper sectionHelper = new SectionHelper(sectionDTO);
            Section section = sectionHelper.sectionDTOToSection();
            section.setCourseId(course.getId());
            section = sectionRepository.save(section);
            sectionDTO.setId(section.getId());
        }
    }

    @Override
    public List<CourseDTO> getCourseCreateByUserId(Long instructorId) {
        return courseRepository.getAllByInstructorId(instructorId).stream().map(this::tupleToCourseDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Optional<CourseDTO> first = courseRepository.getCourseDetail(id).stream().map(tuple -> {
            CourseDTO course = tupleToCourseDTO(tuple);
            return getInstructorDetail(course, tuple);
        }).findFirst();
        return first.orElse(new CourseDTO());
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        CourseHelper courseHelper = new CourseHelper(courseDTO);
        Course course = courseHelper.courseDTOToCourse();
        courseRepository.save(course);
        updateSections(courseDTO);
//        course = courseRepository.save(course);
        return courseDTO;
    }

    private void updateSections(CourseDTO courseDTO) {
        courseDTO.getSections().forEach(sectionDTO -> {
            SectionHelper sectionHelper = new SectionHelper(sectionDTO);
            Section section = sectionHelper.sectionDTOToSection();
            section.setCourseId(courseDTO.getId());
            section = sectionRepository.save(section);
            sectionDTO.setId(section.getId());
        });
    }

    private CourseDTO tupleToCourseDTO(Tuple tuple) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId((Long) tuple.get("id"));
        courseDTO.setTitle((String) tuple.get("title"));
        courseDTO.setImageDescriptionLink((String) tuple.get("imageDescriptionLink"));
        courseDTO.setRating((Float) tuple.get("rating"));
        courseDTO.setRatingCount((Integer) tuple.get("ratingCount"));
        courseDTO.setCreateTime((Date) tuple.get("createTime"));
        courseDTO.setStatus((Course.CourseStatus) tuple.get("status"));
        courseDTO.setDescription((String) tuple.get("description"));
        courseDTO.setCommentCount((Long) tuple.get("commentCount"));
        return courseDTO;
    }

    private CourseDTO getInstructorDetail(CourseDTO courseDTO, Tuple tuple) {
        courseDTO.setInstructor(userService.getDetail((Long) tuple.get("instructorId")));
        return courseDTO;
    }
}
