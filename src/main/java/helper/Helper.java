package helper;

public class Helper {
	
	private static final String clientId= "1000.LIM0W0QNKWSYXJT396NHID5XDVZADQ";
	private static final String clientSecret= "e12ad6f8fcf8b94edda68faf5bbb879c77cc322e0e";
	private static final String redirectURI= "http://localhost:8080/NewProject/oauthredirect";
	
	public static String getClientId()
	{
		return clientId;
	}
	
	public static String getClientSecret()
	{
		return clientSecret;
	}
	
	public static String getRedirectURI()
	{
		return redirectURI;
	}
}
