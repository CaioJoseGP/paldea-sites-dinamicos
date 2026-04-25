package com.jardim.paldea.controller;

import com.jardim.paldea.service.PlantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowcaseController {

    private final PlantService plantService;

    public ShowcaseController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/ofertas")
    public ModelAndView showOffers(@RequestParam(defaultValue = "ativa") String promocaoStatus) {
        boolean promotionActive = !"inativa".equalsIgnoreCase(promocaoStatus);

        ModelAndView modelAndView = new ModelAndView("ofertas");
        modelAndView.addObject("promocaoAtiva", promotionActive);
        modelAndView.addObject("promocaoStatus", promotionActive ? "ativa" : "inativa");
        modelAndView.addObject("destaques", plantService.findHighlightedPlants());
        return modelAndView;
    }

    @GetMapping("/catalogo")
    public ModelAndView showCatalog() {
        ModelAndView modelAndView = new ModelAndView("catalogo");
        modelAndView.addObject("plants", plantService.findAll());
        return modelAndView;
    }
}
