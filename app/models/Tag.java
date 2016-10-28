package models;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
//this models is contains tag data from the API and act as database table for  List of Tag
@Entity
public class Tag extends Model{

    private String name;
    @ManyToMany(mappedBy="tags") 
    public List<Contact> contacts = new ArrayList<Contact>(); 
    public Tag(String name) {
        this.name= name;
    }
    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("byName", name).first();
        if(tag == null) {
            tag = new Tag(name);
        }
        return tag;
    }
    public String toJson() {
        return "{\"name\":" + ((this.getName() != null) ? "\"" + this.getName() + "\"" : "null") + "}";
    }
    public String toJson(String parent) {
        String json = "{\"" + parent + "\":";
        json += this.toJson();
        json += "}";

        return json;
    }
    public String toString() {
        return this.getName();
    }
    public String getName() {
        return this.name;
    }
}
