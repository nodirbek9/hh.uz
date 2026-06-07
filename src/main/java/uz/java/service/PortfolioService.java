package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.resume.PortfolioRequest;
import uz.java.dto.resume.PortfolioResponse;
import uz.java.entity.jobseeker.Portfolio;
import uz.java.entity.jobseeker.Resume;
import uz.java.exception.GenericNotFoundException;
import uz.java.repository.PortfolioRepository;
import uz.java.repository.ResumeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ResumeRepository resumeRepository;

    @Transactional
    public Long create(PortfolioRequest request) {
        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new GenericNotFoundException("resume.not.found"));
        Portfolio portfolio = new Portfolio();
        portfolio.setResume(resume);
        portfolio.setImage(request.getImage());
        return portfolioRepository.save(portfolio).getId();
    }

    @Transactional(readOnly = true)
    public List<PortfolioResponse> getByResume(Long resumeId) {
        return portfolioRepository.findByResumeId(resumeId)
                .stream().map(p -> {
                    PortfolioResponse r = new PortfolioResponse();
                    r.setId(p.getId());
                    r.setResumeId(p.getResume().getId());
                    r.setImage(p.getImage());
                    return r;
                }).toList();
    }

    @Transactional
    public Boolean delete(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("portfolio.not.found"));
        portfolio.makeAsDeleted();
        portfolioRepository.save(portfolio);
        return true;
    }
}
