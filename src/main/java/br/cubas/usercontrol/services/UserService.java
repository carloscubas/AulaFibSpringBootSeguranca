package br.cubas.usercontrol.services;

import java.util.List;

import br.cubas.usercontrol.entities.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

	List<User> findAll();

}