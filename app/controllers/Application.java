package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
	//render the contactform.html
	public static void contactform() {
        render();
    }
	//render a json data to the tag combox under contactform.html
	 public static void filltag() 
	 {
		 try
	        {
			 List<Tag> tags = Tag.all().fetch();
			 renderJSON("{\"data\":\"" + tags + "\"}");
	        }
	        catch (Exception e) 
	        {
	            renderJSON("{\"error\":\"" + e + "\"}");
	        }
     }
	//render a json data to the contact table under contactform.html
	 public static void getcontact(String tag){
		
		 List Contactdata = new ArrayList();
		 try
	        {
			 if(tag.equals("ALL"))
			 {
				
				 List<Contact> contacts = Contact.all().fetch();
				 for (Contact c : contacts) {
					 Contactdata.add(c.toJson());
			        }
				
			 }
			 else
			 {
				 String query = "select distinct c from Contact c join c.tags as t where t.name ="+"'"+tag+"'";
				 List<Contact> contacts = Contact.find(query).fetch();
				 for (Contact c : contacts) {
					 Contactdata.add(c.toJson());
			        }
			 }
			 renderJSON(Contactdata);
			
	        }
	        catch (Exception e) 
	        {
	            renderJSON("{\"error\":\"" + e + "\"}");
	        }
		 
	 }
	//render a json data count that verify if it search contact base on the input tag
	public static void gettags(String tag) {
        String username = Play.configuration.getProperty("highriseapp.username");
        String token   = Play.configuration.getProperty("highriseapp.token");
        try
        {
        	Integer count = extractcontact.getContent(token, username, tag);  	
        	renderJSON(count);
        }
        catch (Exception e) 
        {
            renderJSON("{\"error\":\"" + e + "\"}");
        }
    }

}