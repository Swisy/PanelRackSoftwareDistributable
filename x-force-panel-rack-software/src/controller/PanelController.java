package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import model.PanelModel;
import parts.Part;
import parts.PartTemplate;
import parts.User;

public class PanelController {
	private PanelModel model;
	
	public PanelController(PanelModel model) {
		this.model = model;
	}
	
	/**
	 * Adds piece to model and creates template for the piece if there isn't one.
	 */
	public boolean addPart(Part piece) {
		if(!model.hasTemplate(piece.getPartNumber())) {
			model.addTemplate(piece);
		}
		return this.model.addToModel(piece);
	}
	
	/**
	 * Checks if there is a template for the associated part number stored in the model.
	 * 
	 * Returns true if there is a matching template in the model, and false if there is not.
	 */
	public boolean hasTemplate(String partNumber) {
		return model.hasTemplate(partNumber);
	}
	
	/**
	 * Adds a template to the model
	 */
	public void addTemplate(PartTemplate template) {
		model.addTemplateFromTemplate(template);
	}
	
	/**
	 * Removes a template from the model
	 */
	public void removeTemplate(PartTemplate template) {
		model.removeTemplate(template);
	}
	
	/**
	 * Checks if there is a part for the associated serial number stored in the model.
	 * 
	 * Returns true if there is a matching part in the model, and false if there is not.
	 */
	public boolean hasPart(String serial) {
		return model.hasPart(serial);
	}
	
	/**
	 * Removes the passed part from the model and returns true if the operation was successful and
	 * false if the part was not in the model and thus could not be removed.
	 */
	public boolean removePart(Part piece) {
		return this.model.removeFromModel(piece);
	}
	
	/**
	 * Returns true if there is a User object for the associated id in the model and false if there
	 * isn't.
	 */
	public boolean profileExists(String personId) {
		return model.hasProfile(personId);
	}
	
	/**
	 * Returns the User object with the passed id that is stored in the model. If there is no User
	 * object with the passed id, this method will return an empty User object where all the fields
	 * are empty strings.
	 */
	public User getProfile(String personId) {
		return model.getUser(personId);
	}
	
	/**
	 * Creates a new User object with the passed fields and adds it to the model.
	 */
	public void addUser(String id, String name, String rank) {
		User newUser = new User(name, rank, id);
		model.addUser(newUser);
	}
	
	/**
	 * Returns an array of all the part templates stored in the model.
	 */
	public PartTemplate[] getTemplatesArray() {
		return this.model.getTemplatesArray();
	}
	
	/**
	 * Returns the PartTemplate with the passed part number stored in the model.
	 */
	public PartTemplate getTemplate(String partNumber) {
		return model.getTemplate(partNumber);
	}
	
	/**
	 * Returns an array containing all of the Part objects currently stored in the model.
	 */
	public Part[] getPartsArray() {
		return this.model.getModelArray();
	}
	
	/**
	 * Returns an array containing all of the Part objects currently stored in the model.
	 */
	public User[] getUsersArray() {
		return this.model.getUserArray();
	}
	
	/**
	 * Returns the Part with the passed serial stored in the model.
	 */
	public Part getPart(String serial) {
		return model.getPart(serial);
	}
	
	/**
	 * Prints out the contents of the model to the console.
	 */
	public void printModel() {
		model.printModel();
	}
	
	/**
	 * This method will create a save file containing the current state of the model.
	 * 
	 * @param saveFileName The name of the save file that will be created.
	 * 
	 * @return returns true if successful and false if an error was caught.
	 */
	public boolean saveModel(String saveFileName) {
		try {
	    	File newFile = new File(saveFileName);
			newFile.createNewFile();
	    	FileOutputStream saveToFile = new FileOutputStream(saveFileName, false);
		    ObjectOutputStream outputStream = new ObjectOutputStream(saveToFile);
			outputStream.writeObject(model);
			outputStream.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Fills in the DefaultTableModel that is used by the table in the log history pane by reading
	 * in the csv document that is named log.csv by default.
	 * 
	 * @param logFileName
	 * @param logModel
	 */
	public void fillLogModel(String logFileName, DefaultTableModel logModel){
		String line;
		String[] commaSplit;
		try {
            File f1 = new File(logFileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                commaSplit = line.split(",", 9);
                try {
                logModel.addRow(new Object[] {commaSplit[0], commaSplit[1], commaSplit[2],commaSplit[3],
                		commaSplit[4], commaSplit[5], commaSplit[6], commaSplit[7], commaSplit[8]});
                } catch(Exception ex) {
                }
            }
            fr.close();
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	//ACFT,JCN,PART #,PART NAME,SERIAL #,DATE LOGGED IN,NAME & EMPLOYEE #,DATE LOGGED OUT,NAME & EMPLOYEE #
	/**
	 * Adds a row of data to the log csv file.
	 * 
	 * @param logFileName
	 * @param line
	 */
	public void writeLineToLog(String logFileName, String line) {
		File file = new File(logFileName);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(line);
            fr.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Updates the log csv file to fill in the withdraw information when a part is withdrawn.
	 * 
	 * @param logFileName
	 * @param partSerial
	 * @param depositDate
	 * @param withdrawDate
	 * @param curId
	 */
	public void addWithdrawDate(String logFileName, String partSerial, String depositDate,
			String withdrawDate, String curId) {
		List<String> lines = new ArrayList<String>();
		String line;
		String[] commaSplit;
		try {
            File f1 = new File(logFileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.contains(partSerial) && line.contains(depositDate)) {
                	commaSplit = line.split(",", 9);
                	commaSplit[7] = withdrawDate;
                	User curUser = getProfile(curId);
                	String cur = "";
                	cur += curUser.getName();
                	cur += " ";
                	cur += curUser.getId();
                	commaSplit[8] = cur;
                	line = "";
                	for(int i = 0; i < commaSplit.length; i++) {
                		line += commaSplit[i];
                		if(i != 8) {
                			line += ",";
                		}
                	}
                }
                // If there is no new line at end of the line, add one.
                if((line.length() == 0) || (line.charAt(line.length() - 1) != '\n')) {
                	line += "\n";	
                }
                lines.add(line);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(s);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Copies the contents of one file to another file, creates the destination file.
	 * 
	 * @param originFileName The file name of the file that will be copied
	 * @param destinationFileName The file name of the file that will be the copy
	 */
	public void copyFile(String originFileName, String destinationFileName) {
		List<String> lines = new ArrayList<String>();
		String line;
		try {
            File f1 = new File(originFileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                // If there is no new line at end of the line, add one.
                if((line.length() == 0) || (line.charAt(line.length() - 1) != '\n')) {
                	line += "\n";	
                }
                lines.add(line);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(destinationFileName);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(s);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public String getPrinterName(String configFileName) {
		String line;
		String printerName = "";
		try {
            File f1 = new File(configFileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            if ((line = br.readLine()) != null) {
                int nameStart = line.indexOf('\"');
                int nameEnd = line.lastIndexOf('\"');
                if(nameStart != nameEnd) {
                	printerName = line.substring(nameStart + 1, nameEnd);	
                }
            }
            fr.close();
            br.close();
        } catch (Exception ex) {
            return printerName;
        }
		
		return printerName;
	}
}
