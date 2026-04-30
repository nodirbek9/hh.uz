package uz.java.entity.enums;

public enum SkillLevel {
    BASIC("Basic", "Bazoviy", "Boshlangich"),
    MEDIUM("Medium", "Credniy", "O'rtacha"),
    HIGHER("Higher", "Prodvinutniy", "Yuqori");

    private String valueEn;
    private String valueRu;
    private String valueUz;

    SkillLevel(String valueEn, String valueRu, String valueUz) {
        this.valueEn = valueEn;
        this.valueRu = valueRu;
        this.valueUz = valueUz;
    }
}
