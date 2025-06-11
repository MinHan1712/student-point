package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ClassCourse;
import student.point.repository.ClassCourseRepository;
import student.point.service.ClassCourseService;
import student.point.service.dto.ClassCourseDTO;
import student.point.service.mapper.ClassCourseMapper;

/**
 * Service Implementation for managing {@link student.point.domain.ClassCourse}.
 */
@Service
@Transactional
public class ClassCourseServiceImpl implements ClassCourseService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassCourseServiceImpl.class);

    private final ClassCourseRepository classCourseRepository;

    private final ClassCourseMapper classCourseMapper;

    public ClassCourseServiceImpl(ClassCourseRepository classCourseRepository, ClassCourseMapper classCourseMapper) {
        this.classCourseRepository = classCourseRepository;
        this.classCourseMapper = classCourseMapper;
    }

    @Override
    public ClassCourseDTO save(ClassCourseDTO classCourseDTO) {
        LOG.debug("Request to save ClassCourse : {}", classCourseDTO);
        ClassCourse classCourse = classCourseMapper.toEntity(classCourseDTO);
        classCourse = classCourseRepository.save(classCourse);
        return classCourseMapper.toDto(classCourse);
    }

    @Override
    public ClassCourseDTO update(ClassCourseDTO classCourseDTO) {
        LOG.debug("Request to update ClassCourse : {}", classCourseDTO);
        ClassCourse classCourse = classCourseMapper.toEntity(classCourseDTO);
        classCourse = classCourseRepository.save(classCourse);
        return classCourseMapper.toDto(classCourse);
    }

    @Override
    public Optional<ClassCourseDTO> partialUpdate(ClassCourseDTO classCourseDTO) {
        LOG.debug("Request to partially update ClassCourse : {}", classCourseDTO);

        return classCourseRepository
            .findById(classCourseDTO.getId())
            .map(existingClassCourse -> {
                classCourseMapper.partialUpdate(existingClassCourse, classCourseDTO);

                return existingClassCourse;
            })
            .map(classCourseRepository::save)
            .map(classCourseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassCourseDTO> findOne(Long id) {
        LOG.debug("Request to get ClassCourse : {}", id);
        return classCourseRepository.findById(id).map(classCourseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ClassCourse : {}", id);
        classCourseRepository.deleteById(id);
    }
}
