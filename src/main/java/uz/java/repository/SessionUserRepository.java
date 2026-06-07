package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.user.SessionUser;

public interface SessionUserRepository extends JpaRepository<SessionUser, Long> {
    SessionUser findByUserId(Long id);
}
