package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.SkillRequest;
import uz.java.dto.resume.SkillResponse;
import uz.java.entity.enums.SkillLevel;
import uz.java.entity.jobseeker.Skill;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public SkillResponse toResponse(Skill skill) {
        if ( skill == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String code = null;

        id = skill.getId();
        name = skill.getName();
        code = skill.getCode();

        SkillResponse skillResponse = new SkillResponse( id, name, code );

        return skillResponse;
    }

    @Override
    public Skill toEntity(SkillRequest request) {
        if ( request == null ) {
            return null;
        }

        Skill skill = new Skill();

        skill.setName( request.getName() );
        if ( request.getSkillLevel() != null ) {
            skill.setSkillLevel( Enum.valueOf( SkillLevel.class, request.getSkillLevel() ) );
        }

        return skill;
    }

    @Override
    public void updateFromRequest(SkillRequest request, Skill oldskill) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            oldskill.setName( request.getName() );
        }
        if ( request.getSkillLevel() != null ) {
            oldskill.setSkillLevel( Enum.valueOf( SkillLevel.class, request.getSkillLevel() ) );
        }
    }
}
