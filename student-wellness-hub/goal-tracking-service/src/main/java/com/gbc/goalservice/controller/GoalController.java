package com.gbc.goalservice.controller;
import com.gbc.goalservice.model.Goal;
import com.gbc.goalservice.service.GoalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService service;
    @Value("${WELLNESS_SERVICE_URL:http://wellness-service:8081}") private String wellnessUrl;
    public GoalController(GoalService service){this.service=service;}
    @PostMapping public ResponseEntity<Goal> create(@RequestBody Goal g){return ResponseEntity.ok(service.create(g));}
    @GetMapping public List<Goal> all(@RequestParam(required=false) String category,@RequestParam(required=false) String status){
        if(category!=null) return service.findByCategory(category); if(status!=null) return service.findByStatus(status); return service.findAll();
    }
    @GetMapping("/{id}") public ResponseEntity<Goal> get(@PathVariable String id){ return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping("/{id}/complete") public ResponseEntity<Goal> complete(@PathVariable String id){ return ResponseEntity.ok(service.markCompleted(id)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id){ service.delete(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/{id}/suggest-resources") public ResponseEntity<Object> suggest(@PathVariable String id){ return service.findById(id).map(g -> ResponseEntity.ok(service.suggestResources(g.getCategory(), wellnessUrl))).orElseGet(() -> ResponseEntity.notFound().build()); }
}
