package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseConnection;
import exception.InvalidException;
import pojo.User;
import pojo.UserSession;

public class Helper {
	
	private static final String clientId= "1000.LIM0W0QNKWSYXJT396NHID5XDVZADQ";
	private static final String redirectURI= "http://localhost:8080/NewProject/oauthredirect";
	
	public static String getClientId()
	{
		return clientId;
	}
	
	public static String getClientSecret() throws InvalidException {
		String query = "Select client_secret from ClientDetails where authType=?";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) 
		{
			statement.setString(1, "Zoho");
			System.out.println(statement);
			try(ResultSet result = statement.executeQuery())
			{
				String clientSecret = null;
				while (result.next()) {
					clientSecret = result.getString(1);
				}
				return clientSecret;				
			}
		} catch (SQLException | ClassNotFoundException error) {
			System.out.println("SQL Exception: " + error.getMessage());
			throw new InvalidException("Error occurred while fetching from DB.", error);
		}
	}
	
	public static String getRedirectURI()
	{
		return redirectURI;
	}

	public static void checkForNull(Object obj) throws InvalidException
	{
		if(obj==null)
		{
			throw new InvalidException("Value cannot be Null!");
		}
	}
	
	public static User buildUserFromJson(JSONObject data) throws JSONException
	{
		User user= new User();
		
		user.setName(data.getString("name"));
		user.setFirstName(data.getString("first_name"));
		user.setLastName(data.getString("last_name"));
		user.setEmail(data.getString("email"));
		user.setGender(data.getString("gender"));
		
		return user;
	}
	
	public static UserSession buildUserSession(String accessToken, String refreshToken, int userId)
	{
		UserSession uSession= new UserSession();
		uSession.setAccessToken(accessToken);
		uSession.setRefreshToken(refreshToken);
		uSession.setUserId(userId);
		
		return uSession;
	}
}
