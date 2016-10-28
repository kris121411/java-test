package controllers;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.util.Base64;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import models.Contact;
import classes.*;

//this class generate xml data from api and parse to respective classes  
public class extractcontact {

	
	// FUNCTION that generate an xmlsource under tags.xml and people.xml in the apiurl
	public static Integer getContent(String token, String username, String tag) throws Exception {
		Integer sourcecontact = null;
		String tagpath = "tags.xml";
		String xmlsourcetag = getxml( tagpath, username, token);
		Serializer serializer = new Persister();
        Tags tags = serializer.read(Tags.class, xmlsourcetag);
        Integer tag_ID = 0;
        for (int i=0; i<tags.getTags().size(); i++){
	       if(tag.toLowerCase().equals(tags.getTags().get(i).getName().toLowerCase()))
	       {
	    	   tag_ID = tags.getTags().get(i).getId();
               break;
	       }
	    }
        if (tag_ID > 0) {
        	String peoplepath = "people.xml";
        	sourcecontact = getContactsPerson(
        			getPeopleBaseonTagValue(peoplepath, "tag_id", tag_ID, username, token)
            );
        }
       
		return sourcecontact;
	}


	// FUNCTION that generate an xmlsource under people.xml in the apiurl
	private static Integer getContactsPerson(String peopleBaseonTagValue) throws Exception
	{
		Integer count = null ;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Date.class, new SetDateFormat(format));
        Serializer serializer = new Persister(matcher);
        People people = serializer.read(People.class, peopleBaseonTagValue);
		for (int i=0; i<people.getPersons().size(); i++){
			count = Contact.saveperson(people.getPersons().get(i));
 	    }
        return count ;
	}
	// FUNCTION that generate an xmlsource under people.xml under base on the input tag in the apiurl
	private static String getPeopleBaseonTagValue(String peoplepath,
			String var_param, Integer val_param, String username, String token) throws UnsupportedEncodingException {
		
		
		String basicAuth = Base64.encodeAsString((token + ":x").getBytes("utf-8"));
        String url = "https://" + username + ".highrisehq.com";
        Client client = ClientBuilder.newClient();
        String xmlsource = client.target(url)
                        .path(peoplepath)
                        .queryParam(var_param, val_param)
                        .request(MediaType.APPLICATION_XML)
                        .header("authorization", "Basic " + basicAuth)
                        .get(String.class);
        return xmlsource;
	}
	//FUNCTION that generate an xmlsource under tags.xml in the apiurl
	private static String getxml(String path, String username, String token) throws UnsupportedEncodingException {
		String basicAuth = Base64.encodeAsString((token + ":x").getBytes("utf-8"));
        String url = "https://" + username + ".highrisehq.com";
        Client client = ClientBuilder.newClient();
        String xmlsource = client.target(url)
                        .path(path)
                        .request(MediaType.APPLICATION_XML)
                        .header("authorization", "Basic " + basicAuth)
                        .get(String.class);

        return xmlsource;
	}


}
