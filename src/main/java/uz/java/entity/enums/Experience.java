package uz.java.entity.enums;

public enum Experience {
    NO_EXPERIENCE("No experience"),
    ONE_THREE("between 1 and 3 years"),
    THREE_SIZ("between 3 to 6 years"),
    SIX_PLUS("more than 6 years");

    private String value;

    Experience(String value) {
        this.value = value;
    }
}
