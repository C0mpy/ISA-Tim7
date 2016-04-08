package services;

import java.util.Properties;

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
				InternetAddress.parse((String)data.get("email")));
			message.setSubject("Activating account");
			message.setText("<a href='127.0.0.1:8080/ISA-Tim7/index.html'>activate account</a>", "utf-8", "html");
		
			
			Transport.send(message);


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		
		
		
		UserDAO.addGuest((String)data.get("email"), (String)data.get("fname"), (String)data.get("lname"), (String)data.get("pwd"));
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
}
