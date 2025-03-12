package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.MasterData;
import student.point.repository.MasterDataRepository;
import student.point.service.MasterDataService;
import student.point.service.dto.MasterDataDTO;
import student.point.service.mapper.MasterDataMapper;

/**
 * Service Implementation for managing {@link student.point.domain.MasterData}.
 */
@Service
@Transactional
public class MasterDataServiceImpl implements MasterDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataServiceImpl.class);

    private final MasterDataRepository masterDataRepository;

    private final MasterDataMapper masterDataMapper;

    public MasterDataServiceImpl(MasterDataRepository masterDataRepository, MasterDataMapper masterDataMapper) {
        this.masterDataRepository = masterDataRepository;
        this.masterDataMapper = masterDataMapper;
    }

    @Override
    public MasterDataDTO save(MasterDataDTO masterDataDTO) {
        LOG.debug("Request to save MasterData : {}", masterDataDTO);
        MasterData masterData = masterDataMapper.toEntity(masterDataDTO);
        masterData = masterDataRepository.save(masterData);
        return masterDataMapper.toDto(masterData);
    }

    @Override
    public MasterDataDTO update(MasterDataDTO masterDataDTO) {
        LOG.debug("Request to update MasterData : {}", masterDataDTO);
        MasterData masterData = masterDataMapper.toEntity(masterDataDTO);
        masterData = masterDataRepository.save(masterData);
        return masterDataMapper.toDto(masterData);
    }

    @Override
    public Optional<MasterDataDTO> partialUpdate(MasterDataDTO masterDataDTO) {
        LOG.debug("Request to partially update MasterData : {}", masterDataDTO);

        return masterDataRepository
            .findById(masterDataDTO.getId())
            .map(existingMasterData -> {
                masterDataMapper.partialUpdate(existingMasterData, masterDataDTO);

                return existingMasterData;
            })
            .map(masterDataRepository::save)
            .map(masterDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MasterDataDTO> findOne(Long id) {
        LOG.debug("Request to get MasterData : {}", id);
        return masterDataRepository.findById(id).map(masterDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MasterData : {}", id);
        masterDataRepository.deleteById(id);
    }
}
