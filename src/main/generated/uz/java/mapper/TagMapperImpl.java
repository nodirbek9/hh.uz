package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.tag.TagRequest;
import uz.java.dto.tag.TagResponse;
import uz.java.entity.employer.Tag;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagResponse toTagResponse(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        String name = null;
        Long id = null;
        String code = null;

        name = tag.getTagName();
        id = tag.getId();
        code = tag.getCode();

        TagResponse tagResponse = new TagResponse( id, name, code );

        return tagResponse;
    }

    @Override
    public Tag toTag(TagRequest request) {
        if ( request == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setTagName( request.getTagName() );
        tag.setCode( request.getCode() );

        return tag;
    }

    @Override
    public void updateFromRequest(TagRequest request, Tag oldTag) {
        if ( request == null ) {
            return;
        }

        if ( request.getTagName() != null ) {
            oldTag.setTagName( request.getTagName() );
        }
        if ( request.getCode() != null ) {
            oldTag.setCode( request.getCode() );
        }
    }
}
