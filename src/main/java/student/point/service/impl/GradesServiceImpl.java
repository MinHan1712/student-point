package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Grades;
import student.point.repository.GradesRepository;
import student.point.service.GradesService;
import student.point.service.dto.GradesDTO;
import student.point.service.mapper.GradesMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Grades}.
 */
@Service
@Transactional
public class GradesServiceImpl implements GradesService {

    private static final Logger LOG = LoggerFactory.getLogger(GradesServiceImpl.class);

    private final GradesRepository gradesRepository;

    private final GradesMapper gradesMapper;

    public GradesServiceImpl(GradesRepository gradesRepository, GradesMapper gradesMapper) {
        this.gradesRepository = gradesRepository;
        this.gradesMapper = gradesMapper;
    }

    @Override
    public GradesDTO save(GradesDTO gradesDTO) {
        LOG.debug("Request to save Grades : {}", gradesDTO);
        Grades grades = gradesMapper.toEntity(gradesDTO);
        grades = gradesRepository.save(grades);
        return gradesMapper.toDto(grades);
    }

    @Override
    public GradesDTO update(GradesDTO gradesDTO) {
        LOG.debug("Request to update Grades : {}", gradesDTO);
        Grades grades = gradesMapper.toEntity(gradesDTO);
        grades = gradesRepository.save(grades);
        return gradesMapper.toDto(grades);
    }

    @Override
    public Optional<GradesDTO> partialUpdate(GradesDTO gradesDTO) {
        LOG.debug("Request to partially update Grades : {}", gradesDTO);

        return gradesRepository
            .findById(gradesDTO.getId())
            .map(existingGrades -> {
                gradesMapper.partialUpdate(existingGrades, gradesDTO);

                return existingGrades;
            })
            .map(gradesRepository::save)
            .map(gradesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GradesDTO> findOne(Long id) {
        LOG.debug("Request to get Grades : {}", id);
        return gradesRepository.findById(id).map(gradesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Grades : {}", id);
        gradesRepository.deleteById(id);
    }
}
