package uz.java.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.java.entity.enums.UserRole;

@Entity  // Entity anotatsiya shu class bazada ham shunaqa nomli table yaratmoqchimiz
@Getter // getter method uchun
@Setter // setter method
@AllArgsConstructor // hamma construktor yaratadi
@NoArgsConstructor // bo'sh construktor yaratadi
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Auditable {

    @Column(unique = true)
    String email;

    @Column(name = "password")
    String password;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "is_verified")
    Boolean isVerified;

}
