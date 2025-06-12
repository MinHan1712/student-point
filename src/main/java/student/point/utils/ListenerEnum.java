package student.point.utils;

public enum ListenerEnum {
    CLASS("CLASS"),
    CONDUCT_SCORE("CONDUCT_SCORE"),
    COURSE("COURSE"),
    FACULTIES("FACULTIES"),
    GRADES("GRADES"),
    STATISTICS("STATISTICS"),
    STATISTICS_DETAIL("STATISTICS_DETAIL"),
    STUDENT("STUDENT"),
    TEACHERS("TEACHERS");

    private String code;

    ListenerEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
