import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.PanelController;
import model.PanelModel;
import parts.Part;
import parts.PartTemplate;
import parts.User;

/**
 * 
 * @author Saul Weintraub <siwsiwsiw6@gmail.com>
 * @version 1.0
 *
 */
public class PanelView {
	private static boolean adminMode = false; // enables or disables admin operations
	private static boolean saveBackups = false;
	private final static String adminCode = "setthisbeforecompiling";
	static String currentId; // the id of the user currently logged in
	static String saveFilesPath = System.getProperty("user.home") + "\\AppData\\Roaming\\PanelRackSoftware\\";
	static String saveName = "save.dat"; // the filename of the save file
	static String currentAircraftId; // the last used aircraft tail#
	static String currentJobNumber; // the last used job number
	static String backupSaveLocation = System.getProperty("user.home") + "\\AppData\\Roaming\\PanelRackSoftware\\Backups";
	static String logFileName = "log.csv"; // the filename of the log file
	static String operationsLogFileName = "operation-history.txt";
	static PanelController controller; // the controller
	private static TagPrinter printerService;
	private static String printerName = "";// Set this to the name of the printer to enable printing
	private static String configFileName = "config.txt"; //For now this only is used to set the printer name

	public static void main(String[] args) {
		// Enable admin mode if proper code was used as a command line argument
		if(args.length > 0) {
			if(args[0].equals(adminCode)) {
				adminMode = true;
			}
			//Print out the printers
			if(args[0].equals("getprinters")) {
				printerService = new TagPrinter();
				System.out.println(printerService.getPrinters());
				System.exit(0);
			}
		}
		
		try {
			// If the directory does not exist, make it
			Files.createDirectories(Paths.get(saveFilesPath));
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String loadSave = "yes";
		PanelModel model;
		if(loadSave.equals("yes")) {
			model = new PanelModel();
			try (ObjectInputStream objIn = 
					new ObjectInputStream(new FileInputStream(saveFilesPath + saveName));){
				model = (PanelModel) objIn.readObject();
			} catch (IOException | ClassNotFoundException | ClassCastException e) {
				model = new PanelModel();
			}
		} else {
			model = new PanelModel();
		}
		controller = new PanelController(model);
		printerService = new TagPrinter();
		printerName = controller.getPrinterName(saveFilesPath+configFileName);
		
		//If save backups are enabled, save a backup of the model at the beginning of the session
		//The backups will be saved at backupSaveLocation
		//The name of the backup will be formatted as "2023-08-02_11-26-3_(originalfilename)"
		//A backup will always be saved if launched in admin mode
		if(saveBackups || adminMode) {
			saveBackupFiles();
		}
		
		//Launch the GUI
		ViewGUI.launch();
	}
	
	/**
	 * Called by ViewGUI
	 * Runs when a user inputs their man id and presses the submit button on the login pane.
	 * Will check if the manId already has a profile. If it does then the user will be logged in
	 * and the ViewGUI will switch to the process selection pane. If the user does not exist then
	 * the ViewGui will initiate the profile creation process.
	 * 
	 * @param view
	 */
	public static void manSubmitAction(ViewGUI view) {
		String id = view.getManInput();
		currentId = id;
		
		// If profile does not exist, create profile
		if(!controller.profileExists(id)) {
			view.enableProfileCreation();
			return;
		}
		//RECORD LOGIN OPERATION
		recordLogin(id);
		
		// Otherwise change the view to the selection of deposit or withdraw
		view.switchToProcessSelection();
	}
	
	/**
	 * Called by ViewGUI
	 * Runs when a user presses the create new profile button after inputting their name and rank.
	 * Will create a new user with the information in the input fields in the ViewGUI and then
	 * set that user to the active user and switch the ViewGui to the proccess selection pane.
	 * 
	 * @param view
	 */
	public static void profileSubmitAction(ViewGUI view) {
		// TODO verify input is valid
		String name = view.getNameInput();
		String rank = view.getRankInput();
		controller.addUser(currentId, name, rank);
		controller.saveModel(saveFilesPath + saveName);
		
		//RECORD PROFILE CREATION OPERATION
		recordProfileCreation(currentId);
		
		// Change the view to the selection of deposit or withdraw
		view.switchToProcessSelection();
	}
	
	
	
	/**
	 * Called by ViewGUI
	 * Runs when the active pane switches to the log history pane. Fills in the table in the log
	 * history pane.
	 * 
	 * @param table
	 */
	public static void setUpLogTable(JTable table) {
		DefaultTableModel logModel = (DefaultTableModel) table.getModel();
		logModel.setRowCount(0);
		
		controller.fillLogModel(saveFilesPath + logFileName, logModel);
	}
	
	
	/*
	 * The following methods deal with depositing stuff.
	 */
	
	/**
	 * Called by ViewGUI
	 * Runs when the active pane switches to the deposit pane. Fills in the table in the template
	 * selection tab of the deposit pane.
	 * 
	 * @param table The table that will be filled in. Should be templateSelectionTable
	 */
	public static void setUpTemplateTable(JTable table) {
		DefaultTableModel templateModel = (DefaultTableModel) table.getModel();
		templateModel.setRowCount(0);
		
		PartTemplate[] templateArray = controller.getTemplatesArray();
		int numTemplates = templateArray.length;
		
		for(int i = 0; i < numTemplates; i++) {
			templateModel.addRow(new Object[] {templateArray[i].getPartNumber(), templateArray[i].getPartName()});
		}
		
	}
	
	/**
	 * Called by ViewGUI
	 * Runs when a row from the template table is selected. Fills in the inputs in tab 3 of the
	 * deposit pane with the information stored in the template.
	 * 
	 * @param view The GUI, should be ViewGUI
	 * @param partNumber the part number of the template that was selected from the template table
	 */
	public static void templateSelectedEvent(ViewGUI view, String partNumber) {
		PartTemplate template = controller.getTemplate(partNumber);
		view.fillInfoFromTemplate(template.getPartNumber(), template.getQuantity(), template.getPartName());
	}
	
	/*
	 * 0: job number
	 * 1: aircraft id
	 * 2: part serial
	 * 3: part name
	 * 4: part number
	 * 5: quantity
	 * 6: removal reason
	 */
	
	/**
	 * Called by ViewGUI
	 * Runs when the User presses the submit button on the fourth tab of the deposit pane. Creates
	 * a part object with the inputs from the user and adds it to the model. Should then print a tag
	 * with the information of the part (not implemented) and unlock the locks (not implemented)
	 * 
	 * @param view The GUI for the software, should be ViewGUI
	 */
	public static void depositPart(ViewGUI view) {
		
		Part newPart = new Part();
		String[] partInputs = view.getPartInputs();
		
		// Make sure there is not already a part with the entered serial
		if(controller.hasPart(partInputs[2])) {
			view.alertSerialExists();
			return;
		}
		
		// job number
		newPart.setJobNumber(partInputs[0]);
		
		// part number
		newPart.setPartNumber(partInputs[4]);
		
		// part name
		newPart.setPartName(partInputs[3]);
		
		
		// quantity
		newPart.setQuantity(partInputs[5]);
		
		// part serial number
		newPart.setSerialNumber(partInputs[2]);
		
		// aircraft ID
		newPart.setAircraftNumber(partInputs[1]);
		
		// reason for removal
		newPart.setRemovalReason(partInputs[6]);
		
		// is serviceable
		if(partInputs[7].equals("yes")){
			newPart.setIsServiceable(true);
		} else {
			newPart.setIsServiceable(false);
		}
		
		// Add user info and date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		newPart.setDepositDate(dtf.format(now));
		
		newPart.setPersonInfo(controller.getProfile(currentId));
		
		// Set current job and aircraft
		currentAircraftId = partInputs[1];
		currentJobNumber = partInputs[0];
		
		//TODO update template
		if(view.updateTemplateCheck() && controller.hasTemplate(partInputs[4])) {
			PartTemplate template = controller.getTemplate(partInputs[4]);
			template.setQuantity(partInputs[5]);
			template.setPartName(partInputs[3]);
		}
		
		// Add part to database
		controller.addPart(newPart);
		controller.saveModel(saveFilesPath + saveName);
		
		// RECORD DEPOSIT PART OPERATION
		recordDepositOperation(newPart.getSerialNumber());
		
		// Update log
		String logLine = newPart.toLogString();
		controller.writeLineToLog(saveFilesPath + logFileName, logLine);
		
		//TODO print tag
		if(printTag(newPart.toString())) {
			view.confirmTagPrint(newPart.toString());
		} else {
			view.alertNoPrint(newPart.toString());
		}
		
		
		//TODO THIS IS WHERE THE LOCKING MECHANISM WILL BE IMPLEMENTED
		// Open gate
		
		// Wait
		
		// Close gate
		
		//TODO add another part
		view.switchToProcessSelection();
	}
	
	/**
	 * Called by depositPart()
	 * 
	 * Should print the string tagString from the connected printer.
	 * 
	 * @param tagString The string representation of the tag of a part generated by the Part method
	 * toString()
	 * 
	 * @return true if a tag is expected to be printed, false otherwise
	 */
	public static boolean printTag(String tagString) {
		if(!printerService.hasPrinter(printerName)) {
			return false;
		}
		
		//Print the tag
		if(!printerName.equals("")) {
			printerService.printString(printerName, tagString);
			
			//cut the paper
			byte[] cutP = new byte[] { 0x1d, 'V', 1 };
			printerService.printBytes(printerName, cutP);
			return true;
		} else {
			System.out.println(tagString);	
			return false;
		}
		
	}
	
	
	/*
	 * The following methods deal with withdrawing stuff.
	 */
	
	/**
	 * Called by ViewGUI
	 * Runs when the active pane switches to the withdraw pane. Fills in the table in the withdraw
	 * pane.
	 * 
	 * @param table The table that will be filled in, should be withdrawSelectionTable
	 */
	public static void setUpWithdrawSelectionTable(JTable table) {
		DefaultTableModel partModel = (DefaultTableModel) table.getModel();
		partModel.setRowCount(0);
		
		Part[] partsArray = controller.getPartsArray();
		int numParts = partsArray.length;
		Part curPart;
		for(int i = 0; i < numParts; i++) {
			curPart = partsArray[i];
			partModel.addRow(new Object[] {curPart.getSerialNumber(), curPart.getAircraftNumber(),
					curPart.getPartNumber(), curPart.getPartName()});
		}
	}
	
	/**
	 * Called by ViewGui when the withdrawPartButton is pressed in the withdrawPane
	 * 
	 * Removes the part from the model, records the withdraw operation, and unlocks the locks(not
	 * implemented)
	 * 
	 * @param view The GUI for the software, should be ViewGUI
	 * @param serialNumber The serial number of the part being withdrawn
	 */
	public static void withdrawPart(ViewGUI view, String serialNumber) {
		Part piece = controller.getPart(serialNumber);
		if(piece.isInUse()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			piece.setWithdrawDate(dtf.format(now));
			controller.removePart(piece);
			controller.saveModel(saveFilesPath + saveName);
			// TODO add log
			controller.addWithdrawDate(saveFilesPath + logFileName, piece.getSerialNumber(), piece.getDepositDate(),
					piece.getWithdrawDate(), currentId);
		}
		
		//RECORD WITHDRAW OPERATION
		recordWithdrawOperation(serialNumber);
		
		//TODO
		// Open gate
		
		// Wait
		
		// Close gate
		view.switchToProcessSelection();
	}
	
	
	
	/*
	 * The following methods deal with the admin features of the software.
	 */
	
	
	/**
	 * Called by ViewGUI when switched to adminPartsPane
	 *
	 * Fills the adminPartTable from ViewGui with all of the parts currently stored in the model
	 * 
	 * @param table The table that will be filled, should be adminPartTable
	 */
	public static void setUpAdminPartTable(JTable table) {
		DefaultTableModel adminPartModel = (DefaultTableModel) table.getModel();
		adminPartModel.setRowCount(0);
		
		Part[] partsArray = controller.getPartsArray();
		int numParts = partsArray.length;
		
		//"Serial Number", "Job Number", "A/C Tail#", "Part Number", "Part Name", "Quantity", "Servicable"
		for(int i = 0; i < numParts; i++) {
			adminPartModel.addRow(new Object[] {partsArray[i].getSerialNumber(), partsArray[i].getJobNumber(),
					partsArray[i].getAircraftNumber(), partsArray[i].getPartNumber(), partsArray[i].getPartName(),
					partsArray[i].getQuantity(), partsArray[i].getIsServiceable()});
		}
		
	}
	
	/**
	 * Called by ViewGUI when an admin updates a part from the adminPartTable. Creates a
	 * confirmation string that will be used in a dialogue to confirm if the user wants to make the
	 * changes to the part.
	 * 
	 * @param adminPartTable The table the user edited to make updates to a part, should be 
	 * adminPartTable
	 * 
	 * @param selectedRow The index of the selected row of the table, is the row that has the part
	 * that will be updated
	 * 
	 * @return A string detailing all the proposed changes made to the part
	 */
	public static String createUpdatePartString(JTable adminPartTable, int selectedRow) {
		String msg = "Value Type: Previous Value -> New Value";
		String partSerial = (String) adminPartTable.getValueAt(selectedRow, 0);
		boolean anyUpdate = false;
		String curValue = "";
		String oldValue = "";
		
		//Job Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 1);
		oldValue = controller.getPart(partSerial).getJobNumber();
		if(!curValue.equals(oldValue)) {
			msg += "\nJob Number: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Aircraft Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 2);
		oldValue = controller.getPart(partSerial).getAircraftNumber();
		if(!curValue.equals(oldValue)) {
			msg += "\nA/C Tail#: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Part Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 3);
		oldValue = controller.getPart(partSerial).getPartNumber();
		if(!curValue.equals(oldValue)) {
			msg += "\nPart Number: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Part Name
		curValue = (String) adminPartTable.getValueAt(selectedRow, 4);
		oldValue = controller.getPart(partSerial).getPartName();
		if(!curValue.equals(oldValue)) {
			msg += "\nPart Name: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Quantity
		curValue = (String) adminPartTable.getValueAt(selectedRow, 5);
		oldValue = controller.getPart(partSerial).getQuantity();
		if(!curValue.equals(oldValue)) {
			msg += "\nQuantity: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Servicable
		curValue = "No";
		if((boolean) adminPartTable.getValueAt(selectedRow, 6)) {
			curValue = "Yes";
		}
		oldValue = "No";
		if(controller.getPart(partSerial).getIsServiceable()) {
			oldValue = "Yes";
		}
		if(!curValue.equals(oldValue)) {
			msg += "\nServicable: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		// If nothing was updated, return an empty string
		if(!anyUpdate) {
			return "";
		}
		
		return msg;
	}
	
	/**
	 * Called by ViewGUI when an admin updates a part from the adminPartTable and confirms the
	 * changes. Updates the part in the model from the changes made to the table.
	 * 
	 * @param adminPartTable The table the user edited to make updates to a part, should be 
	 * adminPartTable
	 * 
	 * @param selectedRow The index of the selected row of the table, is the row that has the part
	 * that will be updated
	 */
	public static void updatePartFromAdminTable(JTable adminPartTable, int selectedRow) {
		if(!inAdminMode()) {
			return;
		}
		Part partToUpdate = controller.getPart((String) adminPartTable.getValueAt(selectedRow, 0));
		
		String curValue = "";
		String oldValue = "";
		
		//Job Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 1);
		oldValue = partToUpdate.getJobNumber();
		if(!curValue.equals(oldValue)) {
			partToUpdate.setJobNumber(curValue);
		}
		
		//Aircraft Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 2);
		oldValue = partToUpdate.getAircraftNumber();
		if(!curValue.equals(oldValue)) {
			partToUpdate.setAircraftNumber(curValue);
		}
		
		//Part Number
		curValue = (String) adminPartTable.getValueAt(selectedRow, 3);
		oldValue = partToUpdate.getPartNumber();
		if(!curValue.equals(oldValue)) {
			partToUpdate.setPartNumber(curValue);
		}
		
		//Part Name
		curValue = (String) adminPartTable.getValueAt(selectedRow, 4);
		oldValue = partToUpdate.getPartName();
		if(!curValue.equals(oldValue)) {
			partToUpdate.setPartName(curValue);
		}
		
		//Quantity
		curValue = (String) adminPartTable.getValueAt(selectedRow, 5);
		oldValue = partToUpdate.getQuantity();
		if(!curValue.equals(oldValue)) {
			partToUpdate.setQuantity(curValue);
		}
		
		//Servicable
		Boolean servicable = (Boolean) adminPartTable.getValueAt(selectedRow, 6);
		if(!servicable.equals(partToUpdate.getIsServiceable())) {
			partToUpdate.setIsServiceable(servicable);
		}
		
		controller.saveModel(saveFilesPath + saveName);
	}
	
	/**
	 * Called by ViewGUI when switched to adminUsersPane
	 *
	 * Fills the adminUserTable from ViewGui with all of the Users currently stored in the model
	 * 
	 * @param table The table that will be filled, should be adminUserTable
	 */
	public static void setUpAdminUserTable(JTable table) {
		DefaultTableModel adminUserModel = (DefaultTableModel) table.getModel();
		adminUserModel.setRowCount(0);
		
		User[] usersArray = controller.getUsersArray();
		int numUsers = usersArray.length;
		
		//"Man Id", "Name", "Rank", User Object (hidden)
		for(int i = 0; i < numUsers; i++) {
			adminUserModel.addRow(new Object[] {usersArray[i].getId(), usersArray[i].getName(),
					usersArray[i].getRank(), usersArray[i]});
		}
		
	}
	
	/**
	 * Called by ViewGUI when an admin updates a User from the adminUserTable. Creates a
	 * confirmation string that will be used in a dialogue to confirm if the admin wants to make the
	 * changes to the user.
	 * 
	 * @param adminUserTable The table the admin edited to make updates to a user, should be 
	 * adminUserTable
	 * 
	 * @param selectedRow The index of the selected row of the table, is the row that has the user
	 * that will be updated
	 * 
	 * @return A string detailing all the proposed changes made to the user
	 */
	public static String createUpdateUserString(JTable adminUserTable, int selectedRow) {
		String msg = "Value Type: Previous Value -> New Value";
		User oldUser = (User) adminUserTable.getModel().getValueAt(selectedRow, 3);
		boolean anyUpdate = false;
		String curValue = "";
		String oldValue = "";
		
		//Id
		curValue = (String) adminUserTable.getValueAt(selectedRow, 0);
		oldValue = oldUser.getId();
		if(!curValue.equals(oldValue)) {
			msg += "\nMan Id: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Name
		curValue = (String) adminUserTable.getValueAt(selectedRow, 1);
		oldValue = oldUser.getName();
		if(!curValue.equals(oldValue)) {
			msg += "\nName: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Rank
		curValue = (String) adminUserTable.getValueAt(selectedRow, 2);
		oldValue = oldUser.getRank();
		if(!curValue.equals(oldValue)) {
			msg += "\nRank: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		
		// If nothing was updated, return an empty string
		if(!anyUpdate) {
			return "";
		}
		
		return msg;
	}
	
	/**
	 * Called by ViewGUI when an admin updates a user from the adminUserTable and confirms the
	 * changes. Updates the user in the model from the changes made to the table.
	 * 
	 * @param adminUserTable The table the admin edited to make updates to a user, should be 
	 * adminUserTable
	 * 
	 * @param selectedRow The index of the selected row of the table, is the row that has the user
	 * that will be updated
	 */
	public static void updateUserFromAdminTable(JTable adminUserTable, int selectedRow) {
		if(!inAdminMode()) {
			return;
		}
		User userToUpdate = (User) adminUserTable.getModel().getValueAt(selectedRow, 3);
		
		String curValue = "";
		String oldValue = "";
		
		//Id
		curValue = (String) adminUserTable.getValueAt(selectedRow, 0);
		oldValue = userToUpdate.getId();
		if(!curValue.equals(oldValue)) {
			userToUpdate.setId(curValue);
		}
		
		//Name
		curValue = (String) adminUserTable.getValueAt(selectedRow, 1);
		oldValue = userToUpdate.getName();
		if(!curValue.equals(oldValue)) {
			userToUpdate.setName(curValue);
		}
		
		//Rank
		curValue = (String) adminUserTable.getValueAt(selectedRow, 2);
		oldValue = userToUpdate.getRank();
		if(!curValue.equals(oldValue)) {
			userToUpdate.setRank(curValue);
		}
		
		controller.saveModel(saveFilesPath + saveName);
	}
	
	
	/**
	 * Called by ViewGUI when an admin updates a template from the adminTemplateTable. Creates a
	 * confirmation string that will be used in a dialogue to confirm if the admin wants to make the
	 * changes to the template.
	 * 
	 * @param adminTemplateTable The table the admin edited to make updates to a template, should be 
	 * adminTemplateTable
	 * 
	 * @param selectedRow The index of the selected row of the table, is the row that has the user
	 * that will be updated
	 * 
	 * @return A string detailing all the proposed changes made to the template
	 */
	public static String createUpdateTemplateString(JTable adminTemplateTable, int selectedRow) {
		String msg = "Value Type: Previous Value -> New Value";
		PartTemplate oldTemplate = (PartTemplate) adminTemplateTable.getModel().getValueAt(selectedRow, 3);
		boolean anyUpdate = false;
		String curValue = "";
		String oldValue = "";
		
		//Part Number
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 0);
		oldValue = oldTemplate.getPartNumber();
		if(!curValue.equals(oldValue)) {
			msg += "\nPart Number: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Part Name
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 1);
		oldValue = oldTemplate.getPartName();
		if(!curValue.equals(oldValue)) {
			msg += "\nPart Name: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		//Quantity
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 2);
		oldValue = oldTemplate.getQuantity();
		if(!curValue.equals(oldValue)) {
			msg += "\nQuantity: " + oldValue + " -> " + curValue;
			anyUpdate = true;
		}
		
		
		// If nothing was updated, return an empty string
		if(!anyUpdate) {
			return "";
		}
		
		return msg;
	}
	
	public static String createDeleteTemplateString(JTable adminTemplateTable, int selectedRow) {
		PartTemplate oldTemplate = (PartTemplate) adminTemplateTable.getModel().getValueAt(selectedRow, 3);
		
		return oldTemplate.toString();
	}
	
	public static void updateTemplateFromAdminTable(JTable adminTemplateTable, int selectedRow) {
		if(!inAdminMode()) {
			return;
		}
		PartTemplate templateToUpdate = (PartTemplate) adminTemplateTable.getModel().getValueAt(selectedRow, 3);
		controller.removeTemplate(templateToUpdate);
		
		String curValue = "";
		String oldValue = "";
		
		//Part Number
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 0);
		oldValue = templateToUpdate.getPartNumber();
		if(!curValue.equals(oldValue)) {
			templateToUpdate.setPartNumber(curValue);
		}
		
		//Part Name
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 1);
		oldValue = templateToUpdate.getPartName();
		if(!curValue.equals(oldValue)) {
			templateToUpdate.setPartName(curValue);
		}
		
		//Quantity
		curValue = (String) adminTemplateTable.getValueAt(selectedRow, 2);
		oldValue = templateToUpdate.getQuantity();
		if(!curValue.equals(oldValue)) {
			templateToUpdate.setQuantity(curValue);
		}
		controller.addTemplate(templateToUpdate);
		
		controller.saveModel(saveFilesPath + saveName);
	}
	
	public static void deleteTemplateFromAdminTable(JTable adminTemplateTable, int selectedRow) {
		if(!inAdminMode()) {
			return;
		}
		PartTemplate templateToDelete = (PartTemplate) adminTemplateTable.getModel().getValueAt(selectedRow, 3);
		controller.removeTemplate(templateToDelete);
		
		controller.saveModel(saveFilesPath + saveName);
	}
	
	public static void setUpAdminTemplateTable(JTable table) {
		DefaultTableModel adminTemplateModel = (DefaultTableModel) table.getModel();
		adminTemplateModel.setRowCount(0);
		
		PartTemplate[] templateArray = controller.getTemplatesArray();
		int numTemplates = templateArray.length;
		
		//"Man Id", "Name", "Rank", User Object (hidden)
		for(int i = 0; i < numTemplates; i++) {
			adminTemplateModel.addRow(new Object[] {templateArray[i].getPartNumber(),
					templateArray[i].getPartName(), templateArray[i].getQuantity(), templateArray[i]});
		}
		
	}
	
	/**
	 * This method will be called when an admin creates a new user in the admin panel.
	 * If all the parameters are valid for creating a new user, a new user will be created
	 * and added to the model and the method will return true. If a parameter is not valid
	 * or if a user with the same id exists, a new user will not be created and the method
	 * will return false.
	 * 
	 * @param name: The name of the new user
	 * @param id: The man# of the new user
	 * @param rank: The rank of the new user
	 * @return true if a new user was created, false if a new user was not created
	 */
	public static boolean adminAddUserToModel(String name, String id, String rank) {
		if(name.length() == 0) {
			return false;
		}
		
		if(id.length() == 0) {
			return false;
		}
		
		if(rank.length() == 0) {
			return false;
		}
		
		if(controller.profileExists(id)) {
			return false;
		}
		
		controller.addUser(id, name, rank);
		controller.saveModel(saveFilesPath + saveName);
		
		return true;
	}
	
	
	/*
	 * The following methods deal with the variables stored in this class
	 */
	
	/**
	 * Called when a user logs out. Clears the current id, aircraft id, and job number fields.
	 */
	public static void newLogin() {
		if(currentId != null) {
			//RECORD LOGOUT OPERATION
			recordLogout(currentId);	
		} else {
			//RECORD SOFTWARE START
			recordSoftwareStart();
		}
		currentId = "";
		currentAircraftId = "";
		currentJobNumber = "";
	}
	
	/**
	 * 
	 * @return The last used aircraft tail number this session
	 */
	public static String getCurAircraft() {
		return currentAircraftId;
	}
	
	/**
	 * 
	 * @return The last used job number this session
	 */
	public static String getCurJob() {
		return currentJobNumber;
	}
	
	/**
	 * 
	 * @return true if the software is in admin mode, false if it is not
	 */
	public static boolean inAdminMode() {
		return adminMode;
	}
	
	/*
	 * The following methods record the operations conducted to the operations log file.
	 */
	
	/**
	 * Writes to the operations log that the software started
	 */
	public static void recordSoftwareStart() {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "\n------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "SOFTWARE STARTED";
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}
	
	/**
	 * Writes to the operations log that a user logged in
	 */
	public static void recordLogin(String manId) {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "\n------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "LOGIN OPERATION\nCURRENT USER SET TO ";
		operationString += controller.getProfile(manId).getName();
		operationString += " (";
		operationString += manId;
		operationString += ")";
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}
	
	/**
	 * Writes to the operations log that a user logged out
	 */
	public static void recordLogout(String manId) {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "LOGOUT OPERATION\n";
		operationString += controller.getProfile(manId).getName();
		operationString += " (";
		operationString += manId;
		operationString += ") HAS LOGGED OUT";
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}
	
	/**
	 * Writes to the operations log that a new user profile was created
	 */
	public static void recordProfileCreation(String manId) {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "\n------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "PROFILE CREATION OPERATION\nPROFILE CREATED FOR ";
		operationString += controller.getProfile(manId).getName();
		operationString += " (";
		operationString += manId;
		operationString += ") ";
		operationString += controller.getProfile(manId).getRank();
		operationString += "\nCURRENT USER SET TO ";
		operationString += controller.getProfile(manId).getName();
		operationString += " (";
		operationString += manId;
		operationString += ")";
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}
	
	/**
	 * Writes to the operations log that a part was deposited
	 */
	public static void recordDepositOperation(String partSerial) {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "DEPOSIT PART OPERATION\n";
		operationString += "CURRENT USER: ";
		operationString += controller.getProfile(currentId).getName();
		operationString += " (";
		operationString += currentId;
		operationString += ")\n";
		operationString += "SERIAL NUMBER OF PART DEPOSITED: ";
		operationString += partSerial;
		
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}

	/**
	 * Writes to the operations log that a part was withdrawn
	 */
	public static void recordWithdrawOperation(String partSerial) {
		//Create the string that will be written to the operation history file
		String operationString = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		operationString += "------------------------------------------------------------\n";
		operationString += dtf.format(now);
		operationString += "\n";
		
		operationString += "WITHDRAW PART OPERATION\n";
		operationString += "CURRENT USER: ";
		operationString += controller.getProfile(currentId).getName();
		operationString += " (";
		operationString += currentId;
		operationString += ")\n";
		operationString += "SERIAL NUMBER OF PART WITHDRAWN: ";
		operationString += partSerial;
		
		
		//Write the string to the operation history file
		controller.writeLineToLog(saveFilesPath + operationsLogFileName, operationString);
	}
	
	/**
	 * Saves a backup of the model state, the log, and operation history to the backupSaveLocation
	 */
	private static void saveBackupFiles() {
		String modelSavePath = backupSaveLocation + "\\model-saves\\";
		String logSavePath = backupSaveLocation + "\\log\\";
		String operationHistorySavePath = backupSaveLocation + "\\operation-history\\";
		try {
			// If the directory does not exist, make it
			Files.createDirectories(Paths.get(modelSavePath));
			Files.createDirectories(Paths.get(logSavePath));
			Files.createDirectories(Paths.get(operationHistorySavePath));
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
		LocalDateTime now = LocalDateTime.now(); 
		
		String modelSaveName = modelSavePath + dtf.format(now) + "_" + saveName;
		String logSaveName = logSavePath + dtf.format(now) + "_" + logFileName;
		String operationsSaveName = operationHistorySavePath + dtf.format(now) + "_" + operationsLogFileName;
		
		//Save a backup of the model
		controller.saveModel(modelSaveName);	
		
		//Save a backup of the log
		controller.copyFile(saveFilesPath + logFileName, logSaveName);
		
		//Save a backup of the operation history
		controller.copyFile(saveFilesPath + operationsLogFileName, operationsSaveName);
	}
}
