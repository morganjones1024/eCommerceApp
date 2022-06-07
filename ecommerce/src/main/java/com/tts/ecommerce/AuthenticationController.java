package com.tts.ecommerce;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ecommerce.Services.UserService;
import com.tts.ecommerce.model.User;

@Controller
public class AuthenticationController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/signin")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "signin";
	}
	
	@PostMapping("/signin")
	public String signup(@Valid User user, BindingResult bindingResult, @RequestParam String submit,
			HttpServletRequest request, Model model) throws ServletException {
		model.addAttribute("user", user);
		if (userService.getLoggedInUser() != null) {
			bindingResult.reject("error.user", "Already logged in.");
		}

		String password = user.getPassword();
		
		if (submit.equals("up")) {
			userService.goodPassword(password, bindingResult);
			
		if (!bindingResult.hasErrors()) {
			if (userService.findByUsername(user.getUsername()) == null) {
				userService.saveNew(user);
			} else {
				bindingResult.rejectValue("username", "error.user", "Username is already taken.");
			}
		}
		if (!bindingResult.hasErrors() && userService.findByUsername(user.getUsername()) == null) {
			bindingResult.rejectValue("username", "error.user", "User does not exist.");
		}
		}
		if (!bindingResult.hasErrors()) {
			request.login(user.getUsername(), password);
			return "redirect:/";
		}
		return "signin";
	}

}
