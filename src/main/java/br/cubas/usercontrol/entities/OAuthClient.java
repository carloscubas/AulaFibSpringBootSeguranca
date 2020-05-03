package br.cubas.usercontrol.entities;

public class OAuthClient {

	private Integer id;

	private String clientId;

	private String clientSecret;

	private String scope;

	private String authorizedGrantTypes;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	@Override
	public String toString() {
		return "OAuthClient [id=" + id + ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", scope="
				+ scope + ", authorizedGrantTypes=" + authorizedGrantTypes + ", accessTokenValidity="
				+ accessTokenValidity + ", refreshTokenValidity=" + refreshTokenValidity + "]";
	}

}
