package services;

import java.util.ArrayList;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import beans.Friend;
import dao.FriendDAO;

@Path("/friends")
@Singleton
public class FriendService {

	@Context
	HttpServletRequest request;
	
	
	@POST
	@Path("/addFriend")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addFriend(JSONObject data){
		
		return FriendDAO.addFriend((String) data.get("friend1"),(String) data.get("friend2"));
		
	
	}
	
	@POST
	@Path("/getMyFriends")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Friend> getMyFriends(JSONObject data){
		
		return FriendDAO.getMyFriends((String) data.get("user"),(String) data.get("order"));
		
		
	}
	
	@POST
	@Path("/deleteFriend")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteFriend(JSONObject data){
		return FriendDAO.deleteFriend((String) data.get("user"),(String) data.get("friend"));
	}
	
	
	
	
}
