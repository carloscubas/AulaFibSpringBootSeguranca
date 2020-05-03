package br.cubas.usercontrol.constants;

public class AuthorizationGrant {

	public static final String SCOPE_WRITE = "#oauth2.hasScope('white')";
	public static final String SCOPE_RESOURCE_SERVICE = "#oauth2.hasScope('service')";

	public static final String AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	public static final String AUTHORITY_USER = "hasAuthority('USER')";

}
