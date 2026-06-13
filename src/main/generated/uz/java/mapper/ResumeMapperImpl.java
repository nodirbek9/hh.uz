package uz.java.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.resume.CertificateShortResponse;
import uz.java.dto.resume.CourseResponse;
import uz.java.dto.resume.PortfolioResponse;
import uz.java.dto.resume.ResumeRequest;
import uz.java.dto.resume.ResumeResponse;
import uz.java.dto.resume.SkillResponse;
import uz.java.entity.employer.Profession;
import uz.java.entity.jobseeker.Certificate;
import uz.java.entity.jobseeker.Course;
import uz.java.entity.jobseeker.Portfolio;
import uz.java.entity.jobseeker.Resume;
import uz.java.entity.jobseeker.Skill;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class ResumeMapperImpl implements ResumeMapper {

    @Override
    public ResumeResponse toResponse(Resume resume) {
        if ( resume == null ) {
            return null;
        }

        ResumeResponse resumeResponse = new ResumeResponse();

        resumeResponse.setProfession( resumeProfessionName( resume ) );
        resumeResponse.setId( resume.getId() );
        if ( resume.getStatus() != null ) {
            resumeResponse.setStatus( resume.getStatus().name() );
        }
        resumeResponse.setAboutMe( resume.getAboutMe() );
        resumeResponse.setCertificateList( certificateListToCertificateShortResponseList( resume.getCertificateList() ) );
        resumeResponse.setSkills( skillSetToSkillResponseSet( resume.getSkills() ) );
        resumeResponse.setCourseList( courseListToCourseResponseList( resume.getCourseList() ) );
        resumeResponse.setPortfolioList( portfolioListToPortfolioResponseList( resume.getPortfolioList() ) );

        return resumeResponse;
    }

    @Override
    public Resume toEntity(ResumeRequest request) {
        if ( request == null ) {
            return null;
        }

        Resume resume = new Resume();

        resume.setType( request.getType() );

        return resume;
    }

    @Override
    public void updateFromRequest(ResumeRequest request, Resume resume) {
        if ( request == null ) {
            return;
        }

        if ( request.getType() != null ) {
            resume.setType( request.getType() );
        }
    }

    private String resumeProfessionName(Resume resume) {
        if ( resume == null ) {
            return null;
        }
        Profession profession = resume.getProfession();
        if ( profession == null ) {
            return null;
        }
        String name = profession.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected CertificateShortResponse certificateToCertificateShortResponse(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String receivedFrom = null;
        LocalDate receivedAt = null;
        String contactName = null;
        LocalDate issueAt = null;

        id = certificate.getId();
        name = certificate.getName();
        receivedFrom = certificate.getReceivedFrom();
        receivedAt = certificate.getReceivedAt();
        contactName = certificate.getContactName();
        issueAt = certificate.getIssueAt();

        CertificateShortResponse certificateShortResponse = new CertificateShortResponse( id, name, receivedFrom, receivedAt, contactName, issueAt );

        return certificateShortResponse;
    }

    protected List<CertificateShortResponse> certificateListToCertificateShortResponseList(List<Certificate> list) {
        if ( list == null ) {
            return null;
        }

        List<CertificateShortResponse> list1 = new ArrayList<CertificateShortResponse>( list.size() );
        for ( Certificate certificate : list ) {
            list1.add( certificateToCertificateShortResponse( certificate ) );
        }

        return list1;
    }

    protected SkillResponse skillToSkillResponse(Skill skill) {
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

    protected Set<SkillResponse> skillSetToSkillResponseSet(Set<Skill> set) {
        if ( set == null ) {
            return null;
        }

        Set<SkillResponse> set1 = new LinkedHashSet<SkillResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Skill skill : set ) {
            set1.add( skillToSkillResponse( skill ) );
        }

        return set1;
    }

    protected CourseResponse courseToCourseResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse courseResponse = new CourseResponse();

        courseResponse.setId( course.getId() );
        courseResponse.setName( course.getName() );
        courseResponse.setOrganization( course.getOrganization() );
        courseResponse.setSpecialisation( course.getSpecialisation() );
        courseResponse.setGraduationYear( course.getGraduationYear() );

        return courseResponse;
    }

    protected List<CourseResponse> courseListToCourseResponseList(List<Course> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseResponse> list1 = new ArrayList<CourseResponse>( list.size() );
        for ( Course course : list ) {
            list1.add( courseToCourseResponse( course ) );
        }

        return list1;
    }

    protected PortfolioResponse portfolioToPortfolioResponse(Portfolio portfolio) {
        if ( portfolio == null ) {
            return null;
        }

        PortfolioResponse portfolioResponse = new PortfolioResponse();

        portfolioResponse.setId( portfolio.getId() );
        portfolioResponse.setImage( portfolio.getImage() );

        return portfolioResponse;
    }

    protected List<PortfolioResponse> portfolioListToPortfolioResponseList(List<Portfolio> list) {
        if ( list == null ) {
            return null;
        }

        List<PortfolioResponse> list1 = new ArrayList<PortfolioResponse>( list.size() );
        for ( Portfolio portfolio : list ) {
            list1.add( portfolioToPortfolioResponse( portfolio ) );
        }

        return list1;
    }
}
