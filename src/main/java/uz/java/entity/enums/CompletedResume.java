package uz.java.entity.enums;

public enum CompletedResume {
    ADDITIONAL_EDUCATION("Qo'shimcha ta'lim", "Дополнительное образование", "Additional education"),
    TESTS_AND_EXAMINATION("Testlar va imtihonlar", "Тесты и экзамены", "Tests and examination"),
    ELECTRONIC_CERTIFICATES("Elektron sertifikatlar", "Электронные сертификаты", "Electronic certificates"),
    VEHICLE_AVAILABILITY("Transport vositasining mavjudligi", "Наличие транспорта", "Vehicle availability"),
    PORTFOLIO("Portfolio", "Портфолио", "Portfolio"),
    REFERENCES("Tavsiyanomalar", "Рекомендации", "References");

    public String uz;
    public String ru;
    public String en;

    CompletedResume(String uz, String ru, String en) {
        this.uz = uz;
        this.ru = ru;
        this.en = en;
    }
}