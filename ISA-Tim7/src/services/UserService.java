package services;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import beans.Friend;
import beans.User;
import dao.UserDAO;

@Path("/user")
@Singleton
public class UserService {
	
	@Context
	HttpServletRequest request;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(JSONObject data) {
		
		return UserDAO.login((String)data.get("email"), 
				(String)data.get("password"));
	}
	
	@POST
	@Path("/addGuest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addGuest(JSONObject data){
		String token=generateToken();
		sendActivationToken((String)data.get("email"),token);
		UserDAO.addGuest((String)data.get("email"), (String)data.get("fname"), 
				(String)data.get("lname"), (String)data.get("pwd"),token);
	}
	
	@POST
	@Path("/addManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addManager(JSONObject data) {

		String restId = (((String) data.get("city")).split(" ")[0]);
		UserDAO.addManager((String)data.get("email"), (String)data.get("name"), (String)data.get("lName"), (String)data.get("pass"), restId);
	}
	
	@POST
	@Path("/check")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean checkEmail(String email){
		
		return UserDAO.emailExists(email);
		
	}
	
	@POST
	@Path("/resend")
	@Consumes(MediaType.TEXT_PLAIN)
	public void resend(String email){
		String token = generateToken();
		sendActivationToken( email, token);
		UserDAO.setNewToken( email, token);
	}
	@POST
	@Path("/activate_acc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean checkEmail(JSONObject data){
		
		return UserDAO.activate_acc((String) data.get("email"),(String) data.get("token"));
		
	}
	
	@POST
	@Path("/changeFname")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String changeFname(JSONObject data){
		if(UserDAO.changeFname((String) data.get("user"),(String) data.get("fname")))
			return "ok";
		else
			return "not_ok";
	}
	
	@POST
	@Path("/changeLname")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String changeLname(JSONObject data){
		if(UserDAO.changeLname((String) data.get("user"),(String) data.get("lname")))
			return "ok";
		else
			return "not_ok";
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String changePassword(JSONObject data){
		if(UserDAO.changePassword((String) data.get("user"),(String) data.get("newp"),(String) data.get("oldp")))
			return "ok";
		else
			return "not_ok";
	}
	
	@POST
	@Path("/searchPeople")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  ArrayList<Friend> search(JSONObject data){
		ArrayList<Friend> result= new ArrayList<Friend>();
		
		result = UserDAO.search((String) data.get("name"),(String) data.get("sender"));
		
		return result;
	}
	
	private void sendActivationToken(String email,String token){
		final String username = "odjebigaguglu@gmail.com";
		final String password = "kuracbakin123 ";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

 			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("nebitno@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Activating account");
			message.setText("Enter this code on your first login to activate your account ---->  "+token);
		
			
			Transport.send(message);


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		
		
		
	}
	
	private String generateToken(){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String token = sb.toString();
		
		return token;
	}
}
