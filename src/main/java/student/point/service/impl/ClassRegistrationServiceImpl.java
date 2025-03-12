package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ClassRegistration;
import student.point.repository.ClassRegistrationRepository;
import student.point.service.ClassRegistrationService;
import student.point.service.dto.ClassRegistrationDTO;
import student.point.service.mapper.ClassRegistrationMapper;

/**
 * Service Implementation for managing {@link student.point.domain.ClassRegistration}.
 */
@Service
@Transactional
public class ClassRegistrationServiceImpl implements ClassRegistrationService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassRegistrationServiceImpl.class);

    private final ClassRegistrationRepository classRegistrationRepository;

    private final ClassRegistrationMapper classRegistrationMapper;

    public ClassRegistrationServiceImpl(
        ClassRegistrationRepository classRegistrationRepository,
        ClassRegistrationMapper classRegistrationMapper
    ) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.classRegistrationMapper = classRegistrationMapper;
    }

    @Override
    public ClassRegistrationDTO save(ClassRegistrationDTO classRegistrationDTO) {
        LOG.debug("Request to save ClassRegistration : {}", classRegistrationDTO);
        ClassRegistration classRegistration = classRegistrationMapper.toEntity(classRegistrationDTO);
        classRegistration = classRegistrationRepository.save(classRegistration);
        return classRegistrationMapper.toDto(classRegistration);
    }

    @Override
    public ClassRegistrationDTO update(ClassRegistrationDTO classRegistrationDTO) {
        LOG.debug("Request to update ClassRegistration : {}", classRegistrationDTO);
        ClassRegistration classRegistration = classRegistrationMapper.toEntity(classRegistrationDTO);
        classRegistration = classRegistrationRepository.save(classRegistration);
        return classRegistrationMapper.toDto(classRegistration);
    }

    @Override
    public Optional<ClassRegistrationDTO> partialUpdate(ClassRegistrationDTO classRegistrationDTO) {
        LOG.debug("Request to partially update ClassRegistration : {}", classRegistrationDTO);

        return classRegistrationRepository
            .findById(classRegistrationDTO.getId())
            .map(existingClassRegistration -> {
                classRegistrationMapper.partialUpdate(existingClassRegistration, classRegistrationDTO);

                return existingClassRegistration;
            })
            .map(classRegistrationRepository::save)
            .map(classRegistrationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassRegistrationDTO> findOne(Long id) {
        LOG.debug("Request to get ClassRegistration : {}", id);
        return classRegistrationRepository.findById(id).map(classRegistrationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ClassRegistration : {}", id);
        classRegistrationRepository.deleteById(id);
    }
}
