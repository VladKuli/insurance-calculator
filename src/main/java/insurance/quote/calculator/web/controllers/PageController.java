package insurance.quote.calculator.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

}