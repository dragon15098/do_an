package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.Section;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.SectionDTO;
import com.example.demo.model.helper.CourseHelper;
import com.example.demo.model.helper.SectionHelper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.SectionService;
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
    private final UserService userService;
    private final SectionService sectionService;

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
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
    public CourseDTO insertOrUpdate(CourseDTO courseDTO) {
        CourseHelper courseHelper = new CourseHelper(courseDTO);
        Course course = courseHelper.courseDTOToCourse();
        if (course.getId() == null) {
            course.setStatus(Course.CourseStatus.WAIT);
            course.setCreateTime(new Date());
            course.setCommentCount(0L);
            course.setRating(5f);
            course.setRatingCount(0L);
        }
        course = courseRepository.save(course);
        courseDTO.setId(course.getId());
        updateSections(courseDTO);
        return courseDTO;
    }

    private void updateSections(CourseDTO courseDTO) {

        if (courseDTO.getSections() != null) {
            //fake course to ignore loop dto in response
            CourseDTO course = new CourseDTO();
            course.setId(courseDTO.getId());

            courseDTO.getSections().forEach(sectionDTO -> sectionDTO.setCourse(course));
            sectionService.insertOrUpdate(courseDTO.getSections());
        }
    }


    private CourseDTO tupleToCourseDTO(Tuple tuple) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId((Long) tuple.get("id"));
        courseDTO.setTitle((String) tuple.get("title"));
        courseDTO.setImageDescriptionLink((String) tuple.get("imageDescriptionLink"));
        courseDTO.setRating((Float) tuple.get("rating"));
        courseDTO.setRatingCount((Long) tuple.get("ratingCount"));
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
