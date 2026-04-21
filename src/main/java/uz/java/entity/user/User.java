package uz.java.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.java.entity.enums.Status;
import uz.java.entity.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(unique = true)
    String email;

    @Column(name = "password")
    String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // keyinchalik qoshamiz
}
