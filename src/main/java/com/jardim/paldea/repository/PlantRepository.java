package com.jardim.paldea.repository;

import com.jardim.paldea.model.Plant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PlantRepository {

    private final Map<Long, Plant> plants = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public PlantRepository() {
        save("Samambaia Imperial", "Folhagem de sombra para ambientes internos e varandas.", "Folhagem", 42.90);
        save("Lavanda Francesa", "Aromatica perfumada, ideal para canteiros ensolarados.", "Aromaticas", 28.00);
        save("Rosa do Deserto", "Flor de alto impacto visual para vitrines e presentes.", "Flores", 36.50);
        save("Suculenta Echeveria", "Pequena, resistente e perfeita para mesas e aparadores.", "Suculentas", 18.90);
        save("Palmeira Rafis", "Composicao elegante para recepcoes e salas amplas.", "Palmeiras", 96.00);
        save("Alecrim", "Erva fresca para culinaria e jardins sensoriais.", "Ervas", 14.50);
    }

    public List<Plant> findAll() {
        return new ArrayList<>(plants.values());
    }

    public Optional<Plant> findById(long id) {
        return Optional.ofNullable(plants.get(id));
    }

    public Plant save(String nome, String descricao, String categoria, double preco) {
        long id = sequence.incrementAndGet();
        Plant plant = new Plant(id, nome, descricao, categoria, preco);
        plants.put(id, plant);
        return plant;
    }

    public Plant save(Plant plant) {
        if (plant.getId() == 0L) {
            plant.setId(sequence.incrementAndGet());
        }
        plants.put(plant.getId(), plant);
        return plant;
    }

    public Optional<Plant> update(long id, Plant updatedPlant) {
        if (!plants.containsKey(id)) {
            return Optional.empty();
        }
        updatedPlant.setId(id);
        plants.put(id, updatedPlant);
        return Optional.of(updatedPlant);
    }

    public boolean delete(long id) {
        return plants.remove(id) != null;
    }
}
