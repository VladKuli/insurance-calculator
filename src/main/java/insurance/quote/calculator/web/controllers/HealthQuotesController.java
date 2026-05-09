package insurance.quote.calculator.web.controllers;


import insurance.quote.calculator.core.application.service.quote_calculation.QuoteCalculationService;
import insurance.quote.calculator.web.dto.form.HealthQuoteRequestForm;
import insurance.quote.calculator.web.mapper.request.factory.QuoteRequestFormMapperFactory;
import insurance.quote.calculator.web.mapper.response.QuoteResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HealthQuotesController {

    private final QuoteCalculationService quoteCalculationService;
    private final QuoteRequestFormMapperFactory mapperFactory;
    private final QuoteResponseMapper quoteResponseMapper;

    @GetMapping("/quotes/health")
    public String healthQuotePage(Model model) {
        model.addAttribute("healthQuoteRequestForm", new HealthQuoteRequestForm());
        return "quotes/health";
    }

    @PostMapping("/quotes/health")
    public String calculateHealthQuote(
            @Valid @ModelAttribute("healthQuoteRequestForm") HealthQuoteRequestForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "quotes/health";
        }

        var command = mapperFactory.toCommand(form);
        var quote = quoteCalculationService.calculateQuote(command);
        var response = quoteResponseMapper.toResponse(quote);

        model.addAttribute("quoteResponse", response);

        return "quotes/health";
    }

    @PostMapping("/quotes/health/accept")
    public String acceptHealthQuote() {
        return "redirect:/quotes/health/success";
    }

    @PostMapping("/quotes/health/reject")
    public String rejectHealthQuote() {
        return "redirect:/quotes/health";
    }

    @GetMapping("/quotes/health/success")
    public String healthQuoteSuccessPage() {
        return "quotes/health-success";
    }


}
