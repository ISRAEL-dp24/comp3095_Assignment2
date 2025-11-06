package com.gbc.wellnessservice.repository;
import com.gbc.wellnessservice.model.WellnessResource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface WellnessResourceRepository extends JpaRepository<WellnessResource, Long> {
    List<WellnessResource> findByCategoryIgnoreCase(String category);
    List<WellnessResource> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String t, String d);
}
