package student.point.listener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import student.point.domain.Grades;
import student.point.domain.Student;
import student.point.service.IdGeneratorService;
import student.point.utils.ListenerEnum;

@Component
public class GradesListener {

    private final IdGeneratorService idGeneratorService;

    public GradesListener(@Lazy IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @PrePersist
    public void generateApplicationRegisterId(Grades application) {
        if (application.getGradesCode() == null) {
            application.setGradesCode(idGeneratorService.generateNextId(ListenerEnum.GRADES.getCode()));
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
