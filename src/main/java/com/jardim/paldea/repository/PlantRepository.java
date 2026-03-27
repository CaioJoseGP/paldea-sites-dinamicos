package com.jardim.paldea.repository;

import com.jardim.paldea.model.Plant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PlantRepository {

    private final List<Plant> plantas = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public PlantRepository() {
        // Dados mock pré-carregados
        save(new Plant(0, "Samambaia", "Planta ornamental para ambientes internos e externos", 25.90, "Folhagem"));
        save(new Plant(0, "Rosa Vermelha", "Clássica rosa vermelha para jardins e vasos", 18.50, "Flores"));
        save(new Plant(0, "Lavanda", "Planta aromática ideal para bordaduras", 22.00, "Aromáticas"));
        save(new Plant(0, "Manjericão", "Erva culinária fresca, ótima para temperos", 8.90, "Ervas"));
        save(new Plant(0, "Suculenta Echeveria", "Suculenta compacta e fácil de cuidar", 15.00, "Suculentas"));
        save(new Plant(0, "Palmeira Ráfis", "Palmeira elegante para interiores", 89.90, "Palmeiras"));
    }

    public List<Plant> findAll() {
        return new ArrayList<>(plantas);
    }

    public Optional<Plant> findById(long id) {
        return plantas.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Plant save(Plant plant) {
        plant.setId(idCounter.incrementAndGet());
        plantas.add(plant);
        return plant;
    }

    public Optional<Plant> update(long id, Plant updated) {
        for (int i = 0; i < plantas.size(); i++) {
            if (plantas.get(i).getId() == id) {
                updated.setId(id);
                plantas.set(i, updated);
                return Optional.of(updated);
            }
        }
        return Optional.empty();
    }

    public boolean deleteById(long id) {
        return plantas.removeIf(p -> p.getId() == id);
    }
}
