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

    @Column(name = "first_name")
    String firstName;

//    OneToMany
//    ManyToOne    boglanish turlari
//    ManyToMany
//    OneToOne
//    private Resume resume;



    @Column(unique = true)
    String username;

    @Column(name = "last_name")
    String lastName;

    @Column(unique = true)
    String email;

    @Column(columnDefinition = "VARCHAR DEFAULT 998 ")
    String phone;

    String photoUrl;

    // keyinchalik qoshamiz
}
