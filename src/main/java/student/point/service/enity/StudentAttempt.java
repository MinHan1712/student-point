package student.point.service.enity;

public class StudentAttempt {

    private Long studentId;

    private Long totalAttempt;

    public StudentAttempt() {}

    public StudentAttempt(Long studentId, Long totalAttempt) {
        this.studentId = studentId;
        this.totalAttempt = totalAttempt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(Long totalAttempt) {
        this.totalAttempt = totalAttempt;
    }
}
