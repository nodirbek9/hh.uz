package uz.java.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(value = {
        "createdAt", "updatedAt", "createdBy", "updatedBy"
}, allowGetters = true)
@EntityListeners(value = AuditingEntityListener.class)
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    // jpa orqali save bolganda qaysidir entity shuni createdAt ga hozirgi vaqtni automatik set qiladi
    @JsonIgnore
    LocalDateTime createdAt;

    @LastModifiedDate
    @JsonIgnore
    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    @CreatedBy
    @JsonIgnore
    Long createdBy;

    @LastModifiedBy
    @JsonIgnore
    Long updatedBy;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean deleted;

    public void makeAsDeleted() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
