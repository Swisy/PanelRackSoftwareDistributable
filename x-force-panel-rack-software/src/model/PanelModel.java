package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parts.Part;
import parts.PartTemplate;
import parts.User;

public class PanelModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Part> panels;
	private HashMap<String, PartTemplate> templates;
	private HashSet<User> users;
	
	public PanelModel() {
		this.panels = new HashSet<Part>();
		this.templates = new HashMap<String, PartTemplate>();
		this.users = new HashSet<User>();
	}
	
	/**
	 * Adds a Part to the set of Parts stored in the model called "panels"
	 * 
	 * @param piece the Part that will be added to the model
	 * @return true if the part was successfully added, false otherwise
	 */
	public boolean addToModel(Part piece) {
		return this.panels.add(piece);
	}
	
	/**
	 * Removes a Part from the set of Parts stored in the model called "panels"
	 * 
	 * @param piece the Part that will be removed from the model
	 * @return true if the part was successfully removed, false otherwise
	 */
	public boolean removeFromModel(Part piece) {
		return this.panels.remove(piece);
	}
	
	/**
	 * 
	 * @param partNumber the partNumber of the template
	 * @return true if a template with the partNumber exists, false otherwise
	 */
	public boolean hasTemplate(String partNumber) {
		return templates.containsKey(partNumber);
	}
	
	/**
	 * Creates a PartTemplate from the Part piece and adds it to the HashMap templates
	 * 
	 * @param piece The Part that will be used to create a template
	 */
	public void addTemplate(Part piece) {
		String part_number = piece.getPartNumber();
		PartTemplate new_template = new PartTemplate(piece);
		templates.put(part_number, new_template);
	}
	
	/**
	 * Adds a PartTemplate to the HashMap templates
	 * 
	 * @param template the PartTemplate that will be added
	 */
	public void addTemplateFromTemplate(PartTemplate template) {
		templates.put(template.getPartNumber(), template);
	}
	
	/**
	 * Removes a PartTemplate from the HashMap templates
	 * 
	 * @param template the PartTemplate that will be removed
	 */
	public void removeTemplate(PartTemplate template) {
		templates.remove(template.getPartNumber());
	}
	
	/**
	 * 
	 * @param userId
	 * @return true if a user with an id of userId exists, false if it doesn't
	 */
	public boolean hasProfile(String userId) {
		for(User guy : this.users) {
			if(guy.getId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a User to the HashSet of users stored in this model
	 * 
	 * @param person the User being added
	 */
	public void addUser(User person) {
		users.add(person);
	}
	
	/**
	 * 
	 * @param profileId
	 * @return the User object stored in the model with an id of profileId
	 */
	public User getUser(String profileId) {
		for(User guy : this.users) {
			if(guy.getId().equals(profileId)) {
				return guy;
			}
		}
		return new User();
	}
	
	/*
	 * Gets the template for a piece of specified part number
	 */
	public PartTemplate getTemplate(String partNumber) {
		return templates.get(partNumber);
	}
	
	/*
	 * Gets the values of the templates map in array form
	 */
	public PartTemplate[] getTemplatesArray() {
		int size = this.templates.size();
		PartTemplate[] list = this.templates.values().toArray(new PartTemplate[size]);
		Arrays.sort(list);
		return list;
	}
	
	/*
	 * Gets the set of parts in array form
	 */
	public Part[] getModelArray() {
		int size = this.panels.size();
		Part[] list = this.panels.toArray(new Part[size]);
		Arrays.sort(list);
		return list;
	}
	
	/*
	 * Gets the set of users in array form
	 */
	public User[] getUserArray() {
		int size = this.users.size();
		User[] list = this.users.toArray(new User[size]);
		Arrays.sort(list);
		return list;
	}
		
	
	public boolean hasPart(String serial) {
		for(Part part : this.panels) {
			if(part.getSerialNumber().equals(serial)) {
				return true;
			}
		}
		return false;
	}
	
	public Part getPart(String serial) {
		for(Part piece : this.panels) {
			if(piece.getSerialNumber().equals(serial)) {
				return piece;
			}
		}
		return new Part();
	}
	
	/*
	 * Gets the array of parts
	 */
	public Set<Part> getModelSet(){
		return this.panels;
	}
	
	/*
	 * Gets the hashmap of part templates
	 */
	public HashMap<String, PartTemplate> getAllTemplates(){
		return this.templates;
	}
	
	public void printModel() {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Parts:");
		for(Part part : panels) {
			System.out.println(part.toString());
			System.out.println();
		}
		
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Part Templates:");
		for (Map.Entry<String, PartTemplate> set : templates.entrySet()) {
			System.out.println(set.getValue().toString());
			System.out.println();
		}
		
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Users:");
		for (User user : users) {
			System.out.println(user.toString());
			System.out.println();
       }
	}
	
}
