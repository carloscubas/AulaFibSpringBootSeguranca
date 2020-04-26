package br.cubas.usercontrol.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.cubas.usercontrol.entities.Role;
import br.cubas.usercontrol.entities.User;
import br.cubas.usercontrol.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.getRoles().add(new Role("ROLE_BASIC"));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);

	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}