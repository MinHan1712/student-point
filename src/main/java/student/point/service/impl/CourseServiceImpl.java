package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Course;
import student.point.repository.CourseRepository;
import student.point.service.CourseService;
import student.point.service.dto.CourseDTO;
import student.point.service.mapper.CourseMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Course}.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        LOG.debug("Request to save Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDTO update(CourseDTO courseDTO) {
        LOG.debug("Request to update Course : {}", courseDTO);
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    public Optional<CourseDTO> partialUpdate(CourseDTO courseDTO) {
        LOG.debug("Request to partially update Course : {}", courseDTO);

        return courseRepository
            .findById(courseDTO.getId())
            .map(existingCourse -> {
                courseMapper.partialUpdate(existingCourse, courseDTO);

                return existingCourse;
            })
            .map(courseRepository::save)
            .map(courseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseDTO> findOne(Long id) {
        LOG.debug("Request to get Course : {}", id);
        return courseRepository.findById(id).map(courseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
