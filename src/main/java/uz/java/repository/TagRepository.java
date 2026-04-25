package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.employer.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query("select t from Tag t where t.tagName=?1")
    Tag findByTagName(String tagName);
}
