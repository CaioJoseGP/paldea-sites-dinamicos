package com.jardim.paldea.controller;

import com.jardim.paldea.model.Plant;
import com.jardim.paldea.repository.PlantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlantController {

    private final PlantRepository repository;

    public PlantController(PlantRepository repository) {
        this.repository = repository;
    }

    // ---------- CRUD ----------

    @GetMapping("/plantas")
    public List<Plant> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/plantas/{id}")
    public ResponseEntity<Plant> buscarPorId(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/plantas")
    public Plant criar(@RequestBody Plant plant) {
        return repository.save(plant);
    }

    @PutMapping("/plantas/{id}")
    public ResponseEntity<Plant> atualizar(@PathVariable long id, @RequestBody Plant plant) {
        return repository.update(id, plant)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/plantas/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id) {
        if (repository.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ---------- Promoção (para demonstrar IF) ----------

    @GetMapping("/promocao")
    public Map<String, Object> verificarPromocao() {
        Map<String, Object> promo = new HashMap<>();
        promo.put("ativa", true);
        promo.put("mensagem", "🌿 Promoção de Outono! 20% de desconto em todas as Suculentas!");
        promo.put("desconto", 20);
        return promo;
    }
}
