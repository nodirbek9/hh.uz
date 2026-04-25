package uz.java.entity.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.java.entity.user.Auditable;
import uz.java.entity.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "search_alerts")
public class SearchAlert extends Auditable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String keyword;

    private String city;

    private String category;
}
