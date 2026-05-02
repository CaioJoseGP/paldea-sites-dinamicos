package com.jardim.paldea.controller;

import com.jardim.paldea.model.LoginForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping({"/", "/login"})
    public ModelAndView showLogin(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return new ModelAndView("redirect:/catalogo");
        }

        return buildLoginPage(new LoginForm(), HttpStatus.OK, null, null);
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginForm loginForm, RedirectAttributes redirectAttributes, HttpSession session) {
        String errorMessage = loginForm.validateAccess();

        // Entrega 2 - uso de erros HTTP: login invalido responde 400 Bad Request.
        if (errorMessage != null) {
            return buildLoginPage(loginForm, HttpStatus.BAD_REQUEST, "Nao foi possivel entrar", errorMessage);
        }

        String displayName = loginForm.displayName();
        session.setAttribute("usuario", displayName);
        redirectAttributes.addFlashAttribute("feedbackStatus", HttpStatus.OK.value());
        redirectAttributes.addFlashAttribute("feedbackTone", "success");
        redirectAttributes.addFlashAttribute("feedbackTitle", "Acesso liberado");
        redirectAttributes.addFlashAttribute("feedbackMessage", "Bem-vindo, " + displayName + ". O painel comercial da Paldea esta disponivel.");
        redirectAttributes.addFlashAttribute("usuario", displayName);
        return new ModelAndView("redirect:/catalogo");
    }

    private ModelAndView buildLoginPage(LoginForm loginForm, HttpStatus status, String title, String message) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.setStatus(status);
        modelAndView.addObject("loginForm", loginForm);
        modelAndView.addObject("staffEmail", LoginForm.STAFF_EMAIL);
        modelAndView.addObject("staffPassword", LoginForm.STAFF_PASSWORD);

        if (message != null) {
            modelAndView.addObject("feedbackStatus", status.value());
            modelAndView.addObject("feedbackTone", "danger");
            modelAndView.addObject("feedbackTitle", title);
            modelAndView.addObject("feedbackMessage", message);
        }

        return modelAndView;
    }
}
