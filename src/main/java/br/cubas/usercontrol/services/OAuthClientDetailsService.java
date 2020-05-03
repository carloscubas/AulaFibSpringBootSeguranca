package br.cubas.usercontrol.services;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import br.cubas.usercontrol.entities.OAuthClient;
import br.cubas.usercontrol.repository.OAuthClientRepository;

@Service
public class OAuthClientDetailsService implements ClientDetailsService {

	private static final Logger log = LoggerFactory.getLogger(OAuthClientDetailsService.class);

	@Autowired
	OAuthClientRepository repository;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OAuthClient oAuthClient = repository.findByClientId(clientId);

		if (Objects.isNull(oAuthClient)) {
			log.warn("m=loadClientByClientId clientId={} msg=not_found", clientId);
			throw new ClientRegistrationException("Invalid client");
		}
		log.info("m=loadClientByClientId clientId={}", clientId);

		BaseClientDetails baseClientDetails = new BaseClientDetails(oAuthClient.getClientId(), null,
				oAuthClient.getScope(), oAuthClient.getAuthorizedGrantTypes(), null);

		baseClientDetails.setClientSecret(oAuthClient.getClientSecret());
		baseClientDetails.setAccessTokenValiditySeconds(oAuthClient.getAccessTokenValidity());
		baseClientDetails.setRefreshTokenValiditySeconds(oAuthClient.getRefreshTokenValidity());
		return baseClientDetails;
	}

}
