package student.point.listener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import student.point.domain.Classes;
import student.point.domain.Student;
import student.point.service.IdGeneratorService;
import student.point.utils.ListenerEnum;

@Component
public class ClassListener {

    private final IdGeneratorService idGeneratorService;

    public ClassListener(@Lazy IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @PrePersist
    public void generateApplicationRegisterId(Classes classes) {
        if (classes.getClassCode() == null) {
            classes.setClassCode(idGeneratorService.generateNextId(ListenerEnum.CLASS.getCode()));
        }

        if (classes.getCreatedDate() == null) {
            classes.setCreatedDate(Instant.now());
        }
        if (StringUtils.isBlank(classes.getCreatedBy())) {
            classes.setCreatedBy("admin");
        }
        if (classes.getLastModifiedDate() == null) {
            classes.setLastModifiedDate(Instant.now());
        }
        if (StringUtils.isBlank(classes.getLastModifiedBy())) {
            classes.setLastModifiedBy("admin");
        }
    }
}
