package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Helper {
	
	private static final String clientId= "1000.LIM0W0QNKWSYXJT396NHID5XDVZADQ";
	private static final String redirectURI= "http://localhost:8080/NewProject/oauthredirect";
	
	public static String getClientId()
	{
		return clientId;
	}
	
	public static String getClientSecret()
	{
		String query="Select client_secret from ClientDetails where authType=?";
		try(Connection connection= DatabaseConnection.getConnection();
				PreparedStatement statement= connection.prepareStatement(query))
		{
			statement.setString(1, "Zoho");
			System.out.println(statement);
			ResultSet result= statement.executeQuery();
			
			String clientSecret=null;
			while(result.next())
			{
				clientSecret= result.getString(1);
			}
			
			return clientSecret;
		} 
		catch (SQLException error) 
		{
			error.printStackTrace();
		}
		return null;
	}
	
	public static String getRedirectURI()
	{
		return redirectURI;
	}

}
