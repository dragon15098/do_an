package com.example.demo.service.impl;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CategoryDTO;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.Page;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.helper.CourseHelper;
import com.example.demo.model.helper.RandomHelper;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CourseService;
import com.example.demo.service.SectionService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryService categoryService;
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
        Optional<CourseDTO> first = courseRepository.getCourseDetail(id).stream()
                .map(tuple -> {
                    CourseDTO course = tupleToCourseDTO(tuple);
                    CategoryDTO courseCategory = getCourseCategory(tuple);
                    UserDTO instructor = getInstructorDetail(tuple);
                    course.setCategory(courseCategory);
                    course.setInstructor(instructor);
                    course = getCourseSellDetail(course);
                    return course;
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
            course.setPrice(0F);
        }
        course = courseRepository.save(course);
        courseDTO.setId(course.getId());
        updateSections(courseDTO);
        return courseDTO;
    }

    @Override
    public List<CourseDTO> findCourseByTitle(String title) {
        return courseRepository.getCourseByTitle("%" + title + "%")
                .stream()
                .map(this::tupleToCourseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseDTO> getCourseHottest(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "numberSells"));
        List<Tuple> topSell = courseRepository.getCourseFilter(pageable);
        List<CourseDTO> data = topSell.stream().map(this::tupleToDTO).collect(Collectors.toList());
        Page<CourseDTO> response = new Page<>();
        response.setData(data);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        return response;
    }

    private CourseDTO tupleToDTO(Tuple tuple) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(Long.parseLong(tuple.get("id").toString()));
        courseDTO.setTitle((String) tuple.get("title"));
        courseDTO.setImageDescriptionLink((String) tuple.get("imageDescriptionLink"));
        courseDTO.setPrice(Float.parseFloat(tuple.get("price").toString()));
        courseDTO.setCommentCount(Long.parseLong(tuple.get("courseComment").toString()));
        courseDTO.setRating(Float.parseFloat(tuple.get("rating").toString()));
        courseDTO.setRatingCount(Long.parseLong(tuple.get("courseRating").toString()));
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

    @Override
    public CourseDTO approveCourse(CourseDTO courseDTO) {
        courseRepository.approveCourse(courseDTO.getId(), Course.CourseStatus.APPROVED);
        courseDTO.setStatus(Course.CourseStatus.APPROVED);
        return courseDTO;
    }

    @Override
    public Page<CourseDTO> getCourseNewest(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createTime");
        List<CourseDTO> data = courseRepository.getCourseFilter(pageable)
                .stream().map(this::tupleToDTO).collect(Collectors.toList());
        Page<CourseDTO> page = new Page<>();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        page.setData(data);
        return page;
    }

    @Override
    public Page<CourseDTO> getSomeRandomCourse(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<CourseDTO> data = courseRepository.getCourseFilter(pageable)
                .stream().map(this::tupleToDTO).collect(Collectors.toList());
        RandomHelper randomHelper = new RandomHelper(data);
        List<CourseDTO> randomCourseDTO = randomHelper.getRandomCourseDTO(pageSize);
        Page<CourseDTO> page = new Page<>();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        page.setData(randomCourseDTO);
        return page;
    }

    @Override
    public List<CourseDTO> getCourseByFilter(List<Long> categoryId, String courseTitle) {
        if(courseTitle!=null){
            courseTitle = "%" + courseTitle + "%";
        }
        else{
            courseTitle = "%";
        }
        return courseRepository.getCourseByFilter(categoryId, courseTitle)
                .stream()
                .map(tuple -> {
                    CourseDTO courseDTO = tupleToCourseDTO(tuple);
                    getCourseSellDetail(courseDTO);
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseDTO> searchCourse(String arrayIds, String title,
                                        String status, String fromDate,
                                        String toDate, String instructorName,
                                        Integer pageSize, Integer pageNumber) {
        if (fromDate != null && toDate != null && !fromDate.equals("NaN-aN-aN") && !toDate.equals("NaN-aN-aN")) {
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date toD = format.parse(toDate);
                Date fromD = format.parse(fromDate);
                if (arrayIds != null) {
                    List<Long> categoryIds = Arrays.stream(arrayIds.split(","))
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    if (title == null) {
                        title = "%";
                    } else {
                        title = "%" + title + "%";
                    }
                    if (instructorName == null) {
                        instructorName = "%";
                    } else {
                        instructorName = "%" + instructorName + "%";
                    }
                    if (status == null || status.equals("")) {
                        status = "%";
                    }
                    List<CourseDTO> collect = courseRepository.search(categoryIds, title,
                            instructorName, fromD, toD, status)
                            .stream().map(tuple -> {
                                CourseDTO courseDTO = tupleToCourseDTO(tuple);
                                courseDTO = this.getCourseSellDetail(courseDTO);
                                UserDTO instructor = new UserDTO();
                                instructor.setFirstName((String) tuple.get("firstName"));
                                instructor.setLastName((String) tuple.get("lastName"));
                                courseDTO.setInstructor(instructor);
                                return courseDTO;
                            })
                            .collect(Collectors.toList());
                    Page<CourseDTO> result = new Page<>();
                    result.setData(collect);
                    result.setPageNumber(pageNumber);
                    result.setPageSize(pageSize);
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Page<>();
    }


    private CourseDTO tupleToCourseDTO(Tuple tuple) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(Long.parseLong(tuple.get("id").toString()));
        courseDTO.setTitle((String) tuple.get("title"));
        courseDTO.setImageDescriptionLink((String) tuple.get("imageDescriptionLink"));
        courseDTO.setDescription((String) tuple.get("description"));
        courseDTO.setPrice(Float.parseFloat(tuple.get("price").toString()));
        courseDTO.setStatus(Course.CourseStatus.valueOf((String) tuple.get("status")));
        return courseDTO;
    }

    private CourseDTO getCourseSellDetail(CourseDTO courseDTO) {
        return this.courseRepository.getCourseSellDetail(courseDTO.getId()).stream()
                .map(tuple -> {
                    courseDTO.setCommentCount(Long.parseLong(tuple.get("reviewCount").toString()));
                    courseDTO.setCourseSell(Long.parseLong(tuple.get("totalSell").toString()));
                    courseDTO.setRating(Float.parseFloat(tuple.get("rating").toString()));
                    courseDTO.setRatingCount(Long.parseLong(tuple.get("reviewCount").toString()));
                    return courseDTO;
                }).findFirst().orElse(courseDTO);
    }

    private UserDTO getInstructorDetail(Tuple tuple) {
        return userService.getDetail(Long.parseLong(tuple.get("instructorId").toString()));

    }

    private CategoryDTO getCourseCategory(Tuple tuple) {
        return categoryService.getCategoryById(Long.parseLong(tuple.get("categoryId").toString()));
    }
}
