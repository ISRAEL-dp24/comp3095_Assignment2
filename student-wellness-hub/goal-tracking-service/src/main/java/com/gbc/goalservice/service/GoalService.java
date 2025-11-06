package com.gbc.goalservice.service;
import com.gbc.goalservice.model.Goal;
import com.gbc.goalservice.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
@Service
public class GoalService {
    private final GoalRepository repo; private final RestTemplate rest = new RestTemplate();
    public GoalService(GoalRepository repo){this.repo=repo;}
    public Goal create(Goal g){return repo.save(g);} public Optional<Goal> findById(String id){return repo.findById(id);} public List<Goal> findAll(){return repo.findAll();}
    public List<Goal> findByCategory(String category){return repo.findByCategoryIgnoreCase(category);} public List<Goal> findByStatus(String status){return repo.findByStatusIgnoreCase(status);} public void delete(String id){repo.deleteById(id);}
    public Goal markCompleted(String id){Goal g=repo.findById(id).orElseThrow(); g.setStatus("completed"); return repo.save(g);}
    public Object suggestResources(String category, String wellnessUrl){ String url = wellnessUrl + "/api/resources?category=" + category; return rest.getForObject(url, Object.class);}
}
