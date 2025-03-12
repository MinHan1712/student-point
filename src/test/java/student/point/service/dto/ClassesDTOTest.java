package student.point.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import student.point.web.rest.TestUtil;

class ClassesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassesDTO.class);
        ClassesDTO classesDTO1 = new ClassesDTO();
        classesDTO1.setId(1L);
        ClassesDTO classesDTO2 = new ClassesDTO();
        assertThat(classesDTO1).isNotEqualTo(classesDTO2);
        classesDTO2.setId(classesDTO1.getId());
        assertThat(classesDTO1).isEqualTo(classesDTO2);
        classesDTO2.setId(2L);
        assertThat(classesDTO1).isNotEqualTo(classesDTO2);
        classesDTO1.setId(null);
        assertThat(classesDTO1).isNotEqualTo(classesDTO2);
    }
}
