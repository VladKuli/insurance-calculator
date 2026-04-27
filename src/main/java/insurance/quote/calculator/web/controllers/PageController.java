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

    private final QuoteCalculationService quoteCalculationService;
    private final QuoteRequestFormMapperFactory mapperFactory;
    private final QuoteResponseMapper quoteResponseMapper;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/quotes/car")
    public String carQuotePage(Model model) {
        model.addAttribute("carQuoteRequestForm", new CarQuoteRequestForm());
        return "quotes/car";
    }

    @PostMapping("/quotes/car")
    public String calculateCarQuote(
            @Valid @ModelAttribute("carQuoteRequestForm") CarQuoteRequestForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "quotes/car";
        }

        var command = mapperFactory.toCommand(form);
        var quote = quoteCalculationService.calculateQuote(command);
        var response = quoteResponseMapper.toResponse(quote);

        model.addAttribute("quoteResponse", response);

        return "quotes/car";
    }

    @PostMapping("/quotes/car/accept")
    public String acceptCarQuote() {
        return "redirect:/quotes/car/success";
    }

    @PostMapping("/quotes/car/reject")
    public String rejectCarQuote() {
        return "redirect:/quotes/car";
    }

    @GetMapping("/quotes/car/success")
    public String carQuoteSuccessPage() {
        return "quotes/car-success";
    }

}