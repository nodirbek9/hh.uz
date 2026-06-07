package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.dto.tag.TagResponse;
import uz.java.entity.employer.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query("select t from Tag t where t.tagName=?1")
    Tag findByTagName(String tagName);

    @Query("select new uz.java.dto.tag.TagResponse(t.id, t.tagName, t.code) from Tag t where t.deletedAt is null and t.tagName ilike '%' || :search || '%' ")
    List<TagResponse> findAllCustom(String search);
}
