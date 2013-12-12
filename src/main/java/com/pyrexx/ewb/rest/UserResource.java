package com.pyrexx.ewb.rest;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pyrexx.ewb.domain.*;
import com.sun.jersey.spi.resource.Singleton;

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

@Singleton
@Path("/user")
public class UserResource  {
	//private static final long serialVersionUID = 123456l;
	private List<User> users;

	/**
	 * Creates a new instance of UserResource
	 * 
	 * @return
	 */
	public UserResource() {
		System.out.println("User Resource has invoked! ");
		if(this.users == null) {
		users = new ArrayList<User>();
		User user = new User("lei", "1234");
		user.setId(100);
		UserProfile userProfile = new UserProfile("Shi", "Lei",
				"Spichernstr.2", "10777", "Berlin", 1, "03088716060",
				"technik@pyrexx.com");
		//user.getMaintencenBooks().add(mbook);
		user.setUserProfile(userProfile);
		users.add(user);
		}
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
							+ ", successfully login!");
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
			System.out.println("Function \"save_ewb_id\" ");
			return this.createEWB(json);
		}
		if (function.equals("ewb_search")) {
			System.out.println("Function \"ewb_search without params\" ");
			 return this.searchEWB(json);
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
		System.out.println(" username: " + username); // LOG
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
						
						this.users.get(i).getMaintenanceBooks().add(ewb);
						ewb.setId(this.calEWBId(users.get(i)));
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
	
	// function load_ewb_id
	private String searchEWB(JSONObject json) throws JSONException {
		String username = json.getString("username");
		String password = json.getString("password");
		JSONObject responseJSON = new JSONObject();
		JSONObject searchResult = new JSONObject();

		if (username != null && password != null) {
			for (int i = 0; i < users.size(); i++) {
				if (username.equals(users.get(i).getUsername())
						&& password.equals(users.get(i).getPassword())) { // validated
																			// username
																			// and
																			// password
					responseJSON.put("status", 1);
					responseJSON.put("statusdescription", "Function is OK");
					if (json.opt("params") == null) {
						System.out.println("The ewb_search has no params."); // LOG
						for (int n = 0; n < users.get(i).getMaintenanceBooks()
								.size(); n++) {
							System.out.println("mBook id:"
									+ users.get(i).getMaintenanceBooks().get(n)
											.getId()
									+ ", street:"
									+ users.get(i).getMaintenanceBooks().get(n)
											.getRoad()); // LOG
							searchResult.put(
									("" + (n + 1)),
									this.getEWBInJSON(users.get(i)
											.getMaintenanceBooks().get(n)));
						}
					} else { // with params,
						List<MaintenanceBook> books = this.getEWBByParams(
								users.get(i), json.getJSONObject("params"));
						for (int m = 0; m < books.size(); m++) {
							searchResult.put("" + m + 1,
									this.getEWBInJSON(books.get(m)));
						}
					}
					System.out.println(users.get(i).getMaintenanceBooks()
							.size()
							+ " EWB have found in the user "
							+ users.get(i).getUsername()); // LOG
					responseJSON.put("ewb_search_result", searchResult);
					return responseJSON.toString();
				}
			}
		}
		return "";
	}

	// Return a EWB with JSON form
	private JSONObject getEWBInJSON(MaintenanceBook book) throws JSONException {
		JSONObject jBook = new JSONObject();
		if (book == null) {
			jBook.put("0", " ");
		} else {
			jBook.put("ewb_id", book.getId());
			jBook.putOpt("ewb_mi_name", book.getRenter_Name());
			jBook.putOpt("ewb_mi_forename", book.getRenter_Firstname());
			jBook.putOpt("ewb_str_name", book.getRoad());
			jBook.putOpt("ewb_str_zip", book.getPostcode());
			jBook.putOpt("ewb_str_town", book.getCity());
			jBook.putOpt("ewb_ls_name", book.getHousenumber());
		}
		return jBook;
	}

	private List<MaintenanceBook> getEWBByParams(User user, JSONObject jParams)
			throws JSONException {
		String street = jParams.optString("ewb_str_name");
		String zip = jParams.optString("ewb_str_zip");
		String houseNr = jParams.optString("ewb_ls_name");
		List<MaintenanceBook> results = new ArrayList<MaintenanceBook>();
		int bookSize = user.getMaintenanceBooks().size();
		// situation 1, there are zip and street name
		if (zip.length() > 0 && street.length() > 0) {
			if (houseNr.length() > 0) { // zip. street,house >0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getPostcode().indexOf(zip) > -1
							&& book.getRoad().indexOf(street) > -1
							&& book.getHousenumber().indexOf(zip) > -1) {
						results.add(book);
					}
				}
			} else {
				// zip, street >0, house =0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getPostcode().indexOf(zip) > -1
							&& book.getRoad().indexOf(street) > -1) {
						results.add(book);
					}
				}
			}
		}
		// situation 2, there is zip and no street name
		if (zip.length() > 0) {
			if (houseNr.length() > 0) {
				// zip>0 ,street =0 ,house >0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getPostcode().indexOf(zip) > -1
							&& book.getHousenumber().indexOf(zip) > -1) {
						results.add(book);
					}
				}
			} else {
				// zip >0,street=0, house =0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getPostcode().indexOf(zip) > -1) {
						results.add(book);
					}
				}
			}
		}
		// situation 3, there is street name and no zip
		if (street.length() > 0) {
			if (houseNr.length() > 0) {
				// zip=0 ,street>0 ,house >0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getRoad().indexOf(street) > -1
							&& book.getHousenumber().indexOf(zip) >= 0) {
						results.add(book);
					}
				}
			} else {
				// zip=0,street>0, house =0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getRoad().indexOf(street) > -1) {
						results.add(book);
					}
				}
			}
		}
		// situation 4, there is no street name and no zip, only housenr.
		if (street.length() == 0 && zip.length() == 0) {
			if (houseNr.length() > 0) {
				// zip=0 ,street=0 ,house >0
				for (int i = 0; i < bookSize; i++) {
					MaintenanceBook book = user.getMaintenanceBooks().get(i);
					if (book.getHousenumber().indexOf(zip) >= 0) {
						results.add(book);
					}
				}
			} else {
				// zip=0,street=0, house =0
				return user.getMaintenanceBooks();
			}
		}
		return results;
	}
	
	// calculate an id for ewb
	private int calEWBId (User user) {
		int id = 0;
		int size = user.getMaintenanceBooks().size();
		if (size == 1) {
			id = 1;
		}
		if (size > 1) {
			id = user.getMaintenanceBooks().get(size-2).getId()+1;
		}		
		return id;
	}

}
