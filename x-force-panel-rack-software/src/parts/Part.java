package parts;

import java.io.Serializable;

public class Part implements Serializable, Comparable<Part>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String job_number; //#1 tag field
	private String aircraft_id_number; //#2 tag field
    private String deposit_date; //"month/day/year"
    private String withdraw_date;
    private String quantity; //#9 tag field saved in template
    private String part_number; //#11 tag field saved in template
    private String serial_number; //#12 tag field
    private String part_name; //#14 tag field saved in template
    private String removal_reason; //#14 tag field
    private User person_info; //#14 tag field
    private boolean isServiceable;
    private boolean inUse;
    // TODO deal with disc_date
    
    public Part() {
    	this.inUse = false;
    }
    
    /**
     * 
     * @return job number of the part
     */
    public String getJobNumber() {
    	return this.job_number;
    }
    
    /**
     * 
     * @return aircraft tail id of the part
     */
    public String getAircraftNumber() {
    	return this.aircraft_id_number;
    }
    
    /**
     * 
     * @return deposit date of the part
     */
    public String getDepositDate() {
    	return this.deposit_date;
    }
    
    /**
     * 
     * @return withdraw date of the part
     */
    public String getWithdrawDate() {
    	return this.withdraw_date;
    }
    
    /**
     * 
     * @return quantity of the part
     */
    public String getQuantity() {
    	return this.quantity;
    }
    
    /**
     * 
     * @return part number of the part
     */
    public String getPartNumber() {
    	return this.part_number;
    }
    
    /**
     * 
     * @return serial number of the part
     */
    public String getSerialNumber() {
    	return this.serial_number;
    }
    
    /**
     * 
     * @return part name of the part
     */
    public String getPartName() {
    	return this.part_name;
    }
    
    /**
     * 
     * @return removal reason of the part
     */
    public String getRemovalReason() {
    	return this.removal_reason;
    }
    
    /**
     * 
     * @return true if the part is serviceable, false otherwise
     */
    public boolean getIsServiceable() {
    	return this.isServiceable;
    }
    
    /**
     * 
     * @return The User object of who deposited the part
     */
    public User getPersonInfo() {
    	return this.person_info;
    }
    
    public void setJobNumber(String input) {
    	this.inUse = true;
    	this.job_number = input;
    }
    
    public void setAircraftNumber(String input) {
    	this.inUse = true;
    	this.aircraft_id_number = input;
    }
    
    public void setDepositDate(String input) {
    	this.inUse = true;
    	this.deposit_date = input;
    }
    
    public void setWithdrawDate(String input) {
    	this.withdraw_date = input;
    }
    
    public void setQuantity(String input) {
    	this.inUse = true;
    	this.quantity = input;
    }
    
    public void setPartNumber(String input) {
    	this.inUse = true;
    	this.part_number = input;
    }
    
    public void setSerialNumber(String input) {
    	this.inUse = true;
    	this.serial_number = input;
    }
    
    public void setPartName(String input) {
    	this.inUse = true;
    	this.part_name = input;
    }
    
    public void setIsServiceable(boolean input) {
    	this.inUse = true;
    	this.isServiceable = input;
    }
    
    public void setRemovalReason(String input) {
    	this.inUse = true;
    	this.removal_reason = input;
    }
    
    public void setPersonInfo(User input) {
    	this.inUse = true;
    	this.person_info = input;
    }
    
    public boolean isInUse() {
    	return this.inUse;
    }
    
    //ACFT,JCN,PART #,PART NAME,SERIAL #,DATE LOGGED IN,NAME & EMPLOYEE #,DATE LOGGED OUT
    /**
     * 
     * @return A string that represents a line of the log csv
     */
    public String toLogString() {
    	String line = "";
    	
    	line += this.aircraft_id_number;
    	line += ",";
    	
    	line += this.job_number;
    	line += ",";
    	
    	line += this.part_number;
    	line += ",";
    	
    	line += this.part_name;
    	line += ",";
    	
    	line += this.serial_number;
    	line += ",";
    	
    	line += this.deposit_date;
    	line += ",";
    	
    	line += this.person_info.getName();
    	line += " ";
    	line += this.person_info.getId();
    	line += ",,";
    	
    	
    	return line;
    }
    
    
    // Unique info: "Serial Number:, Job Number:, AircraftId Number:\n"
    // Non-Unique Info: "Part Name:, Part Number:, Quantity:, Date:\n"
    // Personnel Info: "Name:, ID:, Rank:\n"
    // Other Info: "Removal Reason:"
    @Override
    public String toString() {
    	formatRemovalReason();
    	String output = "";
    	
    	// Unique info
    	output += "Job Number:";
    	output += this.job_number;
    	output += "|A/C Tail#:";
    	output += this.aircraft_id_number;
    	output += "\nSerial Number:";
    	output += this.serial_number;
    	
    	// Non-Unique info
    	output += "\n\nPart Name:";
    	output += this.part_name;
    	output += "|Part Number:";
    	output += this.part_number;
    	output += "\nQuantity:";
    	output += this.quantity;
    	output += "\n\nDeposit Date:";
    	output += this.deposit_date;
    	
    	// Personnel Info
    	output += "\nName:";
    	output += this.person_info.getName();
    	output += "|Man #:";
    	output += this.person_info.getId();
    	output += "|Rank:";
    	output += this.person_info.getRank();
    	
    	// Other Info
    	//format removal reason so it isn't too long on one line
    	output += "\n\nRemoval Reason:\n";
    	output += formatRemovalReason();
    	output += "\nServiceable:";
    	if(this.isServiceable) {
    		output += "yes";
    	} else {
    		output += "no";
    	}
    	//output += "\n";
    	
    	
    	return output;
    }
    
    /**
     * Formats the removal reason to make sure each line is under a certain amount of characters
     * @return the formatted removal reason
     */
    private String formatRemovalReason() {
    	String formattedString = "";
    	//TODO change this value and test with printer.
    	int maxCharsPerLine = 30;
    	String[] wordsArray = this.removal_reason.split("\\s+");
    	
    	int curCharLen = 0;
    	int temp;
    	for(String word : wordsArray) {
    		temp = word.length();
    		if(curCharLen + temp > maxCharsPerLine) {
    			if(formattedString.charAt(formattedString.length() - 1) == ' ') {
    				formattedString = formattedString.substring(0, formattedString.length() - 1);
    			}
    			formattedString += "\n";
    			curCharLen = 0;
    		}
    		curCharLen += temp;
    		formattedString += word;
    		formattedString += " ";
    		curCharLen += 1;
    	}
    	
    	
    	return formattedString;
    }

	@Override
	public int compareTo(Part o) {
		String thisDepositDate = this.deposit_date;
		String otherDepositDate = o.getDepositDate();
		
		return thisDepositDate.compareTo(otherDepositDate);
	}
}
