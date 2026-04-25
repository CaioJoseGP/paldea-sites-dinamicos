package com.jardim.paldea.controller;

import com.jardim.paldea.model.LoginForm;
import com.jardim.paldea.service.AuthService;
import com.jardim.paldea.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping({"/", "/login"})
    public ModelAndView showLogin() {
        return buildLoginPage(new LoginForm(), HttpStatus.OK, null, null);
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginForm loginForm, RedirectAttributes redirectAttributes) {
        ServiceResult<String> result = authService.authenticate(loginForm);

        // Entrega 2 - uso de erros HTTP: login invalido responde 400 Bad Request.
        if (!result.isSuccess()) {
            return buildLoginPage(loginForm, HttpStatus.BAD_REQUEST, "Nao foi possivel entrar", result.message());
        }

        redirectAttributes.addFlashAttribute("usuario", result.data());
        redirectAttributes.addFlashAttribute("feedbackStatus", HttpStatus.OK.value());
        redirectAttributes.addFlashAttribute("feedbackTone", "success");
        redirectAttributes.addFlashAttribute("feedbackTitle", "Acesso liberado");
        redirectAttributes.addFlashAttribute("feedbackMessage", result.message());
        return new ModelAndView("redirect:/ofertas");
    }

    private ModelAndView buildLoginPage(LoginForm loginForm, HttpStatus status, String title, String message) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.setStatus(status);
        modelAndView.addObject("loginForm", loginForm);
        modelAndView.addObject("staffEmail", AuthService.STAFF_EMAIL);
        modelAndView.addObject("staffPassword", AuthService.STAFF_PASSWORD);

        if (message != null) {
            modelAndView.addObject("feedbackStatus", status.value());
            modelAndView.addObject("feedbackTone", "danger");
            modelAndView.addObject("feedbackTitle", title);
            modelAndView.addObject("feedbackMessage", message);
        }

        return modelAndView;
    }
}
