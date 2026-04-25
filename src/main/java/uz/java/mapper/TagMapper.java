package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.entity.employer.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(source = "tagName", target = "name")
    TagResponse toTagResponse(Tag tag);

    Tag toTag(TagRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(TagRequest request, @MappingTarget Tag oldTag);
}
