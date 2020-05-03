package br.cubas.usercontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.cubas.usercontrol.constants.AuthorizationGrant;
import br.cubas.usercontrol.entities.User;
import br.cubas.usercontrol.services.UserService;

@Controller
@RequestMapping("/user")
public class UserControllers {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	@PreAuthorize(AuthorizationGrant.AUTHORITY_ADMIN)
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize(AuthorizationGrant.AUTHORITY_ADMIN)
	public ResponseEntity<Boolean> createUsers(@RequestBody User users) {
		boolean salvou = this.userService.createUser(users);
		if (salvou) {
			return new ResponseEntity<>(salvou, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(salvou, HttpStatus.BAD_REQUEST);
		}
	}

}
