package pe.edu.vallegrande.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.vallegrande.education.model.Education;
import pe.edu.vallegrande.education.repository.EducationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EducationService {

    @Autowired
    private EducationRepository repository;

    

    public Flux<Education> findAll() {
        return repository.findAll();
    }

    public Mono<Education> findById(Integer id) {
        return repository.findById(id);
    }

    public Mono<Education> save(Education education) {
        return repository.save(education);
    }

    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }

    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }



    public Flux<Education> getByPersonId(Integer personId) {
        return repository.findByPersonId(personId)
                .map(this::convertToDTO);
    }

    private Education convertToDTO(Education education) {
        Education dto = new Education();
        dto.setIdEducation(education.getIdEducation());
        dto.setDegreeStudy(education.getDegreeStudy());
        dto.setGradeBook(education.getGradeBook());
        dto.setGradeAverage(education.getGradeAverage());
        dto.setFullNotebook(education.getFullNotebook());
        dto.setAssistance(education.getAssistance());
        dto.setTutorials(education.getTutorials());
        dto.setPersonId(education.getPersonId());
        return dto;
    }




    public Mono<Education> saveEducationHistory(Education education) {
        Education history = new Education();
        history.setDegreeStudy(education.getDegreeStudy());
        history.setGradeBook(education.getGradeBook());
        history.setGradeAverage(education.getGradeAverage());
        history.setFullNotebook(education.getFullNotebook());
        history.setAssistance(education.getAssistance());
        history.setTutorials(education.getTutorials());
        history.setPersonId(education.getPersonId());

        return repository.save(history);
    }

    public Mono<Education> updateEducation(Integer id, Education education) {
        return repository.findById(id)
                .flatMap(existingEducation -> {
                    return saveEducationHistory(existingEducation)
                            .then(repository.save(education));  
                });
    }
}
