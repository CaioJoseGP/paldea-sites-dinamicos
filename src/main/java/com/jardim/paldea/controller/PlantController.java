package com.jardim.paldea.controller;

import com.jardim.paldea.model.Plant;
import com.jardim.paldea.model.PlantForm;
import com.jardim.paldea.service.PlantService;
import com.jardim.paldea.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/plantas")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public ModelAndView showCrudPage() {
        return buildCrudPage(HttpStatus.OK, "Painel carregado", "Gerencie o catalogo da Paldea a partir dos formularios abaixo.",
                new PlantForm(), "", "", null);
    }

    @GetMapping("/buscar")
    public ModelAndView search(@RequestParam(defaultValue = "") String id) {
        ServiceResult<Plant> result = plantService.findById(id);
        HttpStatus status = toHttpStatus(result);
        String title = status == HttpStatus.OK ? "Planta localizada" : "Busca nao concluida";
        return buildCrudPage(status, title, result.message(), new PlantForm(), id, "", result.data());
    }

    @PostMapping("/inserir")
    public ModelAndView create(@ModelAttribute PlantForm plantForm) {
        ServiceResult<Plant> result = plantService.create(plantForm);
        HttpStatus status = toHttpStatus(result);
        String title = status == HttpStatus.OK ? "Cadastro realizado" : "Cadastro nao concluido";
        return buildCrudPage(status, title, result.message(), new PlantForm(), "", "", result.data());
    }

    @PostMapping("/atualizar")
    public ModelAndView update(@ModelAttribute PlantForm plantForm) {
        ServiceResult<Plant> result = plantService.update(plantForm);
        HttpStatus status = toHttpStatus(result);
        String title = status == HttpStatus.OK ? "Atualizacao concluida" : "Atualizacao nao concluida";
        return buildCrudPage(status, title, result.message(), plantForm, "", "", result.data());
    }

    @PostMapping("/apagar")
    public ModelAndView delete(@RequestParam(defaultValue = "") String id) {
        ServiceResult<Void> result = plantService.delete(id);
        HttpStatus status = toHttpStatus(result);
        String title = status == HttpStatus.OK ? "Cadastro removido" : "Exclusao nao concluida";
        return buildCrudPage(status, title, result.message(), new PlantForm(), "", id, null);
    }

    // Entrega 2 - uso de erros HTTP: as regras de negocio convertem o retorno para 200, 400 ou 404 via Spring MVC.
    private HttpStatus toHttpStatus(ServiceResult<?> result) {
        return switch (result.type()) {
            case SUCCESS -> HttpStatus.OK;
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
        };
    }

    private ModelAndView buildCrudPage(HttpStatus status, String title, String message, PlantForm plantForm,
                                       String searchId, String deleteId, Plant selectedPlant) {
        List<Plant> plants = plantService.findAll();

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
        return modelAndView;
    }
}
