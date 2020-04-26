package br.cubas.usercontrol.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.cubas.usercontrol.entities.User;
import br.cubas.usercontrol.services.SecurityService;
import br.cubas.usercontrol.services.UserService;
import br.cubas.usercontrol.validator.LoginValidator;
import br.cubas.usercontrol.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserControllers {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginValidator loginValidator;

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("user/form", "userForm", new User());
	}

	@PostMapping("/autentication")
	public ModelAndView autentication(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {

		loginValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("user/form");
		}

		securityService.login(userForm.getUsername(), userForm.getPassword());
		return new ModelAndView("redirect:/user/list");
	}

	@GetMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("/user/list");
	}

	@GetMapping("/listadmin")
	public ModelAndView listadmin(User user) {

		List<User> users = userService.findAll();
		return new ModelAndView("/user/listadmin", "users", users);
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {

		return new ModelAndView("user/registration", "userForm", new User());
	}

	@PostMapping(value = "/registration")
	public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {

		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("user/registration");
		}

		String password = userForm.getPassword();

		userService.save(userForm);

		try {
			securityService.login(userForm.getUsername(), password);
			return new ModelAndView("redirect:/user/list");
		} catch (Exception e) {
			return new ModelAndView("redirect:/user/login");
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/user/login";
	}

}
