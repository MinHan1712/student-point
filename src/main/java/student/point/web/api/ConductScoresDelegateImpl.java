package student.point.web.api;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ConductScores;
import student.point.repository.ConductScoresRepository;
import student.point.service.api.dto.ConductScoresCustomDTO;
import student.point.service.mapper.ConductScoresCustomMapper;

@Service
@Transactional
public class ConductScoresDelegateImpl implements ConductScoresApiDelegate {

    private final ConductScoresRepository conductScoresRepository;
    private final ConductScoresCustomMapper conductScoresCustomMapper;

    public ConductScoresDelegateImpl(ConductScoresRepository conductScoresRepository, ConductScoresCustomMapper conductScoresCustomMapper) {
        this.conductScoresRepository = conductScoresRepository;
        this.conductScoresCustomMapper = conductScoresCustomMapper;
    }

    @Override
    public ResponseEntity<Object> multiConductScores(List<@Valid ConductScoresCustomDTO> conductScoresCustomDTO) throws Exception {
        List<ConductScores> conductScores = new ArrayList<>();
        conductScoresCustomDTO.forEach(x -> conductScores.add(conductScoresCustomMapper.toEntity(x)));
        conductScoresRepository.saveAll(conductScores);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
