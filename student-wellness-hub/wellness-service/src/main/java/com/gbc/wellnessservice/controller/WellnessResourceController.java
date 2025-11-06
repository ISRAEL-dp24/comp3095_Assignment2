package com.gbc.wellnessservice.controller;
import com.gbc.wellnessservice.model.WellnessResource;
import com.gbc.wellnessservice.service.WellnessResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/resources")
public class WellnessResourceController {
    private final WellnessResourceService service;
    public WellnessResourceController(WellnessResourceService service){this.service=service;}
    @GetMapping public List<WellnessResource> all(@RequestParam(required=false) String category,@RequestParam(required=false) String q){
        if(category!=null) return service.findByCategory(category);
        if(q!=null) return service.findAll().stream().filter(r->r.getTitle().toLowerCase().contains(q.toLowerCase()) || (r.getDescription()!=null && r.getDescription().toLowerCase().contains(q.toLowerCase()))).toList();
        return service.findAll();
    }
    @GetMapping("/{id}") public ResponseEntity<WellnessResource> get(@PathVariable Long id){var r=service.findById(id); if(r==null) return ResponseEntity.notFound().build(); return ResponseEntity.ok(r);}
    @PostMapping public ResponseEntity<WellnessResource> create(@RequestBody WellnessResource r){return ResponseEntity.ok(service.create(r));}
    @PutMapping("/{id}") public ResponseEntity<WellnessResource> update(@PathVariable Long id,@RequestBody WellnessResource r){r.setResourceId(id); return ResponseEntity.ok(service.update(r));}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id); return ResponseEntity.noContent().build();}
}
