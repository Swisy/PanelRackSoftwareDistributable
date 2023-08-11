package parts;

import java.io.Serializable;

public class PartTemplate implements Serializable, Comparable<PartTemplate>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String quantity; //#9 tag field saved in template
    private String part_number; //#11 tag field saved in template
    private String part_name; //#14 tag field saved in template
    
    public PartTemplate(Part piece) {
    	this.quantity = piece.getQuantity();
    	this.part_number = piece.getPartNumber();
    	this.part_name = piece.getPartName();
    }
    
    public String getQuantity() {
    	return this.quantity;
    }
    
    public String getPartNumber() {
    	return this.part_number;
    }
    
    public String getPartName() {
    	return this.part_name;
    }
    
    public void setQuantity(String input) {
    	this.quantity = input;
    }
    
    public void setPartName(String input) {
    	this.part_name = input;
    }
    
    public void setPartNumber(String input) {
    	this.part_number = input;
    }
    
    @Override
    public String toString() {
    	String output = "Part Name:";
    	output += this.part_name;
    	output += ", Part Number:";
    	output += this.part_number;
    	output += ", Quantity:";
    	output += this.quantity;
    	
    	return output;
    }

    
    /**
	 * Overwrites the default compareTo method used for sorting.
	 * 
	 * Templates will be sorted by their part number.
	 */
	@Override
	public int compareTo(PartTemplate o) {
		String thisPartNumber = this.part_number;
		String otherPartNumber = o.getPartNumber();
		
		return thisPartNumber.compareTo(otherPartNumber);
	}
}
