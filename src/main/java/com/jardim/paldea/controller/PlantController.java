package com.jardim.paldea.controller;

import com.jardim.paldea.model.Plant;
import com.jardim.paldea.model.PlantCatalog;
import com.jardim.paldea.model.PlantForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PlantController {

    private final PlantCatalog plantCatalog;

    public PlantController(PlantCatalog plantCatalog) {
        this.plantCatalog = plantCatalog;
    }

    @GetMapping("/plantas")
    public ModelAndView showCrudPage(HttpSession session) {
        return buildCrudPage(HttpStatus.OK, "Painel carregado", "Gerencie o catalogo da Paldea a partir dos formularios abaixo.",
                new PlantForm(), "", "", null, session);
    }

    @GetMapping("/plantas/buscar")
    public ModelAndView search(@RequestParam(defaultValue = "") String id, HttpSession session) {
        long plantId = PlantForm.parseId(id);
        if (plantId == 0L) {
            return buildCrudPage(HttpStatus.BAD_REQUEST, "Busca nao concluida",
                    "Informe um identificador numerico para buscar uma planta.", new PlantForm(), id, "", null, session);
        }

        Plant plant = plantCatalog.findById(plantId);
        if (plant == null) {
            return buildCrudPage(HttpStatus.NOT_FOUND, "Busca nao concluida",
                    "Nenhum cadastro foi encontrado para o ID " + plantId + ".", new PlantForm(), id, "", null, session);
        }

        return buildCrudPage(HttpStatus.OK, "Planta localizada",
                "A planta " + plant.getNome() + " foi localizada com sucesso.", new PlantForm(), id, "", plant, session);
    }

    @PostMapping("/plantas/inserir")
    public ModelAndView create(@ModelAttribute PlantForm plantForm, HttpSession session) {
        String validationMessage = plantForm.validatePlantData();
        if (validationMessage != null) {
            return buildCrudPage(HttpStatus.BAD_REQUEST, "Cadastro nao concluido", validationMessage,
                    new PlantForm(), "", "", null, session);
        }

        Plant plant = plantCatalog.create(plantForm);
        return buildCrudPage(HttpStatus.OK, "Cadastro realizado",
                "A planta " + plant.getNome() + " foi cadastrada com o ID " + plant.getId() + ".",
                new PlantForm(), "", "", plant, session);
    }

    @PostMapping("/plantas/atualizar")
    public ModelAndView update(@ModelAttribute PlantForm plantForm, HttpSession session) {
        long plantId = plantForm.idAsLong();
        if (plantId == 0L) {
            return buildCrudPage(HttpStatus.BAD_REQUEST, "Atualizacao nao concluida",
                    "Informe um ID valido para atualizar um cadastro existente.", plantForm, "", "", null, session);
        }

        String validationMessage = plantForm.validatePlantData();
        if (validationMessage != null) {
            return buildCrudPage(HttpStatus.BAD_REQUEST, "Atualizacao nao concluida", validationMessage,
                    plantForm, "", "", null, session);
        }

        Plant plant = plantCatalog.update(plantId, plantForm);
        if (plant == null) {
            return buildCrudPage(HttpStatus.NOT_FOUND, "Atualizacao nao concluida",
                    "Nao existe planta cadastrada com o ID " + plantId + " para atualizacao.",
                    plantForm, "", "", null, session);
        }

        return buildCrudPage(HttpStatus.OK, "Atualizacao concluida",
                "O cadastro da planta " + plant.getNome() + " foi atualizado.", plantForm, "", "", plant, session);
    }

    @PostMapping("/plantas/apagar")
    public ModelAndView delete(@RequestParam(defaultValue = "") String id, HttpSession session) {
        long plantId = PlantForm.parseId(id);
        if (plantId == 0L) {
            return buildCrudPage(HttpStatus.BAD_REQUEST, "Exclusao nao concluida",
                    "Informe um ID valido para remover um cadastro.", new PlantForm(), "", id, null, session);
        }

        if (!plantCatalog.delete(plantId)) {
            return buildCrudPage(HttpStatus.NOT_FOUND, "Exclusao nao concluida",
                    "Nao existe planta cadastrada com o ID " + plantId + " para exclusao.",
                    new PlantForm(), "", id, null, session);
        }

        return buildCrudPage(HttpStatus.OK, "Cadastro removido",
                "O cadastro de ID " + plantId + " foi removido da vitrine administrativa.",
                new PlantForm(), "", id, null, session);
    }

    private ModelAndView buildCrudPage(HttpStatus status, String title, String message, PlantForm plantForm,
                                       String searchId, String deleteId, Plant selectedPlant, HttpSession session) {
        List<Plant> plants = plantCatalog.findAll();

        ModelAndView modelAndView = new ModelAndView("plantas");
        modelAndView.setStatus(status);
        modelAndView.addObject("plantForm", plantForm);
        modelAndView.addObject("searchId", searchId);
        modelAndView.addObject("deleteId", deleteId);
        modelAndView.addObject("selectedPlant", selectedPlant);
        modelAndView.addObject("plants", plants);
        modelAndView.addObject("feedbackStatus", status.value());
        modelAndView.addObject("feedbackTitle", title);
        modelAndView.addObject("feedbackMessage", message);
        modelAndView.addObject("feedbackTone", status == HttpStatus.OK ? "success" : "danger");
        addSessionUser(modelAndView, session);
        return modelAndView;
    }

    private void addSessionUser(ModelAndView modelAndView, HttpSession session) {
        Object user = session.getAttribute("usuario");
        if (user != null) {
            modelAndView.addObject("usuario", user);
        }
    }
}
