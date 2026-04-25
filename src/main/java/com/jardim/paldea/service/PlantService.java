package com.jardim.paldea.service;

import com.jardim.paldea.model.Plant;
import com.jardim.paldea.model.PlantForm;
import com.jardim.paldea.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    public List<Plant> findHighlightedPlants() {
        List<Plant> plants = plantRepository.findAll();
        return plants.subList(0, Math.min(3, plants.size()));
    }

    public ServiceResult<Plant> findById(String idText) {
        Long id = parseId(idText);
        if (id == null) {
            return ServiceResult.badRequest("Informe um identificador numerico para buscar uma planta.");
        }

        return plantRepository.findById(id)
                .map(plant -> ServiceResult.success("A planta " + plant.getNome() + " foi localizada com sucesso.", plant))
                .orElseGet(() -> ServiceResult.notFound("Nenhum cadastro foi encontrado para o ID " + id + "."));
    }

    public ServiceResult<Plant> create(PlantForm plantForm) {
        ValidationResult validation = validateCommonFields(plantForm);
        if (!validation.valid()) {
            return ServiceResult.badRequest(validation.message());
        }

        Plant plant = plantRepository.save(
                plantForm.getNome().trim(),
                plantForm.getDescricao().trim(),
                plantForm.getCategoria().trim(),
                validation.price()
        );

        return ServiceResult.success("A planta " + plant.getNome() + " foi cadastrada com o ID " + plant.getId() + ".", plant);
    }

    public ServiceResult<Plant> update(PlantForm plantForm) {
        Long id = parseId(plantForm.getId());
        if (id == null) {
            return ServiceResult.badRequest("Informe um ID valido para atualizar um cadastro existente.");
        }

        ValidationResult validation = validateCommonFields(plantForm);
        if (!validation.valid()) {
            return ServiceResult.badRequest(validation.message());
        }

        Plant updatedPlant = new Plant(id, plantForm.getNome().trim(), plantForm.getDescricao().trim(),
                plantForm.getCategoria().trim(), validation.price());

        return plantRepository.update(id, updatedPlant)
                .map(plant -> ServiceResult.success("O cadastro da planta " + plant.getNome() + " foi atualizado.", plant))
                .orElseGet(() -> ServiceResult.notFound("Nao existe planta cadastrada com o ID " + id + " para atualizacao."));
    }

    public ServiceResult<Void> delete(String idText) {
        Long id = parseId(idText);
        if (id == null) {
            return ServiceResult.badRequest("Informe um ID valido para remover um cadastro.");
        }

        if (!plantRepository.delete(id)) {
            return ServiceResult.notFound("Nao existe planta cadastrada com o ID " + id + " para exclusao.");
        }

        return ServiceResult.success("O cadastro de ID " + id + " foi removido da vitrine administrativa.", null);
    }

    private ValidationResult validateCommonFields(PlantForm plantForm) {
        if (safe(plantForm.getNome()).isBlank()) {
            return ValidationResult.invalid("O nome da planta precisa ser preenchido.");
        }

        if (safe(plantForm.getCategoria()).isBlank()) {
            return ValidationResult.invalid("Selecione uma categoria para o cadastro.");
        }

        if (safe(plantForm.getDescricao()).isBlank()) {
            return ValidationResult.invalid("Informe uma descricao para apresentar a planta no catalogo.");
        }

        String priceText = safe(plantForm.getPreco()).replace(",", ".");
        if (priceText.isBlank()) {
            return ValidationResult.invalid("Informe um preco para concluir a operacao.");
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                return ValidationResult.invalid("O preco deve ser maior que zero.");
            }
            return ValidationResult.valid(price);
        } catch (NumberFormatException exception) {
            return ValidationResult.invalid("O preco precisa estar em formato numerico.");
        }
    }

    private Long parseId(String value) {
        String cleaned = safe(value);
        if (cleaned.isBlank()) {
            return null;
        }

        try {
            long parsed = Long.parseLong(cleaned);
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private record ValidationResult(boolean valid, String message, double price) {

        static ValidationResult valid(double price) {
            return new ValidationResult(true, "", price);
        }

        static ValidationResult invalid(String message) {
            return new ValidationResult(false, message, 0);
        }
    }
}
