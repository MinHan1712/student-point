package student.point.listener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import student.point.domain.ConductScores;
import student.point.domain.Student;
import student.point.service.IdGeneratorService;
import student.point.utils.ListenerEnum;

@Component
public class ConductScoreListener {

    private final IdGeneratorService idGeneratorService;

    public ConductScoreListener(@Lazy IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @PrePersist
    public void generateApplicationRegisterId(ConductScores application) {
        if (application.getConductScoresCode() == null) {
            application.setConductScoresCode(idGeneratorService.generateNextId(ListenerEnum.CONDUCT_SCORE.getCode()));
        }

        if (application.getCreatedDate() == null) {
            application.setCreatedDate(Instant.now());
        }
        if (StringUtils.isBlank(application.getCreatedBy())) {
            application.setCreatedBy("admin");
        }
        if (application.getLastModifiedDate() == null) {
            application.setLastModifiedDate(Instant.now());
        }
        if (StringUtils.isBlank(application.getLastModifiedBy())) {
            application.setLastModifiedBy("admin");
        }
    }
}
