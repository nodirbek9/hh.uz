package uz.java.entity.enums;

public enum EducationDegree {
    SECONDARY("Secondary", "Среднее", "O‘rta"),
    SECONDARY_VOCATIONAL("Secondary vocational", "Среднее специальное", "O‘rta maxsus"),
    INCOMPLETE_HIGHER_EDUCATION("Incomplete higher education", "Неоконченное высшее", "Oliy ma’lumotsiz"),
    HIGHER_EDUCATION("Higher education", "Высшее", "Oliy ma’lumotli"),
    BACHELORS_DEGREE("Bachelor's degree", "Бакалавр", "Bakalavr darajasi"),
    MASTERS_DEGREE("Master's degree", "Магистр", "Magistr darajasi"),
    PHD("PhD", "Кандидат наук", "PhD"),
    DOCTOR_OF_SCIENCE("Doctor of Science", "Доктор наук", "Fan doktori");

    private String valueEn;
    private String valueRu;
    private String valueUz;

    EducationDegree(String valueEn, String valueRu, String valueUz) {
        this.valueEn = valueEn;
        this.valueRu = valueRu;
        this.valueUz = valueUz;
    }
}