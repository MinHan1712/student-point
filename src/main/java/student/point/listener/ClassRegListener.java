package student.point.listener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import student.point.domain.ClassRegistration;
import student.point.domain.Classes;
import student.point.service.IdGeneratorService;
import student.point.utils.ListenerEnum;

@Component
public class ClassRegListener {

    private final IdGeneratorService idGeneratorService;

    public ClassRegListener(@Lazy IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @PrePersist
    public void generateApplicationRegisterId(ClassRegistration classes) {
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
