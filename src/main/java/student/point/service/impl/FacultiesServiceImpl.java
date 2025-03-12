package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Faculties;
import student.point.repository.FacultiesRepository;
import student.point.service.FacultiesService;
import student.point.service.dto.FacultiesDTO;
import student.point.service.mapper.FacultiesMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Faculties}.
 */
@Service
@Transactional
public class FacultiesServiceImpl implements FacultiesService {

    private static final Logger LOG = LoggerFactory.getLogger(FacultiesServiceImpl.class);

    private final FacultiesRepository facultiesRepository;

    private final FacultiesMapper facultiesMapper;

    public FacultiesServiceImpl(FacultiesRepository facultiesRepository, FacultiesMapper facultiesMapper) {
        this.facultiesRepository = facultiesRepository;
        this.facultiesMapper = facultiesMapper;
    }

    @Override
    public FacultiesDTO save(FacultiesDTO facultiesDTO) {
        LOG.debug("Request to save Faculties : {}", facultiesDTO);
        Faculties faculties = facultiesMapper.toEntity(facultiesDTO);
        faculties = facultiesRepository.save(faculties);
        return facultiesMapper.toDto(faculties);
    }

    @Override
    public FacultiesDTO update(FacultiesDTO facultiesDTO) {
        LOG.debug("Request to update Faculties : {}", facultiesDTO);
        Faculties faculties = facultiesMapper.toEntity(facultiesDTO);
        faculties = facultiesRepository.save(faculties);
        return facultiesMapper.toDto(faculties);
    }

    @Override
    public Optional<FacultiesDTO> partialUpdate(FacultiesDTO facultiesDTO) {
        LOG.debug("Request to partially update Faculties : {}", facultiesDTO);

        return facultiesRepository
            .findById(facultiesDTO.getId())
            .map(existingFaculties -> {
                facultiesMapper.partialUpdate(existingFaculties, facultiesDTO);

                return existingFaculties;
            })
            .map(facultiesRepository::save)
            .map(facultiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FacultiesDTO> findOne(Long id) {
        LOG.debug("Request to get Faculties : {}", id);
        return facultiesRepository.findById(id).map(facultiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Faculties : {}", id);
        facultiesRepository.deleteById(id);
    }
}
