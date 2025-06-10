package student.point.service.dto;

public class SemesterGradeSummaryDTO {

    private Long studentId;
    private String fullName;
    private String academicYear;
    private Integer totalCredits;
    private Double avgScore10;
    private Double avgScore4;
    private String semesterRanking;
    private Double overallAvgScore10;
    private Double overallAvgScore4;
    private String overallRanking;

    public SemesterGradeSummaryDTO(
        Long studentId,
        String fullName,
        String academicYear,
        Integer totalCredits,
        Double avgScore10,
        Double avgScore4,
        String semesterRanking,
        Double overallAvgScore10,
        Double overallAvgScore4,
        String overallRanking
    ) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.academicYear = academicYear;
        this.totalCredits = totalCredits;
        this.avgScore10 = avgScore10;
        this.avgScore4 = avgScore4;
        this.semesterRanking = semesterRanking;
        this.overallAvgScore10 = overallAvgScore10;
        this.overallAvgScore4 = overallAvgScore4;
        this.overallRanking = overallRanking;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Double getAvgScore10() {
        return avgScore10;
    }

    public void setAvgScore10(Double avgScore10) {
        this.avgScore10 = avgScore10;
    }

    public Double getAvgScore4() {
        return avgScore4;
    }

    public void setAvgScore4(Double avgScore4) {
        this.avgScore4 = avgScore4;
    }

    public String getSemesterRanking() {
        return semesterRanking;
    }

    public void setSemesterRanking(String semesterRanking) {
        this.semesterRanking = semesterRanking;
    }

    public Double getOverallAvgScore10() {
        return overallAvgScore10;
    }

    public void setOverallAvgScore10(Double overallAvgScore10) {
        this.overallAvgScore10 = overallAvgScore10;
    }

    public Double getOverallAvgScore4() {
        return overallAvgScore4;
    }

    public void setOverallAvgScore4(Double overallAvgScore4) {
        this.overallAvgScore4 = overallAvgScore4;
    }

    public String getOverallRanking() {
        return overallRanking;
    }

    public void setOverallRanking(String overallRanking) {
        this.overallRanking = overallRanking;
    }
}
