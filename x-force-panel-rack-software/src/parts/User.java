package parts;

import java.io.Serializable;

public class User implements Serializable, Comparable<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String rank;
	private String id; //AKA man#
	@SuppressWarnings("unused")
	private String password; //Unused for now
	
	/**
	 * Default constructor that creates a User object with all fields set to empty strings.
	 * 
	 */
	public User() {
		this.name = "";
		this.rank = "";
		this.id = "";
	}
	
	/**
	 * Constructor for User that sets the object's name, rank, and id fields with the parameters.
	 * 
	 * @param name
	 * @param rank
	 * @param id
	 */
	public User(String name, String rank, String id) {
		this.name = name;
		this.rank = rank;
		this.id = id;
	}
	
	/**
	 * Constructor for User that sets the object's name, rank, id, and password fields with the parameters.
	 * 
	 * @param name
	 * @param rank
	 * @param id
	 */
	public User(String name, String rank, String id, String password) {
		this.name = name;
		this.rank = rank;
		this.id = id;
		this.password = password;
	}
	
	/**
	 * Getter method for the name field.
	 * 
	 * @return the name of the User
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method for the rank field.
	 * 
	 * @return the rank of the User
	 */
	public String getRank() {
		return rank;
	}
	
	/**
	 * Getter method for the id field.
	 * 
	 * @return the id of the User
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Setter method for the name field.
	 */
	public void setName(String input) {
		this.name = input;
	}
	
	/**
	 * Setter method for the rank field.
	 */
	public void setRank(String input) {
		this.rank = input;
	}
	
	/**
	 * Setter method for the id field.
	 */
	public void setId(String input) {
		this.id = input;
	}
	
	// "Name:, ID:, Rank:"
	/**
	 * toString method
	 */
	@Override
	public String toString() {
		String output = "Name:";
		output += this.name;
		output += ", Man#:";
		output += this.id;
		output += ", Rank:";
		output += this.rank;
		
		return output;
	}
	
	/**
	 * Overwrites the default compareTo method used for sorting.
	 * 
	 * Users will be sorted by their last name. If a User only has a first name,
	 * their first name will be compared instead.
	 */
	@Override
	public int compareTo(User arg0) {
		String thisName = this.name.toLowerCase();
		String otherName = arg0.getName().toLowerCase();
		if(thisName.equals(otherName)) {
			return 0;
		}
		
		
		String[] thisNameSplit = thisName.split("\\s+");
		String[] otherNameSplit = otherName.split("\\s+");
		String thisLastName;
		String otherLastName;
		
		if(thisNameSplit.length == 0) {
			thisLastName = thisName;
		} else {
			thisLastName = thisNameSplit[thisNameSplit.length - 1];
		}
		
		if(otherNameSplit.length == 0) {
			otherLastName = otherName;
		} else {
			otherLastName = otherNameSplit[otherNameSplit.length - 1];
		}
		
		int result = thisLastName.compareTo(otherLastName);
		
		if(result == 0) {
			return thisName.compareTo(otherName);
		}
		
		return result;
		
	}
}
