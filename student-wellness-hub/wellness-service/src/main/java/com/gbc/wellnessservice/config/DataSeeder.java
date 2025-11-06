package com.gbc.wellnessservice.config;
import com.gbc.wellnessservice.model.WellnessResource;
import com.gbc.wellnessservice.repository.WellnessResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seed(WellnessResourceRepository repo){ return args-> {
        if(repo.count()==0){
            repo.save(new WellnessResource("Campus Counselling","Free on-campus counselling","counseling","https://gbc.ca/counselling"));
            repo.save(new WellnessResource("Mindfulness Basics","Intro to mindfulness","mindfulness","https://example.com/mindfulness"));
        }
    }; }
}
