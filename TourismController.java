package com.tourism.guide;

import com.tourism.guide.model.Site;
import com.tourism.guide.repository.SiteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class TourismController {
    private final SiteRepository repo;
    public TourismController(SiteRepository repo){this.repo=repo;}

    @GetMapping("/sites") public List<Site> sites(@RequestParam(required=false) String q){
        if(q==null || q.isBlank()) return repo.findAll();
        return repo.findByNameContainingIgnoreCaseOrStateContainingIgnoreCaseOrCategoryContainingIgnoreCase(q,q,q);
    }
    @GetMapping("/sites/{id}") public ResponseEntity<Site> site(@PathVariable Long id){return repo.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
    @PostMapping("/sites") public ResponseEntity<Site> create(@Valid @RequestBody Site site){return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(site));}
    @PutMapping("/sites/{id}") public ResponseEntity<Site> update(@PathVariable Long id,@Valid @RequestBody Site incoming){
        return repo.findById(id).map(s->{s.setName(incoming.getName());s.setState(incoming.getState());s.setCategory(incoming.getCategory());s.setLat(incoming.getLat());s.setLon(incoming.getLon());s.setDescription(incoming.getDescription());s.setHistory(incoming.getHistory());s.setFood(incoming.getFood());s.setHidden(incoming.getHidden());s.setAudio(incoming.getAudio());return ResponseEntity.ok(repo.save(s));}).orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/sites/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(!repo.existsById(id))return ResponseEntity.notFound().build();repo.deleteById(id);return ResponseEntity.noContent().build();}

    public record Ask(@NotBlank String message,String site){}
    @PostMapping("/assistant") public Map<String,String> assistant(@Valid @RequestBody Ask a){
        String q=a.message().toLowerCase(Locale.ROOT); String site=a.site()==null?"this destination":a.site();
        String r=q.contains("food")?"Try the local specialty listed for "+site+" and explore nearby traditional markets.":q.contains("history")?site+" has a rich heritage story. Open the History section for the short guide.":q.contains("time")?"Early morning or late afternoon is usually better for outdoor heritage sites because of lighter heat.":q.contains("plan")||q.contains("itinerary")?"For a short visit, choose two nearby attractions and leave time for food and photography.":"I can help with history, best visiting time, local food, hidden gems, directions and trip planning for "+site+".";
        return Map.of("reply",r);
    }
}
