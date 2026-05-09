package insurance.quote.calculator.web.controllers;

import insurance.quote.calculator.core.application.service.quote_calculation.QuoteCalculationService;
import insurance.quote.calculator.web.dto.form.RealEstateRequestForm;
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
public class RealEstateQuotesController {

    private final QuoteCalculationService quoteCalculationService;
    private final QuoteRequestFormMapperFactory mapperFactory;
    private final QuoteResponseMapper quoteResponseMapper;

    @GetMapping("/quotes/real-estate")
    public String realEstateQuotePage(Model model) {
        model.addAttribute("realEstateRequestForm", new RealEstateRequestForm());
        return "quotes/real-estate";
    }

    @PostMapping("/quotes/real-estate")
    public String calculateRealEstateQuote(
            @Valid @ModelAttribute("realEstateRequestForm") RealEstateRequestForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "quotes/real-estate";
        }

        var command = mapperFactory.toCommand(form);
        var quote = quoteCalculationService.calculateQuote(command);
        var response = quoteResponseMapper.toResponse(quote);

        model.addAttribute("quoteResponse", response);

        return "quotes/real-estate";
    }

    @PostMapping("/quotes/real-estate/accept")
    public String acceptRealEstateQuote() {
        return "redirect:/quotes/real-estate/success";
    }

    @PostMapping("/quotes/real-estate/reject")
    public String rejectRealEstateQuote() {
        return "redirect:/quotes/real-estate";
    }

    @GetMapping("/quotes/real-estate/success")
    public String realEstateQuoteSuccessPage() {
        return "quotes/real-estate-success";
    }

}
