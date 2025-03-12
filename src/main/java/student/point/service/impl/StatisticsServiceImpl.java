package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Statistics;
import student.point.repository.StatisticsRepository;
import student.point.service.StatisticsService;
import student.point.service.dto.StatisticsDTO;
import student.point.service.mapper.StatisticsMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Statistics}.
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private final StatisticsRepository statisticsRepository;

    private final StatisticsMapper statisticsMapper;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, StatisticsMapper statisticsMapper) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsMapper = statisticsMapper;
    }

    @Override
    public StatisticsDTO save(StatisticsDTO statisticsDTO) {
        LOG.debug("Request to save Statistics : {}", statisticsDTO);
        Statistics statistics = statisticsMapper.toEntity(statisticsDTO);
        statistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(statistics);
    }

    @Override
    public StatisticsDTO update(StatisticsDTO statisticsDTO) {
        LOG.debug("Request to update Statistics : {}", statisticsDTO);
        Statistics statistics = statisticsMapper.toEntity(statisticsDTO);
        statistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(statistics);
    }

    @Override
    public Optional<StatisticsDTO> partialUpdate(StatisticsDTO statisticsDTO) {
        LOG.debug("Request to partially update Statistics : {}", statisticsDTO);

        return statisticsRepository
            .findById(statisticsDTO.getId())
            .map(existingStatistics -> {
                statisticsMapper.partialUpdate(existingStatistics, statisticsDTO);

                return existingStatistics;
            })
            .map(statisticsRepository::save)
            .map(statisticsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatisticsDTO> findOne(Long id) {
        LOG.debug("Request to get Statistics : {}", id);
        return statisticsRepository.findById(id).map(statisticsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Statistics : {}", id);
        statisticsRepository.deleteById(id);
    }
}
