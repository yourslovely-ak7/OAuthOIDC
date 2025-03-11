package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import helper.Helper;

@SuppressWarnings("serial")
public class OAuthCallback extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String authCode= req.getParameter("code");
//		String location= req.getParameter("location");
		String server= req.getParameter("accounts-server");
		
		StringBuilder tokenApi= new StringBuilder(server);
		tokenApi.append("/oauth/v2/token?")
		.append("client_id=").append(Helper.getClientId())
		.append("&client_secret=").append(Helper.getClientSecret())
		.append("&grant_type=authorization_code")
		.append("&redirect_uri=").append(Helper.getRedirectURI())
		.append("&code="+authCode);
		
		URL url= new URL(tokenApi.toString());
		HttpURLConnection connection= (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept", "application/json");
		
		StringBuilder response= new StringBuilder();
		try(BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream())))
		{
			String line;
			while((line = reader.readLine()) != null)
			{
				response.append(line);
			}
		}
		
		try
		{
			JSONObject json= new JSONObject(response.toString());
			
			String accessToken= json.getString("access_token");
			String refreshToken= json.getString("refresh_token");
			String idToken= json.getString("id_token");
			String apiDomain= json.getString("api_domain");
			
			System.out.println("Access Token: "+ accessToken);
			System.out.println("Refresh Token: "+ refreshToken);
			System.out.println("ID Token: "+ idToken);
			System.out.println("Domain: "+ apiDomain);
			
			String parts[]= idToken.split("\\.");
			String payload=  new String(Base64.getUrlDecoder().decode(parts[1]));
			System.out.println("Payload: "+ payload);
			
			JSONObject jsonResp= new JSONObject(payload);
			
	        resp.setContentType("text/html");
	        PrintWriter out = resp.getWriter();
	        
	        out.println("<!DOCTYPE html>");
	        out.println("<html>");
	        out.println("<head><title>OAuth Response</title></head>");
	        out.println("<body>");
	        out.println("<h1>Welcome, " + jsonResp.getString("name") + "!</h1>");
	        out.println("<p>First Name: <strong>" + jsonResp.getString("first_name") + "</strong></p>");
	        out.println("<p>Last Name: <strong>" + jsonResp.getString("last_name") + "</strong></p>");
	        out.println("<p>Email ID: <strong>" + jsonResp.getString("email") + "</strong></p>");
	        out.println("<p>Gender: <strong>" + jsonResp.getString("gender") + "</strong></p>");
	        out.println("</body>");
	        out.println("</html>");
		}
		catch(JSONException error)
		{
			System.out.println("Exception occurred: "+error.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
