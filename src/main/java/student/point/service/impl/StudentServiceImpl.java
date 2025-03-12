package student.point.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.Student;
import student.point.repository.StudentRepository;
import student.point.service.StudentService;
import student.point.service.dto.StudentDTO;
import student.point.service.mapper.StudentMapper;

/**
 * Service Implementation for managing {@link student.point.domain.Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        LOG.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDTO update(StudentDTO studentDTO) {
        LOG.debug("Request to update Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public Optional<StudentDTO> partialUpdate(StudentDTO studentDTO) {
        LOG.debug("Request to partially update Student : {}", studentDTO);

        return studentRepository
            .findById(studentDTO.getId())
            .map(existingStudent -> {
                studentMapper.partialUpdate(existingStudent, studentDTO);

                return existingStudent;
            })
            .map(studentRepository::save)
            .map(studentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        LOG.debug("Request to get Student : {}", id);
        return studentRepository.findById(id).map(studentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
