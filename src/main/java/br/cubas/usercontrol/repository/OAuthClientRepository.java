package br.cubas.usercontrol.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import br.cubas.usercontrol.entities.OAuthClient;

@Repository
public class OAuthClientRepository {

	List<OAuthClient> authClients = new ArrayList<OAuthClient>();

	public OAuthClientRepository() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		OAuthClient clientPassword = new OAuthClient();
		clientPassword.setClientId("web");
		clientPassword.setClientSecret(passwordEncoder.encode("111"));
		clientPassword.setScope("read,write,trust");
		clientPassword.setAuthorizedGrantTypes("refresh_token,password");
		clientPassword.setAccessTokenValidity(86400); // 1 days
		clientPassword.setRefreshTokenValidity(172800); // 1 days

		authClients.add(clientPassword);

		OAuthClient clientCledentials = new OAuthClient();
		clientCledentials.setClientId("spring_boot_resource_service");
		clientCledentials.setClientSecret(passwordEncoder.encode("222"));
		clientCledentials.setScope("read,write,trust");
		clientCledentials.setAuthorizedGrantTypes("client_credentials");

		authClients.add(clientCledentials);

	}

	public OAuthClient findByClientId(String clientId) {
		OAuthClient oAuthClient = null;
		for (OAuthClient u : authClients) {
			if (u.getClientId().equals(clientId)) {
				oAuthClient = u;
			}
		}

		System.out.println("lido " + oAuthClient);
		return oAuthClient;
	}

	public void save(OAuthClient oAuthClient) {
		authClients.add(oAuthClient);
		System.out.println("adicionado " + oAuthClient);
	}

	public List<OAuthClient> findAll() {
		return authClients;
	}

}
