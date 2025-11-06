package com.gbc.goalservice.config;
import com.gbc.goalservice.model.Goal;
import com.gbc.goalservice.repository.GoalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seed(GoalRepository repo){ return args -> {
        if(repo.count()==0){
            Goal g1 = new Goal(); g1.setTitle("Meditate daily"); g1.setDescription("10 mins"); g1.setCategory("mindfulness"); g1.setStatus("in-progress"); g1.setTargetDate(LocalDate.now().plusDays(30)); repo.save(g1);
        }
    }; }
}
