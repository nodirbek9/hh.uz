package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.employer.Company;

// bu yerda biza ORM sifatida JPA ishlatamiz
// ORM bu Java object larini database table lariga map(ogirib) beradi
//ORM ga misollar: Hibernate, JPA, EntityManager, JdbcTemplate
// JPA bu qolgan ORM lani ustida qurilgan hammasi shundan implementatsiya olgan
//@Repository // JpaRepository interface ni ustida bean qiladigan anotatsiya bolgani uchun bu repostory class lar ustiga bean qiladigan anotatsiya qoyilmaydi
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c from Company c where c.name=?1")
    Company findByNomi(String name);
}
// voris olish uchun kerak boladigan kalit sozlar OOP da
// extend, implement
// interface interface dan extend kalit sozi bilan voris oladi
