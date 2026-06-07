package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.java.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
