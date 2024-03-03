package cz.uhk.fim.workshop.controller;

import cz.uhk.fim.workshop.model.Error;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        HttpStatus s = HttpStatus.valueOf(Integer.parseInt(status.toString()));

        Error e = new Error(s.value(), s.getReasonPhrase());

        return "error";
    }

    @PostMapping("/error")
    @ResponseBody
    public Error handleRestError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus s = HttpStatus.valueOf(Integer.parseInt(status.toString()));

        Error e = new Error(s.value(), s.getReasonPhrase());
        return e;
    }
}
