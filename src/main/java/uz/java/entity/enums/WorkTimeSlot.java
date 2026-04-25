package uz.java.entity.enums;

public enum WorkTimeSlot {
    FIVE_TWO("5/2", "5/2", "5/2"),
    SIX_ONE("6/1", "6/1", "6/1"),
    TWO_TWO("2/2", "2/2", "2/2"),
    THREE_TWO("3/2", "3/2", "3/2"),
    FOUR_THREE("4/3", "4/3", "4/3"),
    WEEKENDS("Dam olish", "По выходным", "WEEKENDS"),
    FLEXIBLE("Erkin grafik", "Свободный","FLEXIBLE"),
    OTHER("Boshqa", "Другое","OTHER");

    private String valueUz;
    private String valueRu;
    private String valueEn;

    WorkTimeSlot(String valueUz, String valueRu, String valueEn) {
        this.valueUz = valueUz;
        this.valueRu = valueRu;
        this.valueEn = valueEn;
    }
}
