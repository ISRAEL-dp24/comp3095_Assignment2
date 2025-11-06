package com.gbc.goalservice.repository;
import com.gbc.goalservice.model.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface GoalRepository extends MongoRepository<Goal,String> {
    List<Goal> findByCategoryIgnoreCase(String category);
    List<Goal> findByStatusIgnoreCase(String status);
}
