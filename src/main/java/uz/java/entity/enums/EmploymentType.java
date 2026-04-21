package uz.java.entity.enums;

public enum EmploymentType {
    FULL_TIME("Full-time"),
    PART_TIME("Part-time"),
    REMOTE("Remote"),
    INTERNSHIP("Internship");

    private String value;

    EmploymentType(String s) {
        this.value = s;
    }
}
