package com.jardim.paldea.controller;

import com.jardim.paldea.model.PlantCatalog;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowcaseController {

    private final PlantCatalog plantCatalog;

    public ShowcaseController(PlantCatalog plantCatalog) {
        this.plantCatalog = plantCatalog;
    }

    @GetMapping("/catalogo")
    public ModelAndView showCatalog(@RequestParam(defaultValue = "ativa") String promocaoStatus, HttpSession session) {
        boolean promotionActive = !"inativa".equalsIgnoreCase(promocaoStatus);

        ModelAndView modelAndView = new ModelAndView("catalogo");
        modelAndView.addObject("promocaoAtiva", promotionActive);
        modelAndView.addObject("promocaoStatus", promotionActive ? "ativa" : "inativa");
        modelAndView.addObject("plants", plantCatalog.findAll());
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
