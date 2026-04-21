package uz.java.entity.enums;

public enum LanguageLevel {
    BASIC("A1 - Basic"),
    ELEMENTARY("A2 - Elementary"),
    INTERMEDIATE("B1 - Intermediate"),
    UPPER_INTERMEDIATE("B2 - Upper Intermediate"),
    ADVANCED("C1 - Advanced"),
    PROFICIENCY("C2 - Proficiency");

    private String value;

    LanguageLevel(String s) {
        this.value = s;
    }
}
