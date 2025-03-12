package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Teachers;
import student.point.repository.TeachersRepository;
import student.point.service.TeachersService;
import student.point.service.dto.TeachersDTO;
import student.point.service.mapper.TeachersMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Teachers}.
 */
@Service
@Transactional
public class TeachersServiceImpl implements TeachersService {

    private static final Logger LOG = LoggerFactory.getLogger(TeachersServiceImpl.class);

    private final TeachersRepository teachersRepository;

    private final TeachersMapper teachersMapper;

    public TeachersServiceImpl(TeachersRepository teachersRepository, TeachersMapper teachersMapper) {
        this.teachersRepository = teachersRepository;
        this.teachersMapper = teachersMapper;
    }

    @Override
    public TeachersDTO save(TeachersDTO teachersDTO) {
        LOG.debug("Request to save Teachers : {}", teachersDTO);
        Teachers teachers = teachersMapper.toEntity(teachersDTO);
        teachers = teachersRepository.save(teachers);
        return teachersMapper.toDto(teachers);
    }

    @Override
    public TeachersDTO update(TeachersDTO teachersDTO) {
        LOG.debug("Request to update Teachers : {}", teachersDTO);
        Teachers teachers = teachersMapper.toEntity(teachersDTO);
        teachers = teachersRepository.save(teachers);
        return teachersMapper.toDto(teachers);
    }

    @Override
    public Optional<TeachersDTO> partialUpdate(TeachersDTO teachersDTO) {
        LOG.debug("Request to partially update Teachers : {}", teachersDTO);

        return teachersRepository
            .findById(teachersDTO.getId())
            .map(existingTeachers -> {
                teachersMapper.partialUpdate(existingTeachers, teachersDTO);

                return existingTeachers;
            })
            .map(teachersRepository::save)
            .map(teachersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeachersDTO> findOne(Long id) {
        LOG.debug("Request to get Teachers : {}", id);
        return teachersRepository.findById(id).map(teachersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Teachers : {}", id);
        teachersRepository.deleteById(id);
    }
}
