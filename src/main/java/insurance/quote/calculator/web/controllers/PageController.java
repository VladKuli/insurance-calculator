package insurance.quote.calculator.web.controllers;

import insurance.quote.calculator.core.application.service.QuoteCalculationService;
import insurance.quote.calculator.web.dto.form.CarQuoteRequestForm;
import insurance.quote.calculator.web.mapper.request.QuoteRequestFormMapperFactory;
import insurance.quote.calculator.web.mapper.response.QuoteResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

}