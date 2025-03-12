package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ModuleConfiguration;
import student.point.repository.ModuleConfigurationRepository;
import student.point.service.ModuleConfigurationService;
import student.point.service.dto.ModuleConfigurationDTO;
import student.point.service.mapper.ModuleConfigurationMapper;

/**
 * Service Implementation for managing {@link student.point.domain.ModuleConfiguration}.
 */
@Service
@Transactional
public class ModuleConfigurationServiceImpl implements ModuleConfigurationService {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleConfigurationServiceImpl.class);

    private final ModuleConfigurationRepository moduleConfigurationRepository;

    private final ModuleConfigurationMapper moduleConfigurationMapper;

    public ModuleConfigurationServiceImpl(
        ModuleConfigurationRepository moduleConfigurationRepository,
        ModuleConfigurationMapper moduleConfigurationMapper
    ) {
        this.moduleConfigurationRepository = moduleConfigurationRepository;
        this.moduleConfigurationMapper = moduleConfigurationMapper;
    }

    @Override
    public ModuleConfigurationDTO save(ModuleConfigurationDTO moduleConfigurationDTO) {
        LOG.debug("Request to save ModuleConfiguration : {}", moduleConfigurationDTO);
        ModuleConfiguration moduleConfiguration = moduleConfigurationMapper.toEntity(moduleConfigurationDTO);
        moduleConfiguration = moduleConfigurationRepository.save(moduleConfiguration);
        return moduleConfigurationMapper.toDto(moduleConfiguration);
    }

    @Override
    public ModuleConfigurationDTO update(ModuleConfigurationDTO moduleConfigurationDTO) {
        LOG.debug("Request to update ModuleConfiguration : {}", moduleConfigurationDTO);
        ModuleConfiguration moduleConfiguration = moduleConfigurationMapper.toEntity(moduleConfigurationDTO);
        moduleConfiguration = moduleConfigurationRepository.save(moduleConfiguration);
        return moduleConfigurationMapper.toDto(moduleConfiguration);
    }

    @Override
    public Optional<ModuleConfigurationDTO> partialUpdate(ModuleConfigurationDTO moduleConfigurationDTO) {
        LOG.debug("Request to partially update ModuleConfiguration : {}", moduleConfigurationDTO);

        return moduleConfigurationRepository
            .findById(moduleConfigurationDTO.getId())
            .map(existingModuleConfiguration -> {
                moduleConfigurationMapper.partialUpdate(existingModuleConfiguration, moduleConfigurationDTO);

                return existingModuleConfiguration;
            })
            .map(moduleConfigurationRepository::save)
            .map(moduleConfigurationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleConfigurationDTO> findOne(Long id) {
        LOG.debug("Request to get ModuleConfiguration : {}", id);
        return moduleConfigurationRepository.findById(id).map(moduleConfigurationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ModuleConfiguration : {}", id);
        moduleConfigurationRepository.deleteById(id);
    }
}
