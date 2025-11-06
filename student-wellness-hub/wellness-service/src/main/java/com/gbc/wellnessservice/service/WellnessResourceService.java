package com.gbc.wellnessservice.service;
import com.gbc.wellnessservice.model.WellnessResource;
import com.gbc.wellnessservice.repository.WellnessResourceRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class WellnessResourceService {
    private final WellnessResourceRepository repo;
    public WellnessResourceService(WellnessResourceRepository repo){this.repo=repo;}
    @Cacheable(value="resourcesAll") public List<WellnessResource> findAll(){return repo.findAll();}
    @Cacheable(value="resourcesByCategory", key="#category") public List<WellnessResource> findByCategory(String category){return repo.findByCategoryIgnoreCase(category);}
    public WellnessResource findById(Long id){return repo.findById(id).orElse(null);}
    @CachePut(value="resource", key="#result.resourceId") public WellnessResource create(WellnessResource r){return repo.save(r);}
    @CacheEvict(value={"resourcesAll","resourcesByCategory","resource"}, allEntries=true) public void delete(Long id){repo.deleteById(id);}
    @CacheEvict(value={"resourcesAll","resourcesByCategory"}, allEntries=true) @CachePut(value="resource", key="#updated.resourceId") public WellnessResource update(WellnessResource updated){return repo.save(updated);}
}
