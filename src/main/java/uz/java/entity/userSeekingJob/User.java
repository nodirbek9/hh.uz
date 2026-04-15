package uz.java.entity.userSeekingJob;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity  // Entity anotatsiya shu class bazada ham shunaqa nomli table yaratmoqchimiz
@Getter // getter method uchun
@Setter // setter method
@AllArgsConstructor // hamma construktor yaratadi
@NoArgsConstructor // bo'sh construktor yaratadi
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id  // id ni primary key qilib beradigan anotatsiya
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "password")
    String password;

//    OneToMany
//    ManyToOne    boglanish turlari
//    ManyToMany
//    OneToOne
//    private Resume resume;

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    Status status;


    // keyinchalik qoshamiz
}
