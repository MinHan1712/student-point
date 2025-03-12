package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.StatisticsDetails;
import student.point.repository.StatisticsDetailsRepository;
import student.point.service.StatisticsDetailsService;
import student.point.service.dto.StatisticsDetailsDTO;
import student.point.service.mapper.StatisticsDetailsMapper;

/**
 * Service Implementation for managing {@link student.point.domain.StatisticsDetails}.
 */
@Service
@Transactional
public class StatisticsDetailsServiceImpl implements StatisticsDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsDetailsServiceImpl.class);

    private final StatisticsDetailsRepository statisticsDetailsRepository;

    private final StatisticsDetailsMapper statisticsDetailsMapper;

    public StatisticsDetailsServiceImpl(
        StatisticsDetailsRepository statisticsDetailsRepository,
        StatisticsDetailsMapper statisticsDetailsMapper
    ) {
        this.statisticsDetailsRepository = statisticsDetailsRepository;
        this.statisticsDetailsMapper = statisticsDetailsMapper;
    }

    @Override
    public StatisticsDetailsDTO save(StatisticsDetailsDTO statisticsDetailsDTO) {
        LOG.debug("Request to save StatisticsDetails : {}", statisticsDetailsDTO);
        StatisticsDetails statisticsDetails = statisticsDetailsMapper.toEntity(statisticsDetailsDTO);
        statisticsDetails = statisticsDetailsRepository.save(statisticsDetails);
        return statisticsDetailsMapper.toDto(statisticsDetails);
    }

    @Override
    public StatisticsDetailsDTO update(StatisticsDetailsDTO statisticsDetailsDTO) {
        LOG.debug("Request to update StatisticsDetails : {}", statisticsDetailsDTO);
        StatisticsDetails statisticsDetails = statisticsDetailsMapper.toEntity(statisticsDetailsDTO);
        statisticsDetails = statisticsDetailsRepository.save(statisticsDetails);
        return statisticsDetailsMapper.toDto(statisticsDetails);
    }

    @Override
    public Optional<StatisticsDetailsDTO> partialUpdate(StatisticsDetailsDTO statisticsDetailsDTO) {
        LOG.debug("Request to partially update StatisticsDetails : {}", statisticsDetailsDTO);

        return statisticsDetailsRepository
            .findById(statisticsDetailsDTO.getId())
            .map(existingStatisticsDetails -> {
                statisticsDetailsMapper.partialUpdate(existingStatisticsDetails, statisticsDetailsDTO);

                return existingStatisticsDetails;
            })
            .map(statisticsDetailsRepository::save)
            .map(statisticsDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatisticsDetailsDTO> findOne(Long id) {
        LOG.debug("Request to get StatisticsDetails : {}", id);
        return statisticsDetailsRepository.findById(id).map(statisticsDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete StatisticsDetails : {}", id);
        statisticsDetailsRepository.deleteById(id);
    }
}
