package student.point.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.point.domain.ModuleConfiguration;
import student.point.repository.ModuleConfigurationRepository;

@Service
@Transactional
public class IdGeneratorService {

    private final ModuleConfigurationRepository moduleConfigurationRepository;

    public IdGeneratorService(ModuleConfigurationRepository moduleConfigurationRepository) {
        this.moduleConfigurationRepository = moduleConfigurationRepository;
    }

    public String generateNextId(String name) {
        ModuleConfiguration moduleConfiguration = moduleConfigurationRepository
            .findOneForUpdateByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Module configuration not found"));

        int numberDigits = String.valueOf(moduleConfiguration.getNumberNextActual()).length();
        String prefix = StringUtils.isBlank(moduleConfiguration.getPrefix()) ? Strings.EMPTY : moduleConfiguration.getPrefix();
        String nextId;

        if (numberDigits <= moduleConfiguration.getPadding()) {
            nextId = prefix + String.format("%0" + moduleConfiguration.getPadding() + "d", moduleConfiguration.getNumberNextActual());
        } else {
            throw new IllegalArgumentException("Number of digits exceeds padding");
        }

        moduleConfiguration.setNumberNextActual(moduleConfiguration.getNumberNextActual() + moduleConfiguration.getNumberIncrement());
        moduleConfigurationRepository.save(moduleConfiguration);

        return nextId;
    }
}
