package com.jardim.paldea.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlantCatalog {

    private final List<Plant> plants = new ArrayList<>();
    private long nextId = 1L;

    public PlantCatalog() {
        addInitialPlant("Samambaia Imperial", "Folhagem de sombra para ambientes internos e varandas.", "Folhagem", 42.90);
        addInitialPlant("Lavanda Francesa", "Aromatica perfumada, ideal para canteiros ensolarados.", "Aromaticas", 28.00);
        addInitialPlant("Rosa do Deserto", "Flor de alto impacto visual para vitrines e presentes.", "Flores", 36.50);
        addInitialPlant("Suculenta Echeveria", "Pequena, resistente e perfeita para mesas e aparadores.", "Suculentas", 18.90);
        addInitialPlant("Palmeira Rafis", "Composicao elegante para recepcoes e salas amplas.", "Palmeiras", 96.00);
        addInitialPlant("Alecrim", "Erva fresca para culinaria e jardins sensoriais.", "Ervas", 14.50);
    }

    public List<Plant> findAll() {
        return new ArrayList<>(plants);
    }

    public List<Plant> findHighlightedPlants() {
        List<Plant> highlightedPlants = new ArrayList<>();
        int totalHighlights = Math.min(3, plants.size());

        for (int index = 0; index < totalHighlights; index++) {
            highlightedPlants.add(plants.get(index));
        }

        return highlightedPlants;
    }

    public Plant findById(long id) {
        for (Plant plant : plants) {
            if (plant.getId() == id) {
                return plant;
            }
        }

        return null;
    }

    public Plant create(PlantForm plantForm) {
        Plant plant = plantForm.toPlant(nextId);
        plants.add(plant);
        nextId++;
        return plant;
    }

    public Plant update(long id, PlantForm plantForm) {
        Plant plant = findById(id);
        if (plant == null) {
            return null;
        }

        plant.setNome(plantForm.cleanNome());
        plant.setDescricao(plantForm.cleanDescricao());
        plant.setCategoria(plantForm.cleanCategoria());
        plant.setPreco(plantForm.priceAsDouble());
        return plant;
    }

    public boolean delete(long id) {
        Plant plant = findById(id);
        if (plant == null) {
            return false;
        }

        plants.remove(plant);
        return true;
    }

    private void addInitialPlant(String nome, String descricao, String categoria, double preco) {
        plants.add(new Plant(nextId, nome, descricao, categoria, preco));
        nextId++;
    }
}
