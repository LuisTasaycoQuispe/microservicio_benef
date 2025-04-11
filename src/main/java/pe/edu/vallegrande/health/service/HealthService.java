package pe.edu.vallegrande.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.vallegrande.health.model.Health;
import pe.edu.vallegrande.health.repository.HealthRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class HealthService {

    @Autowired
    private HealthRepository repository;

    public Flux<Health> findAll() {
        return repository.findAll();
    }

    public Mono<Health> findById(Integer id) {
        return repository.findById(id);
    }

    public Mono<Health> save(Health health) {
        return repository.save(health);
    }

    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }

    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }



    public Flux<Health> getByPersonId(Integer personId) {
        return repository.findByPersonId(personId)
                .map(this::convertToDTO);
    }


    private Health convertToDTO(Health health) {
        Health dto = new Health();

        dto.setIdHealth(health.getIdHealth());
        dto.setVaccine(health.getVaccine());
        dto.setVph(health.getVph());
        dto.setInfluenza(health.getInfluenza());
        dto.setDeworming(health.getDeworming());
        dto.setHemoglobin(health.getHemoglobin());
        dto.setPersonId(health.getPersonId());
        return dto;
    }
    


    

    public Mono<Health> saveHealthHistory(Health health) {
        Health history = new Health();
        history.setVaccine(health.getVaccine());
        history.setVph(health.getVph());
        history.setInfluenza(health.getInfluenza());
        history.setDeworming(health.getDeworming());
        history.setHemoglobin(health.getHemoglobin());
        history.setPersonId(health.getPersonId());

        return repository.save(history);
    }

    public Mono<Health> updateHealth(Integer id, Health health) {
        return repository.findById(id)
                .flatMap(existingHealth -> {
                    return saveHealthHistory(existingHealth)
                            .then(repository.save(health));  
                });
    }

}
