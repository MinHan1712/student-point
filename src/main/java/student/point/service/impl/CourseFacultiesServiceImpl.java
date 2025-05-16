package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.CourseFaculties;
import student.point.repository.CourseFacultiesRepository;
import student.point.service.CourseFacultiesService;
import student.point.service.dto.CourseFacultiesDTO;
import student.point.service.mapper.CourseFacultiesMapper;

/**
 * Service Implementation for managing {@link student.point.domain.CourseFaculties}.
 */
@Service
@Transactional
public class CourseFacultiesServiceImpl implements CourseFacultiesService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseFacultiesServiceImpl.class);

    private final CourseFacultiesRepository courseFacultiesRepository;

    private final CourseFacultiesMapper courseFacultiesMapper;

    public CourseFacultiesServiceImpl(CourseFacultiesRepository courseFacultiesRepository, CourseFacultiesMapper courseFacultiesMapper) {
        this.courseFacultiesRepository = courseFacultiesRepository;
        this.courseFacultiesMapper = courseFacultiesMapper;
    }

    @Override
    public CourseFacultiesDTO save(CourseFacultiesDTO courseFacultiesDTO) {
        LOG.debug("Request to save CourseFaculties : {}", courseFacultiesDTO);
        CourseFaculties courseFaculties = courseFacultiesMapper.toEntity(courseFacultiesDTO);
        courseFaculties = courseFacultiesRepository.save(courseFaculties);
        return courseFacultiesMapper.toDto(courseFaculties);
    }

    @Override
    public CourseFacultiesDTO update(CourseFacultiesDTO courseFacultiesDTO) {
        LOG.debug("Request to update CourseFaculties : {}", courseFacultiesDTO);
        CourseFaculties courseFaculties = courseFacultiesMapper.toEntity(courseFacultiesDTO);
        courseFaculties = courseFacultiesRepository.save(courseFaculties);
        return courseFacultiesMapper.toDto(courseFaculties);
    }

    @Override
    public Optional<CourseFacultiesDTO> partialUpdate(CourseFacultiesDTO courseFacultiesDTO) {
        LOG.debug("Request to partially update CourseFaculties : {}", courseFacultiesDTO);

        return courseFacultiesRepository
            .findById(courseFacultiesDTO.getId())
            .map(existingCourseFaculties -> {
                courseFacultiesMapper.partialUpdate(existingCourseFaculties, courseFacultiesDTO);

                return existingCourseFaculties;
            })
            .map(courseFacultiesRepository::save)
            .map(courseFacultiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseFacultiesDTO> findOne(Long id) {
        LOG.debug("Request to get CourseFaculties : {}", id);
        return courseFacultiesRepository.findById(id).map(courseFacultiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CourseFaculties : {}", id);
        courseFacultiesRepository.deleteById(id);
    }
}
