package models;
import java.util.*;

import javax.persistence.*;

import classes.Address;
import classes.Person;

import play.db.jpa.*;

import models.*;
//this models is contains contact data from the API and act as database table for  List of Contact
@Entity
public class Contact extends Model {


    private String firstName;
    private String lastName;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String companyName;

    @Column(nullable = true)
    private String background;

    @Column(nullable = true)
    private String linkedinUrl;

    @Column(nullable = true)
    private String MessengerProtocol;

    @Column(nullable = true)
    private String MessengerAddress;

    @Column(nullable = true)
    private String emailAddress;

    @Column(nullable = true)
    private String twitterAccount;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String webAddress;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags ;

    private Date createdAt;
    private Date updatedAt;
	private Date createAt;

	public Contact(String firstname2, String lastname2, Date createdAt2,
			Date updatedAt2, String title2, String companyName2,
			String background2, String linkedinUrl2, String protocol,
			String address2, String twitterAccounts, String aAddress,
			String webAddress2, String webAddress3, List<Tag> tagname) {
		this.firstName = firstname2;
		this.lastName = lastname2;
		this.createAt = createdAt2;
		this.updatedAt = updatedAt2;
		this.title = title2;
		this.companyName = companyName2;
		this.background = background2;
		this.linkedinUrl = linkedinUrl2;
		this.MessengerProtocol = protocol;
		this.MessengerAddress = address2;
		this.twitterAccount = twitterAccounts;
		this.emailAddress = webAddress3;
		this.address = aAddress;
		this.webAddress = webAddress2;
		this.tags =  tagname;
	}
	public static Integer saveperson(Person person) {
		
		String Firstname = person.getFirstName();
		String Lastname = person.getLastName();
        Date CreatedAt = person.getCreatedAt();
        Date UpdatedAt  = person.getUpdatedAt();
        String Title = person.getTitle();
        String CompanyName = person.getCompanyName();
        String Background = person.getBackground();
        String LinkedinUrl = person.getLinkedinUrl();
        String Protocol =((person.getContactData().getMessengers().size() > 0)
        		? person.getContactData().getMessengers().get(0).getProtocol(): null);
        String Address = ((person.getContactData().getMessengers().size() > 0)
        		? person.getContactData().getMessengers().get(0).getAddress(): null);
        String EmailAddress = ((person.getContactData().getEmailAddresses().size() > 0)
        		? person.getContactData().getEmailAddresses().get(0).getAddress(): null);
        String TwitterAccounts = ((person.getContactData().getTwitterAccounts().size() > 0)
        		? person.getContactData().getTwitterAccounts().get(0).getUsername(): null);
        String AAddress = ((person.getContactData().getAddresses().size() > 0)
                ? addressToString(person.getContactData().getAddresses().get(0)): null);
        String WebAddress = ((person.getContactData().getWebAddresses().size() > 0)
                ? person.getContactData().getWebAddresses().get(0).getUrl(): null);
        List tagname = new ArrayList<Tag>();
        for (classes.Tag tag : person.getTags()) {
        	tagname.add(Tag.findOrCreateByName(tag.getName()));
        }
       
        String query = "Select c FROM Contact c where c.firstName = "+"'"+Firstname +"'"+ " and c.lastName = "+"'"+Lastname+"'";
		int count = Contact.find(query).fetch().size();
		Integer counter = 0;
		if(count==0)
		{
			counter++;
			Contact contacts  = new Contact (Firstname,Lastname,CreatedAt,UpdatedAt,Title,CompanyName,Background,LinkedinUrl,
					Protocol,Address,EmailAddress,TwitterAccounts,AAddress,WebAddress,tagname).save();
		}
		else
		{
			counter++;
		}
		return counter;  
	}
	private static String addressToString(Address address) {
	        String comma = ", ";
	        String stringAddress = address.getStreet() + comma +
	                            address.getCity() + comma +
	                            address.getState() + comma +
	                            Integer.toString(address.getZip()) + comma +
	                            address.getCountry();
	        return stringAddress;
	 }
	
	public String toJson() {
        String json = "";
        json += "{";
        json += "\"firstName\":" + this.formatString(this.getFirstName()) + ",";
        json += "\"lastName\":" + this.formatString(this.getLastName()) + ",";
        json += "\"title\":" + this.formatString(this.getTitle()) + ",";
        json += "\"companyName\":" + this.formatString(this.getCompanyName()) + ",";
        json += "\"background\":" + this.formatString(this.getBackground()) + ",";
        json += "\"linkedinUrl\":" + this.formatString(this.getLinkedinUrl()) + ",";
        json += "\" MessengerProtocol\":" + this.formatString(this.getMessengerProtocol()) + ",";
        json += "\"MessengerAddress\":" + this.formatString(this.getMessengerAddress()) + ",";
        json += "\"emailAddress\":" + this.formatString(this.getEmailAddress()) + ",";
        json += "\"twitterAccount\":" + this.formatString(this.getTwitterAccount()) + ",";
        json += "\"address\":" + this.formatString(this.getAddress()) + ",";
        json += "\"webAddress\":" + this.formatString(this.getWebAddress());
        json += "}";

        return json;
    }
	public String toJson(String parent) {
        String json = "{\"" + parent + "\":";
        json += this.toJson();
        json += "}";

        return json;
    }
	private String getLastName() {
		 return this.lastName;
	}
	public String getFirstName() {
        return this.firstName;
    }
	public String getTitle() {
        return this.title;
    }
	public String getCompanyName() {
        return this.companyName;
    }
	public String getBackground() {
        return this.background;
    }
	public String getLinkedinUrl() {
        return this.linkedinUrl;
    }
	 public String getMessengerProtocol() {
	        return this.MessengerProtocol;
	}
	 public String getMessengerAddress() {
	        return this.MessengerAddress;
	}
	 public String getEmailAddress() {
	        return this.emailAddress;
	 }
	 public String getTwitterAccount() {
	        return this.twitterAccount;
	 }
	public String getAddress() {
        return this.address;
    }
	public String getWebAddress() {
        return this.webAddress;
    }
	private String formatString(String str) {
        return (str != null) ? "\"" + str + "\"" : "null";
    }
}
