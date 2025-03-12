package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ConductScores;
import student.point.repository.ConductScoresRepository;
import student.point.service.ConductScoresService;
import student.point.service.dto.ConductScoresDTO;
import student.point.service.mapper.ConductScoresMapper;

/**
 * Service Implementation for managing {@link student.point.domain.ConductScores}.
 */
@Service
@Transactional
public class ConductScoresServiceImpl implements ConductScoresService {

    private static final Logger LOG = LoggerFactory.getLogger(ConductScoresServiceImpl.class);

    private final ConductScoresRepository conductScoresRepository;

    private final ConductScoresMapper conductScoresMapper;

    public ConductScoresServiceImpl(ConductScoresRepository conductScoresRepository, ConductScoresMapper conductScoresMapper) {
        this.conductScoresRepository = conductScoresRepository;
        this.conductScoresMapper = conductScoresMapper;
    }

    @Override
    public ConductScoresDTO save(ConductScoresDTO conductScoresDTO) {
        LOG.debug("Request to save ConductScores : {}", conductScoresDTO);
        ConductScores conductScores = conductScoresMapper.toEntity(conductScoresDTO);
        conductScores = conductScoresRepository.save(conductScores);
        return conductScoresMapper.toDto(conductScores);
    }

    @Override
    public ConductScoresDTO update(ConductScoresDTO conductScoresDTO) {
        LOG.debug("Request to update ConductScores : {}", conductScoresDTO);
        ConductScores conductScores = conductScoresMapper.toEntity(conductScoresDTO);
        conductScores = conductScoresRepository.save(conductScores);
        return conductScoresMapper.toDto(conductScores);
    }

    @Override
    public Optional<ConductScoresDTO> partialUpdate(ConductScoresDTO conductScoresDTO) {
        LOG.debug("Request to partially update ConductScores : {}", conductScoresDTO);

        return conductScoresRepository
            .findById(conductScoresDTO.getId())
            .map(existingConductScores -> {
                conductScoresMapper.partialUpdate(existingConductScores, conductScoresDTO);

                return existingConductScores;
            })
            .map(conductScoresRepository::save)
            .map(conductScoresMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConductScoresDTO> findOne(Long id) {
        LOG.debug("Request to get ConductScores : {}", id);
        return conductScoresRepository.findById(id).map(conductScoresMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ConductScores : {}", id);
        conductScoresRepository.deleteById(id);
    }
}
