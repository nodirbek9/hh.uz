package uz.java.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.company.CompanyRequest;
import uz.java.dto.company.CompanyResponse;
import uz.java.entity.employer.Company;
import uz.java.entity.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyResponse toResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setOwnerId( companyUserId( company ) );
        companyResponse.setId( company.getId() );
        companyResponse.setName( company.getName() );
        companyResponse.setDescription( company.getDescription() );
        companyResponse.setLogo( company.getLogo() );
        companyResponse.setPhone( company.getPhone() );
        companyResponse.setEmail( company.getEmail() );
        List<String> list = company.getImageUrls();
        if ( list != null ) {
            companyResponse.setImageUrls( new ArrayList<String>( list ) );
        }

        return companyResponse;
    }

    @Override
    public Company toCompany(CompanyRequest request) {
        if ( request == null ) {
            return null;
        }

        Company company = new Company();

        company.setPhone( request.getPhoneNumber() );
        company.setName( request.getName() );
        company.setDescription( request.getDescription() );
        company.setEmail( request.getEmail() );

        return company;
    }

    @Override
    public void updateFromRequest(CompanyRequest request, Company company) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            company.setName( request.getName() );
        }
        if ( request.getDescription() != null ) {
            company.setDescription( request.getDescription() );
        }
        if ( request.getEmail() != null ) {
            company.setEmail( request.getEmail() );
        }
    }

    private Long companyUserId(Company company) {
        if ( company == null ) {
            return null;
        }
        User user = company.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
