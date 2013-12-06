package com.pyrexx.ewb.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.pyrexx.ewb.domain.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/user")
public class UserResource implements Serializable {
	private static final long serialVersionUID = 123456l;
	private List<User> users;

	/**
	 * Creates a new instance of UserResource
	 * 
	 * @return
	 */
	public UserResource() {
		users = new ArrayList<User>();
		MaintenanceBook mbook = new MaintenanceBook("10777", "Spicherstr.",
				"2", "Berlin", "4", "rechts");
		Room room = new Room("SCHLAFZIMMER");
		Sensor sensor = new Sensor("12345678", "Pyrexx", "PX-1");
		room.getSensors().add(sensor);
		mbook.getRooms().add(room);

		User user = new User("lei", "1234");
		user.setId(100);
		UserProfile userProfile = new UserProfile("Shi", "Lei",
				"Spichernstr.2", "10777", "Berlin", 1, "03088716060",
				"technik@pyrexx.com");
		user.getMaintencenBooks().add(mbook);
		user.setUserProfile(userProfile);

		users.add(user);
	}

	@GET
	@Path("/data")
	// @Consumes("application/json")
	@Produces("application/json")
	public String login(@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("function") String function) throws JSONException {
		JSONObject json = new JSONObject();

		if (function.equals("load_u_id") && username != null
				&& password != null) {
			for (int i = 0; i < users.size(); i++) {
				if (username.equals(users.get(i).getUsername())
						&& password.equals(users.get(i).getPassword())) {
					System.out.println("# Hello " + username
							+ ", welcome to EWB!");
					json.put("status", 1);
					json.put("statusdescription", "Login OK");
					json.put("u_id", users.get(i).getId());
					json.put("a_id", users.get(i).getUserProfile().getId());
					json.put("a_name", users.get(i).getUserProfile()
							.getSurname());
					json.put("a_forename", users.get(i).getUserProfile()
							.getFirstname());
					json.put("a_street", users.get(i).getUserProfile()
							.getStreet());
					json.put("a_zip", users.get(i).getUserProfile()
							.getPostcode());
					json.put("a_town", users.get(i).getUserProfile().getCity());
					json.put("a_salutation", users.get(i).getUserProfile()
							.getGender());
					json.put("a_tel_private", users.get(i).getUserProfile()
							.getTelephone());
					json.put("a_email_private", users.get(i).getUserProfile()
							.getEmail());
					return json.toString();
				}
			}
		}
		System.out.println("Login failed! Name: " + username + ", password: "
				+ password);
		json.put("status", 0);
		json.put("statusdescription", "Failed login!");
		return json.toString();
	}

	@POST
	@Path("/post")
	@Consumes("application/json")
	@Produces("application/json")
	public String handleFunction(String requestJSON) throws JSONException {
		JSONObject json = new JSONObject(requestJSON);
		String function = json.getString("function");

		if (function.equals("save_ewb_id")) {
			return this.createEWB(json);
		}

		return "";
	}

	@GET
	@Path("/{username}")
	@Produces("application/json")
	public User getUser(@PathParam("username") String name) {
		for (int i = 0; i < users.size(); i++) {
			if (name.equals(users.get(i).getUsername())) {
				System.err.println(name + ", welcome to EWB!");
				return users.get(i);
			}
		}
		System.err.println("This user " + name + "can not be found!");
		return null;
	}

	@GET
	@Path("/{username}/{password}")
	public User login(@PathParam("username") String name,
			@PathParam("password") String password) {
		for (int i = 0; i < users.size(); i++) {
			if (name.equals(users.get(i).getUsername())
					&& password.equals(users.get(i).getPassword())) {
				System.err.println("Successful login with " + name + "!");
				return users.get(i);
			}
		}
		System.err.println("Incorrenct login with " + name + "!");
		return null;
	}

	// function save_ewb_id
	private String createEWB(JSONObject json) throws JSONException {
		String username = json.getString("username");
		String password = json.getString("password");
		JSONObject params = json.getJSONObject("params");
		JSONObject responeJSON = new JSONObject();
		JSONObject infoJSON = new JSONObject();
		infoJSON.put("ewb_str_name", params.optString("ewb_str_name"));
		infoJSON.put("ewb_str_zip", params.optString("ewb_str_zip"));
		infoJSON.put("ewb_str_town", params.optString("ewb_str_town"));
		infoJSON.put("ewb_ls_name", params.optString("ewb_ls_name"));
		System.out.println("# Function: save_ewb_id, username: " + username); // LOG
		if (username != null && password != null) {
			for (int i = 0; i < users.size(); i++) {
				if (username.equals(users.get(i).getUsername())
						&& password.equals(users.get(i).getPassword())) {
					if (params.getString("ewb_str_name").length() > 0) { //check if street name is empty or not
						MaintenanceBook ewb = new MaintenanceBook(
								params.optString("ewb_str_zip"),
								params.getString("ewb_str_name"),
								params.optString("ewb_ls_name"),
								params.optString("ewb_str_town"),
								params.optString("ewb_w_etage"),
								params.optString("ewb_w_position"),
								params.optString("ewb_mi_name"),
								params.optString("ewb_mi_forename"),
								params.optString("ewb_mi_tel"));
						params.optString("ewb_str_zip");
						users.get(i).getMaintencenBooks().add(ewb);
						System.out.println("  A new EWB has created, id: "
								+ ewb.getId() + ", street: " + ewb.getRoad()); // LOG
						responeJSON.put("status", 1);
						responeJSON.put("statusdescription", "function is OK");
						responeJSON.put("no_error", infoJSON);
						return responeJSON.toString();
					}
				} else { // username or pw is false
					responeJSON.put("status", 0);
					responeJSON.put("statusdescription",
							"Invalid username or password!");
					responeJSON.put("error", infoJSON);
					return responeJSON.toString();
				}
			}
		}
		responeJSON.put("status", 0);
		responeJSON.put("statusdescription", "Unknown error!");
		responeJSON.put("error", "");
		return responeJSON.toString();
	}
}
