package student.point.listener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import student.point.domain.StatisticsDetails;
import student.point.domain.Student;
import student.point.service.IdGeneratorService;
import student.point.utils.ListenerEnum;

@Component
public class StatisticDetailListener {

    private final IdGeneratorService idGeneratorService;

    public StatisticDetailListener(@Lazy IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @PrePersist
    public void generateApplicationRegisterId(StatisticsDetails application) {
        if (application.getCode() == null) {
            application.setCode(idGeneratorService.generateNextId(ListenerEnum.STATISTICS_DETAIL.getCode()));
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
