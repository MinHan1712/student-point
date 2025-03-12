package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Classes;
import student.point.repository.ClassesRepository;
import student.point.service.ClassesService;
import student.point.service.dto.ClassesDTO;
import student.point.service.mapper.ClassesMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Classes}.
 */
@Service
@Transactional
public class ClassesServiceImpl implements ClassesService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassesServiceImpl.class);

    private final ClassesRepository classesRepository;

    private final ClassesMapper classesMapper;

    public ClassesServiceImpl(ClassesRepository classesRepository, ClassesMapper classesMapper) {
        this.classesRepository = classesRepository;
        this.classesMapper = classesMapper;
    }

    @Override
    public ClassesDTO save(ClassesDTO classesDTO) {
        LOG.debug("Request to save Classes : {}", classesDTO);
        Classes classes = classesMapper.toEntity(classesDTO);
        classes = classesRepository.save(classes);
        return classesMapper.toDto(classes);
    }

    @Override
    public ClassesDTO update(ClassesDTO classesDTO) {
        LOG.debug("Request to update Classes : {}", classesDTO);
        Classes classes = classesMapper.toEntity(classesDTO);
        classes = classesRepository.save(classes);
        return classesMapper.toDto(classes);
    }

    @Override
    public Optional<ClassesDTO> partialUpdate(ClassesDTO classesDTO) {
        LOG.debug("Request to partially update Classes : {}", classesDTO);

        return classesRepository
            .findById(classesDTO.getId())
            .map(existingClasses -> {
                classesMapper.partialUpdate(existingClasses, classesDTO);

                return existingClasses;
            })
            .map(classesRepository::save)
            .map(classesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassesDTO> findOne(Long id) {
        LOG.debug("Request to get Classes : {}", id);
        return classesRepository.findById(id).map(classesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Classes : {}", id);
        classesRepository.deleteById(id);
    }
}
