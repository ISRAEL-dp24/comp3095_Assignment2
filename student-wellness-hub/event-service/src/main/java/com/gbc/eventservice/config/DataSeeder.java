package com.gbc.eventservice.config;
import com.gbc.eventservice.model.Event;
import com.gbc.eventservice.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seed(EventRepository repo){ return args -> {
        if(repo.count()==0){
            Event e1 = new Event(); e1.setTitle("Yoga"); e1.setDescription("Community yoga"); e1.setDate(LocalDateTime.now().plusDays(5)); e1.setLocation("Studio A"); e1.setCapacity(30); repo.save(e1);
        }
    }; }
}
