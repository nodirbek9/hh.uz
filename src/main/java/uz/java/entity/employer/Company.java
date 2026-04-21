package uz.java.entity.employer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    public User user;

    private String name;

    private String description;

    private String logo;

    private String country;

    private String city;

    @Column(columnDefinition = "VARCHAR DEFAULT 998 ")
    private String phone;

    @Column(unique = true)
    private String email;

    @Column(name = "is_verified")
    private String isVerified;

    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();
}
