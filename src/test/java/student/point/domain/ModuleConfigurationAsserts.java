package student.point.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuleConfigurationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleConfigurationAllPropertiesEquals(ModuleConfiguration expected, ModuleConfiguration actual) {
        assertModuleConfigurationAutoGeneratedPropertiesEquals(expected, actual);
        assertModuleConfigurationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleConfigurationAllUpdatablePropertiesEquals(ModuleConfiguration expected, ModuleConfiguration actual) {
        assertModuleConfigurationUpdatableFieldsEquals(expected, actual);
        assertModuleConfigurationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleConfigurationAutoGeneratedPropertiesEquals(ModuleConfiguration expected, ModuleConfiguration actual) {
        assertThat(expected)
            .as("Verify ModuleConfiguration auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()))
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()))
            .satisfies(e -> assertThat(e.getLastModifiedBy()).as("check lastModifiedBy").isEqualTo(actual.getLastModifiedBy()))
            .satisfies(e -> assertThat(e.getLastModifiedDate()).as("check lastModifiedDate").isEqualTo(actual.getLastModifiedDate()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleConfigurationUpdatableFieldsEquals(ModuleConfiguration expected, ModuleConfiguration actual) {
        assertThat(expected)
            .as("Verify ModuleConfiguration relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getPrefix()).as("check prefix").isEqualTo(actual.getPrefix()))
            .satisfies(e -> assertThat(e.getPadding()).as("check padding").isEqualTo(actual.getPadding()))
            .satisfies(e -> assertThat(e.getNumberNextActual()).as("check numberNextActual").isEqualTo(actual.getNumberNextActual()))
            .satisfies(e -> assertThat(e.getNumberIncrement()).as("check numberIncrement").isEqualTo(actual.getNumberIncrement()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleConfigurationUpdatableRelationshipsEquals(ModuleConfiguration expected, ModuleConfiguration actual) {
        // empty method
    }
}
