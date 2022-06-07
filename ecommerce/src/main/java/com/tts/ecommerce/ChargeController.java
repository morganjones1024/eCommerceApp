package com.tts.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tts.ecommerce.Services.StripeService;
import com.tts.ecommerce.Services.UserService;
import com.tts.ecommerce.model.ChargeRequest;
import com.tts.ecommerce.model.ChargeRequest.Currency;
import com.tts.ecommerce.model.User;

@Controller
public class ChargeController {

	@Autowired
	UserService userService;
	
    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction()); 
        User user = userService.getLoggedInUser();        
        user.getCart().clear(); 
        
        userService.saveExisting(user);
        
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}