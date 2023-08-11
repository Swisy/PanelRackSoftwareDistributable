import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import java.awt.Font;
//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ViewGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JLayeredPane layeredPane;
	//loginPane elements
	private JPanel loginPane;
	private JTextField manIdInput;
	private JTextField nameInput;
	private JTextField rankInput;
	private JLabel rankLabel;
	private JLabel nameLabel;
	private JLabel manIdLabel;
	private JButton manIdButton;
	private JButton createProfileButton;
	//selectionPane elements
	private JPanel selectionPane;
	private JLabel selectionLabel;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton logoutButton;
	private JButton viewLogButton;
	//depositTabPane elements
	private JTabbedPane depositTabPane;
	//tab1
	private JPanel depositTab1;
	private JTextField jobNumberInput;
	private JTextField aircraftIdInput;
	private JButton tab1CancelButton;
	//tab2
	private JPanel depositTab2;
	private JPanel templateTableParentPane;
	private JLabel templateSearchLabel;
	private TableRowSorter<TableModel> templateRowSorter;
	private JTextField templateSearchInput;
	private JTable templateSelectionTable;
	private JButton tab2CancelButton;
	//tab3
	private JPanel depositTab3;
	private JTextField partSerialNumberInput;
	private JTextField partNameInput;
	private JLabel partNumberLabel;
	private JTextField partNumberInput;
	private JLabel partQuantityLabel;
	private JTextField partQuantityInput;
	private JCheckBox updateTemplateCheckBox;
	private JButton depositTab3NextButton;
	//tab4
	private JPanel depositTab4;
	private JTextArea partRemovalReasonInput;
	private JCheckBox isServiceableCheckBox;
	private JButton depositTab4SubmitButton;
	//withdrawPane elements
	private JPanel withdrawPane;
	private JTable withdrawSelectionTable;
	private JButton withdrawPartButton;
	private TableRowSorter<TableModel> withdrawRowSorter;
	private JLabel withdrawSearchLabel;
	private JTextField withdrawSearchInput;
	//logHistoryPane elements
	private JPanel logHistoryPane;
	private JTable logTable;
	private TableRowSorter<TableModel> logRowSorter;
	private JLabel logSearchLabel;
	private JTextField logSearchInput;
	//adminSelectionPane elements
	private JPanel adminSelectionPane;
	private JLayeredPane adminLayeredPane;
	private JPanel adminPartsPane;
	private JTable adminPartTable;
	private JButton adminPartCancelButton;
	private JPanel adminUsersPane;
	private JTable adminUserTable;
	private JButton adminUserCancelButton;
	private JPanel adminTemplatePane;
	private JTable adminTemplateTable;
	private JButton adminTemplateCancelButton;

	/**
	 * Launch the application.
	 */
	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewGUI frame = new ViewGUI();
					// Make it fullscreen
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setUndecorated(true);
					//GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
					//GraphicsDevice device = graphics.getDefaultScreenDevice();
					//device.setFullScreenWindow(frame);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewGUI() {
		// Main pane setup
		//ViewGUI thisGui = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 1024);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1262, 977);
		mainPane.add(layeredPane);
		
		// Admin Pane Setup
		createAdminPane();
		
		// Log History Pane Setup
		createLogHistoryPane();
		
		// Withdraw Pane Setup
		createWithdrawPane();
		
		
		// Deposit Pane Setup
		createDepositPane();
		
		
		// Selection Pane Setup
		createSelectionPane();
		
		// Login Pane Setup
		
		createLoginPane();
		
		// TODO remove this after testing
		switchToLoginPane();
		
		if(PanelView.inAdminMode()) {
			switchToAdminSelectionPanel();
			getContentPane().setBackground(Color.GRAY);
		}
		//switchToAdminEditInventory();
		//switchToAdminEditUsers();
	}
	
	private void createDepositPane() {
		ViewGUI thisGui = this;
		depositTabPane = new JTabbedPane(JTabbedPane.TOP);
		depositTabPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(depositTabPane);
		
		depositTab1 = new JPanel();
		depositTabPane.addTab("Job# and A/C Tail#", null, depositTab1, null);
		depositTab1.setLayout(null);
		
		depositTabPane.setSelectedIndex(0);
		
		JLabel jobNumberLabel = new JLabel("Job Number:");
		jobNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		jobNumberLabel.setBounds(10, 11, 214, 44);
		depositTab1.add(jobNumberLabel);
		
		JLabel aircraftIdLabel = new JLabel("A/C Tail#:");
		aircraftIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		aircraftIdLabel.setBounds(10, 72, 214, 44);
		depositTab1.add(aircraftIdLabel);
		
		jobNumberInput = new JTextField();
		jobNumberInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		jobNumberInput.setBounds(234, 11, 351, 44);
		depositTab1.add(jobNumberInput);
		jobNumberInput.setColumns(10);
		
		aircraftIdInput = new JTextField();
		aircraftIdInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		aircraftIdInput.setBounds(234, 72, 351, 44);
		depositTab1.add(aircraftIdInput);
		aircraftIdInput.setColumns(10);
		
		JButton depositTab1NextButton = new JButton("Next");
		depositTab1NextButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		depositTab1NextButton.setBounds(1102, 894, 145, 44);
		depositTab1NextButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				depositTabPane.setSelectedIndex(1);
			} 
		} );
		depositTab1.add(depositTab1NextButton);
		
		tab1CancelButton = new JButton("Cancel");
		tab1CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		tab1CancelButton.setBounds(10, 894, 182, 44);
		tab1CancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		depositTab1.add(tab1CancelButton);
		
		depositTab2 = new JPanel();
		depositTabPane.addTab("Template Selection", null, depositTab2, null);
		depositTab2.setLayout(null);
		
		JButton depositTab2NextButton = new JButton("Next");
		depositTab2NextButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		depositTab2NextButton.setBounds(1102, 894, 145, 44);
		depositTab2NextButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				depositTabPane.setSelectedIndex(2);
			} 
		} );
		depositTab2.add(depositTab2NextButton);
		
		templateTableParentPane = new JPanel();
		templateTableParentPane.setBounds(10, 75, 1237, 774);
		depositTab2.add(templateTableParentPane);
		templateTableParentPane.setLayout(null);
		
		JScrollPane templateSelectionPane = new JScrollPane();
		templateSelectionPane.setBounds(0, 25, 1237, 774);
		templateTableParentPane.add(templateSelectionPane);
		
		templateSelectionTable = new JTable();
		templateSelectionTable.setRowHeight(50);
		templateSelectionTable.setFont(new Font("Tahoma", Font.PLAIN, 25));
		templateSelectionTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Part Number", "Part Name"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		templateSelectionTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		templateSelectionTable.getColumnModel().getColumn(0).setMaxWidth(600);
		templateSelectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		templateSelectionTable.setFillsViewportHeight(true);
		templateSelectionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  if(!templateSelectionTable.getSelectionModel().getValueIsAdjusting()) {
		    		  int selectedRow = templateSelectionTable.getSelectedRow();
		    		  if(selectedRow > -1) {
		    			  String partNumber = (String) templateSelectionTable.getValueAt(selectedRow, 0);
		    			  PanelView.templateSelectedEvent(thisGui, partNumber);    
		    		  }
		    	  }
		        }

		      });
		
		templateRowSorter = new TableRowSorter<>(templateSelectionTable.getModel());
		templateSelectionTable.setRowSorter(templateRowSorter);
		templateSelectionPane.setViewportView(templateSelectionTable);
		
		templateSearchLabel = new JLabel("Search:");
		templateSearchLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		templateSearchLabel.setBounds(0, 0, 80, 25);
		templateTableParentPane.add(templateSearchLabel);
		
		templateSearchInput = new JTextField();
		templateSearchInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		templateSearchInput.setBounds(80, 0, 1157, 25);
		templateSearchInput.getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
                String text = templateSearchInput.getText();

                if (text.trim().length() == 0) {
                    templateRowSorter.setRowFilter(null);
                } else {
                    templateRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = templateSearchInput.getText();

                if (text.trim().length() == 0) {
                    templateRowSorter.setRowFilter(null);
                } else {
                    templateRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
		});
		templateTableParentPane.add(templateSearchInput);
		templateSearchInput.setColumns(10);
		
		JLabel templateSelectionLabel = new JLabel("Please Select A Part Template, Or Manually Enter The Part Information In The Next Tab");
		templateSelectionLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		templateSelectionLabel.setBounds(10, 11, 1237, 44);
		depositTab2.add(templateSelectionLabel);
		
		tab2CancelButton = new JButton("Cancel");
		tab2CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		tab2CancelButton.setBounds(10, 894, 182, 44);
		tab2CancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		depositTab2.add(tab2CancelButton);
		
		depositTab3 = new JPanel();
		depositTabPane.addTab("Part Information", null, depositTab3, null);
		depositTab3.setLayout(null);
		
		depositTab3NextButton = new JButton("Next");
		depositTab3NextButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		depositTab3NextButton.setBounds(1102, 894, 145, 44);
		depositTab3NextButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				depositTabPane.setSelectedIndex(3);
			} 
		} );
		depositTab3.add(depositTab3NextButton);
		
		JLabel partSerialNumberLabel = new JLabel("Part Serial Number:");
		partSerialNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partSerialNumberLabel.setBounds(10, 11, 303, 44);
		depositTab3.add(partSerialNumberLabel);
		
		partSerialNumberInput = new JTextField();
		partSerialNumberInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partSerialNumberInput.setBounds(323, 11, 296, 44);
		depositTab3.add(partSerialNumberInput);
		partSerialNumberInput.setColumns(10);
		
		JLabel partNameLabel = new JLabel("Part Name:");
		partNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partNameLabel.setBounds(20, 295, 178, 44);
		depositTab3.add(partNameLabel);
		
		JTextArea depositVerificationTextArea = new JTextArea();
		depositVerificationTextArea.setForeground(new Color(178, 34, 34));
		depositVerificationTextArea.setText("Please Verify The Below Information Is\r\nAccurate And Make Any Needed Corrections");
		depositVerificationTextArea.setFont(new Font("Tahoma", Font.PLAIN, 39));
		depositVerificationTextArea.setBackground(UIManager.getColor("Label.background"));
		depositVerificationTextArea.setEditable(false);
		depositVerificationTextArea.setBounds(10, 166, 779, 116);
		depositTab3.add(depositVerificationTextArea);
		
		partNameInput = new JTextField();
		partNameInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partNameInput.setBounds(248, 295, 388, 44);
		depositTab3.add(partNameInput);
		partNameInput.setColumns(10);
		
		partNumberLabel = new JLabel("Part Number:");
		partNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partNumberLabel.setBounds(20, 387, 207, 44);
		depositTab3.add(partNumberLabel);
		
		partNumberInput = new JTextField();
		partNumberInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partNumberInput.setBounds(248, 387, 388, 44);
		depositTab3.add(partNumberInput);
		partNumberInput.setColumns(10);
		
		partQuantityLabel = new JLabel("Quantity:");
		partQuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partQuantityLabel.setBounds(20, 479, 207, 44);
		depositTab3.add(partQuantityLabel);
		
		partQuantityInput = new JTextField();
		partQuantityInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partQuantityInput.setBounds(248, 479, 107, 44);
		depositTab3.add(partQuantityInput);
		partQuantityInput.setColumns(10);
		
		updateTemplateCheckBox = new JCheckBox("Update Template");
		updateTemplateCheckBox.setBounds(529, 558, 178, 38);
		depositTab3.add(updateTemplateCheckBox);
		
		JButton tab3CancelButton = new JButton("Cancel");
		tab3CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		tab3CancelButton.setBounds(10, 894, 182, 44);
		tab3CancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		depositTab3.add(tab3CancelButton);
		
		depositTab4 = new JPanel();
		depositTabPane.addTab("Confirmation and Submit", null, depositTab4, null);
		depositTab4.setLayout(null);
		
		depositTab4SubmitButton = new JButton("Submit");
		depositTab4SubmitButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		depositTab4SubmitButton.setBounds(1102, 894, 145, 44);
		depositTab4SubmitButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				// If all input fields are valid
				if(checkDepositInputs()) {
					// Add the part to the model
					PanelView.depositPart(thisGui);
				}
			} 
		} );
		depositTab4.add(depositTab4SubmitButton);
		
		JButton tab4CancelButton = new JButton("Cancel");
		tab4CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		tab4CancelButton.setBounds(10, 894, 182, 44);
		tab4CancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		depositTab4.add(tab4CancelButton);
		
		JLabel partRemovalReasonLabel = new JLabel("Please enter the reason for the part's removal:");
		partRemovalReasonLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		partRemovalReasonLabel.setBounds(10, 11, 721, 44);
		depositTab4.add(partRemovalReasonLabel);
		
		partRemovalReasonInput = new JTextArea();
		partRemovalReasonInput.setWrapStyleWord(true);
		partRemovalReasonInput.setLineWrap(true);
		partRemovalReasonInput.setRows(8);
		partRemovalReasonInput.setFont(new Font("Monospaced", Font.PLAIN, 20));
		partRemovalReasonInput.setBounds(20, 66, 831, 332);
		depositTab4.add(partRemovalReasonInput);
		
		JLabel isServiceableLabel = new JLabel("Is the part serviceable?");
		isServiceableLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		isServiceableLabel.setBounds(10, 455, 386, 44);
		depositTab4.add(isServiceableLabel);
		
		isServiceableCheckBox = new JCheckBox("Serviceable");
		isServiceableCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		isServiceableCheckBox.setBounds(20, 506, 134, 40);
		depositTab4.add(isServiceableCheckBox);
	}
	
	private void createWithdrawPane() {
		ViewGUI thisGui = this;
		withdrawPane = new JPanel();
		withdrawPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(withdrawPane);
		withdrawPane.setLayout(null);
		
		JScrollPane withdrawScrollPane = new JScrollPane();
		withdrawScrollPane.setBounds(12, 60, 1220, 783);
		withdrawPane.add(withdrawScrollPane);
		
		withdrawSelectionTable = new JTable();
		withdrawSelectionTable.setRowHeight(50);
		withdrawSelectionTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Serial Number", "A/C Tail#", "Part Number", "Part Name"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		withdrawSelectionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		withdrawSelectionTable.getColumnModel().getColumn(0).setMinWidth(100);
		withdrawSelectionTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		withdrawSelectionTable.getColumnModel().getColumn(1).setMinWidth(120);
		withdrawSelectionTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		withdrawSelectionTable.getColumnModel().getColumn(2).setMinWidth(100);
		withdrawSelectionTable.getColumnModel().getColumn(3).setPreferredWidth(126);
		withdrawSelectionTable.getColumnModel().getColumn(3).setMinWidth(121);
		withdrawSelectionTable.setFont(new Font("Tahoma", Font.PLAIN, 25));
		withdrawSelectionTable.setFillsViewportHeight(true);
		withdrawScrollPane.setViewportView(withdrawSelectionTable);
		
		withdrawRowSorter = new TableRowSorter<>(withdrawSelectionTable.getModel());
		withdrawSelectionTable.setRowSorter(withdrawRowSorter);
		withdrawSearchInput = new JTextField();
		withdrawSearchInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		withdrawSearchInput.setBounds(92, 35, 1140, 25);
		withdrawPane.add(withdrawSearchInput);
		withdrawSearchInput.setColumns(10);
		withdrawSearchInput.getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
                String text = withdrawSearchInput.getText();

                if (text.trim().length() == 0) {
                	withdrawRowSorter.setRowFilter(null);
                } else {
                	withdrawRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = withdrawSearchInput.getText();

                if (text.trim().length() == 0) {
                	withdrawRowSorter.setRowFilter(null);
                } else {
                	withdrawRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
		});
		
		
		withdrawPartButton = new JButton("Withdraw Part");
		withdrawPartButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		withdrawPartButton.setBounds(916, 883, 316, 53);
		withdrawPartButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int selectedRow = withdrawSelectionTable.getSelectedRow();
				if(selectedRow > -1) {
					String serialNumber = (String) withdrawSelectionTable.getValueAt(selectedRow, 0);
					PanelView.withdrawPart(thisGui, serialNumber);
				}
			} 
		} );
		withdrawPane.add(withdrawPartButton);
		
		JButton withdrawCancelButton = new JButton("Cancel");
		withdrawCancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		withdrawCancelButton.setBounds(33, 883, 193, 53);
		withdrawCancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		withdrawPane.add(withdrawCancelButton);
		
		withdrawSearchLabel = new JLabel("Search:");
		withdrawSearchLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		withdrawSearchLabel.setBounds(12, 35, 80, 25);
		withdrawPane.add(withdrawSearchLabel);
	}
	
	private void createLoginPane() {
		ViewGUI thisGui = this;
		// Login Pane Setup
		loginPane = new JPanel();
		loginPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(loginPane);
		loginPane.setLayout(null);
		
		manIdLabel = new JLabel("MAN#:");
		manIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		manIdLabel.setBounds(12, 13, 108, 44);
		loginPane.add(manIdLabel);
		
		manIdInput = new JTextField();
		manIdInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		manIdInput.setBounds(132, 13, 300, 44);
		loginPane.add(manIdInput);
		manIdInput.setColumns(10);
		
		manIdButton = new JButton("Submit");
		manIdButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		manIdButton.setBounds(470, 13, 202, 44);
		manIdButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if(manIdInput.getText().length() > 0) {
					PanelView.manSubmitAction(thisGui);
				} else {
					JOptionPane.showMessageDialog(loginPane, "No Man # Entered", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
				}
			} 
		} );
		loginPane.add(manIdButton);
		
		nameLabel = new JLabel("Full Name:");
		nameLabel.setEnabled(false);
		nameLabel.setVisible(false);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		nameLabel.setBounds(12, 80, 165, 44);
		loginPane.add(nameLabel);
		
		nameInput = new JTextField();
		nameInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		nameInput.setEnabled(false);
		nameInput.setVisible(false);
		nameInput.setBounds(189, 80, 400, 44);
		loginPane.add(nameInput);
		nameInput.setColumns(10);
		
		rankLabel = new JLabel("Rank:");
		rankLabel.setEnabled(false);
		rankLabel.setVisible(false);
		rankLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		rankLabel.setBounds(12, 147, 89, 44);
		loginPane.add(rankLabel);
		
		rankInput = new JTextField();
		rankInput.setFont(new Font("Tahoma", Font.PLAIN, 35));
		rankInput.setEnabled(false);
		rankInput.setVisible(false);
		rankInput.setBounds(113, 147, 319, 44);
		loginPane.add(rankInput);
		rankInput.setColumns(10);
		
		createProfileButton = new JButton("Create Profile");
		createProfileButton.setEnabled(false);
		createProfileButton.setVisible(false);
		createProfileButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		createProfileButton.setBounds(12, 230, 262, 44);
		createProfileButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				if((rankInput.getText().length() > 0) && (nameInput.getText().length() > 0)) {
					PanelView.profileSubmitAction(thisGui);
				} else {
					JOptionPane.showMessageDialog(loginPane, "Input Fields Left Blank", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
				}
			} 
		} );
		loginPane.add(createProfileButton);
	}
	
	private void createSelectionPane() {
		selectionPane = new JPanel();
		selectionPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(selectionPane);
		selectionPane.setLayout(null);
		
		selectionLabel = new JLabel("Would You Like To Withdraw Or Deposit A Part?");
		selectionLabel.setFont(new Font("Tahoma", Font.PLAIN, 45));
		selectionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		selectionLabel.setBounds(12, 13, 964, 43);
		selectionPane.add(selectionLabel);
		
		depositButton = new JButton("Deposit");
		depositButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		depositButton.setBounds(37, 101, 180, 60);
		depositButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToDepositPane();
			} 
		} );
		selectionPane.add(depositButton);
		
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		withdrawButton.setBounds(261, 101, 233, 60);
		withdrawButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToWithdrawPane();
			} 
		} );
		selectionPane.add(withdrawButton);
		
		logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		logoutButton.setBounds(950, 850, 233, 60);
		logoutButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToLoginPane();
			} 
		} );
		selectionPane.add(logoutButton);
		
		viewLogButton = new JButton("View Log");
		viewLogButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		viewLogButton.setBounds(37, 850, 215, 60);
		viewLogButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToLogHistoryPane();
			} 
		} );
		selectionPane.add(viewLogButton);
		
		selectionPane.setVisible(false);
	}
	
	private void createLogHistoryPane() {
		logHistoryPane = new JPanel();
		logHistoryPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(logHistoryPane);
		logHistoryPane.setLayout(null);
		
		JScrollPane logTableScrollPane = new JScrollPane();
		logTableScrollPane.setBounds(12, 60, 1238, 783);
		logHistoryPane.add(logTableScrollPane);
		
		logTable = new JTable();
		logTable.setFont(new Font("Arial", Font.PLAIN, 15));
		logTable.setRowHeight(20);
		logTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ACFT", "JCN", "PART #", "PART NAME", "SERIAL #", "DATE LOGGED IN",
				"NAME & EMPLOYEE #", "DATE LOGGED OUT", "NAME & EMPLOYEE #"
			}
		){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		logTable.getColumnModel().getColumn(0).setPreferredWidth(69);
		logTable.getColumnModel().getColumn(1).setPreferredWidth(137);
		logTable.getColumnModel().getColumn(2).setPreferredWidth(127);
		logTable.getColumnModel().getColumn(3).setPreferredWidth(127);
		logTable.getColumnModel().getColumn(4).setPreferredWidth(137);
		logTable.getColumnModel().getColumn(5).setPreferredWidth(167);
		logTable.getColumnModel().getColumn(6).setPreferredWidth(185);
		logTable.getColumnModel().getColumn(7).setPreferredWidth(167);
		logTable.getColumnModel().getColumn(8).setPreferredWidth(185);
		logTable.setFillsViewportHeight(true);
		logTableScrollPane.setViewportView(logTable);
		
		logSearchLabel = new JLabel("Search:");
		logSearchLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		logSearchLabel.setBounds(12, 35, 80, 25);
		logHistoryPane.add(logSearchLabel);
		logRowSorter = new TableRowSorter<>(logTable.getModel());
		logTable.setRowSorter(logRowSorter);
		logSearchInput = new JTextField();
		logSearchInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		logSearchInput.setBounds(92, 35, 1158, 25);
		logHistoryPane.add(logSearchInput);
		logSearchInput.setColumns(10);
		logSearchInput.getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
                String text = logSearchInput.getText();

                if (text.trim().length() == 0) {
                	logRowSorter.setRowFilter(null);
                } else {
                	logRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = logSearchInput.getText();

                if (text.trim().length() == 0) {
                	logRowSorter.setRowFilter(null);
                } else {
                	logRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
		});
		
		JButton logHistoryReturnButton = new JButton("Return");
		logHistoryReturnButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		logHistoryReturnButton.setBounds(1044, 903, 184, 44);
		logHistoryReturnButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToProcessSelection();
			} 
		} );
		logHistoryPane.add(logHistoryReturnButton);
	}
	
	private void createAdminPane() {
		adminLayeredPane = new JLayeredPane();
		adminLayeredPane.setBounds(0, 0, 1262, 977);
		layeredPane.add(adminLayeredPane);
		
		createAdminUserListPanel();
		
		createAdminTemplateListPanel();
		
		createAdminPartInventoryPanel();
		
		
		adminSelectionPane = new JPanel();
		adminSelectionPane.setBackground(Color.GRAY);
		adminSelectionPane.setBounds(0, 0, 1262, 977);
		adminLayeredPane.add(adminSelectionPane);
		adminSelectionPane.setLayout(null);
		
		JLabel adminPaneWarningLabel = new JLabel("ADMIN MENU. DO NOT LEAVE OPEN");
		adminPaneWarningLabel.setForeground(new Color(128, 0, 0));
		adminPaneWarningLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminPaneWarningLabel.setBounds(10, 11, 577, 44);
		adminSelectionPane.add(adminPaneWarningLabel);
		
		JButton adminEditInventoryButton = new JButton("Edit Parts");
		adminEditInventoryButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminEditInventoryButton.setForeground(new Color(128, 0, 0));
		adminEditInventoryButton.setBounds(50, 114, 175, 33);
		adminEditInventoryButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminEditInventory();
			} 
		} );
		adminSelectionPane.add(adminEditInventoryButton);
		
		
		JButton adminEditUsersButton = new JButton("Edit Users");
		adminEditUsersButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminEditUsersButton.setForeground(new Color(128, 0, 0));
		adminEditUsersButton.setBounds(50, 157, 175, 33);
		adminEditUsersButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminEditUsers();
			} 
		} );
		adminSelectionPane.add(adminEditUsersButton);
		
		JButton adminEditTemplateButton = new JButton("Edit Templates");
		adminEditTemplateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminEditTemplateButton.setForeground(new Color(128, 0, 0));
		adminEditTemplateButton.setBounds(50, 200, 175, 33);
		adminEditTemplateButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminEditTemplates();
			} 
		} );
		adminSelectionPane.add(adminEditTemplateButton);
		
		/*
		JButton adminOverrideLockButton = new JButton("New button");
		adminOverrideLockButton.setBounds(50, 181, 89, 23);
		adminSelectionPane.add(adminOverrideLockButton);
		
		JButton adminBackupDataButton = new JButton("New button");
		adminBackupDataButton.setBounds(50, 364, 89, 23);
		adminSelectionPane.add(adminBackupDataButton);
		
		JButton adminRestoreBackupButton = new JButton("New button");
		adminRestoreBackupButton.setBounds(50, 470, 89, 23);
		adminSelectionPane.add(adminRestoreBackupButton);
		
		JButton adminClearDataButton = new JButton("New button");
		adminClearDataButton.setBounds(50, 536, 89, 23);
		adminSelectionPane.add(adminClearDataButton);
		*/
		
		JButton adminExitButton = new JButton("Exit");
		adminExitButton.setBounds(50, 293, 89, 23);
		adminExitButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminExitButton.setForeground(new Color(128, 0, 0));
		adminExitButton.setBounds(10, 894, 182, 44);
		adminExitButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);
			} 
		} );
		adminSelectionPane.add(adminExitButton);
		
	}
	
	private void createAdminTemplateListPanel() {
		adminTemplatePane = new JPanel();
		adminTemplatePane.setBackground(Color.GRAY);
		adminTemplatePane.setBounds(0, 0, 1262, 977);
		adminLayeredPane.add(adminTemplatePane);
		adminTemplatePane.setLayout(null);
		
		JScrollPane adminTemplateScrollPane = new JScrollPane();
		adminTemplateScrollPane.setBounds(12, 60, 850, 850);
		adminTemplatePane.add(adminTemplateScrollPane);
		
		adminTemplateTable = new JTable();
		adminTemplateTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
					"Part Number", "Part Name", "Quantity", "Object"
			}
		) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		adminTemplateTable.getColumnModel().getColumn(0).setResizable(false);
		adminTemplateTable.getColumnModel().getColumn(1).setResizable(false);
		adminTemplateTable.getColumnModel().getColumn(2).setResizable(false);
		adminTemplateTable.getColumnModel().getColumn(3).setResizable(false);
		adminTemplateScrollPane.setViewportView(adminTemplateTable);
		adminTemplateTable.setRowHeight(25);
		adminTemplateTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Remove the fourth column from the view, but allow data to be stored there.
		adminTemplateTable.removeColumn(adminTemplateTable.getColumnModel().getColumn(3));
		
		
		JLabel adminTemplateInstructionLabel = new JLabel("Update the values in a row and then press the \"Update\" button to save");
		adminTemplateInstructionLabel.setForeground(new Color(128, 0, 0));
		adminTemplateInstructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		adminTemplateInstructionLabel.setBounds(12, 11, 1220, 44);
		adminTemplatePane.add(adminTemplateInstructionLabel);
		
		JButton adminTemplateUpdateButton = new JButton("Update");
		adminTemplateUpdateButton.setForeground(new Color(128, 0, 0));
		adminTemplateUpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminTemplateUpdateButton.setBounds(673, 921, 189, 44);
		adminTemplateUpdateButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int selected = adminTemplateTable.getSelectedRow();
				if(selected == -1) {
					JOptionPane.showMessageDialog(adminTemplatePane, "You must select the row of the template you want to update", "No Selection", JOptionPane.ERROR_MESSAGE);
					return;
				}
				adminUpdateTemplatesAction(selected);
			} 
		} );
		adminTemplatePane.add(adminTemplateUpdateButton);
		
		adminTemplateCancelButton = new JButton("Cancel");
		adminTemplateCancelButton.setForeground(new Color(128, 0, 0));
		adminTemplateCancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminTemplateCancelButton.setBounds(12, 921, 189, 44);
		adminTemplateCancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminSelectionPanel();
			} 
		} );
		adminTemplatePane.add(adminTemplateCancelButton);
		
		JButton adminTemplateDeleteButton = new JButton("Delete Template");
		adminTemplateDeleteButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		adminTemplateDeleteButton.setForeground(new Color(128, 0, 0));
		adminTemplateDeleteButton.setBounds(1000, 921, 232, 44);
		adminTemplateDeleteButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int selected = adminTemplateTable.getSelectedRow();
				if(selected == -1) {
					JOptionPane.showMessageDialog(adminTemplatePane, "You must select the row of the template you want to delete", "No Selection", JOptionPane.ERROR_MESSAGE);
					return;
				}
				adminDeleteTemplateAction(selected);
			} 
		} );
		adminTemplatePane.add(adminTemplateDeleteButton);
		
	}
	
	private void createAdminUserListPanel() {
		adminUsersPane = new JPanel();
		adminUsersPane.setBackground(Color.GRAY);
		adminUsersPane.setBounds(0, 0, 1262, 977);
		adminLayeredPane.add(adminUsersPane);
		adminUsersPane.setLayout(null);
		
		JScrollPane adminUserScrollPane = new JScrollPane();
		adminUserScrollPane.setBounds(12, 60, 850, 850);
		adminUsersPane.add(adminUserScrollPane);
		
		adminUserTable = new JTable();
		adminUserTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
					"Man #", "Name", "Rank", "Object"
			}
		) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		adminUserTable.getColumnModel().getColumn(0).setResizable(false);
		adminUserTable.getColumnModel().getColumn(1).setResizable(false);
		adminUserTable.getColumnModel().getColumn(2).setResizable(false);
		adminUserTable.getColumnModel().getColumn(3).setResizable(false);
		adminUserScrollPane.setViewportView(adminUserTable);
		adminUserTable.setRowHeight(25);
		adminUserTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Remove the fourth column from the view, but allow data to be stored there.
		adminUserTable.removeColumn(adminUserTable.getColumnModel().getColumn(3));
		
		
		JLabel adminUserInstructionLabel = new JLabel("Update the values in a row and then press the \"Update\" button to save");
		adminUserInstructionLabel.setForeground(new Color(128, 0, 0));
		adminUserInstructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		adminUserInstructionLabel.setBounds(12, 11, 1220, 44);
		adminUsersPane.add(adminUserInstructionLabel);
		
		JButton adminUserUpdateButton = new JButton("Update");
		adminUserUpdateButton.setForeground(new Color(128, 0, 0));
		adminUserUpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminUserUpdateButton.setBounds(673, 921, 189, 44);
		adminUserUpdateButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int selected = adminUserTable.getSelectedRow();
				if(selected == -1) {
					JOptionPane.showMessageDialog(adminUsersPane, "You must select the row of the user you want to update", "No Selection", JOptionPane.ERROR_MESSAGE);
					return;
				}
				adminUpdateUsersAction(selected);
			} 
		} );
		adminUsersPane.add(adminUserUpdateButton);
		
		adminUserCancelButton = new JButton("Cancel");
		adminUserCancelButton.setForeground(new Color(128, 0, 0));
		adminUserCancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminUserCancelButton.setBounds(12, 921, 189, 44);
		adminUserCancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminSelectionPanel();
			} 
		} );
		adminUsersPane.add(adminUserCancelButton);
		
		JButton adminCreateUserButton = new JButton("Add New User");
		adminCreateUserButton.setForeground(new Color(128, 0, 0));
		adminCreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		adminCreateUserButton.setBounds(930, 165, 230, 44);
		adminCreateUserButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				adminCreateUserAction();
			} 
		} );
		adminUsersPane.add(adminCreateUserButton);
		
	}
	
	private void createAdminPartInventoryPanel() {
		adminPartsPane = new JPanel();
		adminPartsPane.setBackground(Color.GRAY);
		adminPartsPane.setBounds(0, 0, 1262, 977);
		adminLayeredPane.add(adminPartsPane);
		adminPartsPane.setLayout(null);
		
		JScrollPane adminPartScrollPane = new JScrollPane();
		adminPartScrollPane.setBounds(12, 60, 1220, 850);
		adminPartsPane.add(adminPartScrollPane);
		
		//TODO find the parts to update using the date
		adminPartTable = new JTable();
		adminPartTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
			},
			new String[] {
					"Serial Number", "Job Number", "A/C Tail#", "Part Number", "Part Name", "Quantity", "Servicable"
			}
		) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		adminPartTable.getColumnModel().getColumn(0).setResizable(false);
		adminPartTable.getColumnModel().getColumn(1).setResizable(false);
		adminPartTable.getColumnModel().getColumn(2).setResizable(false);
		adminPartTable.getColumnModel().getColumn(3).setResizable(false);
		adminPartTable.getColumnModel().getColumn(4).setResizable(false);
		adminPartTable.getColumnModel().getColumn(5).setResizable(false);
		adminPartTable.getColumnModel().getColumn(6).setResizable(false);
		adminPartTable.setRowHeight(25);
		adminPartTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		adminPartScrollPane.setViewportView(adminPartTable);
		
		JLabel adminPartInstructionLabel = new JLabel("Update the values in a row and then press the \"Update\" button to save");
		adminPartInstructionLabel.setForeground(new Color(128, 0, 0));
		adminPartInstructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		adminPartInstructionLabel.setBounds(12, 11, 1220, 44);
		adminPartsPane.add(adminPartInstructionLabel);
		
		JButton adminPartUpdateButton = new JButton("Update");
		adminPartUpdateButton.setForeground(new Color(128, 0, 0));
		adminPartUpdateButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminPartUpdateButton.setBounds(1043, 921, 189, 44);
		adminPartUpdateButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				int selected = adminPartTable.getSelectedRow();
				if(selected == -1) {
					JOptionPane.showMessageDialog(adminPartsPane, "You must select the row of the part you want to update", "No Selection", JOptionPane.ERROR_MESSAGE);
					return;
				}
				adminUpdatePartsAction(selected);
			} 
		} );
		adminPartsPane.add(adminPartUpdateButton);
		
		adminPartCancelButton = new JButton("Cancel");
		adminPartCancelButton.setForeground(new Color(128, 0, 0));
		adminPartCancelButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminPartCancelButton.setBounds(12, 921, 189, 44);
		adminPartCancelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				switchToAdminSelectionPanel();
			} 
		} );
		adminPartsPane.add(adminPartCancelButton);
	}
	
	public String getManInput() {
		return manIdInput.getText();
	}
	
	public String getNameInput() {
		return nameInput.getText();
	}
	
	public String getRankInput() {
		return rankInput.getText();
	}
	
	public void fillInfoFromTemplate(String partNumber, String quantity, String partName) {
		partNameInput.setText(partName);
		partNumberInput.setText(partNumber);
		partQuantityInput.setText(quantity);
	}
	
	public boolean updateTemplateCheck() {
		if(updateTemplateCheckBox.isSelected()) {
			return true;
		}
		return false;
	}
	
	
	private void adminCreateUserAction() {
		JTextField tempNameField = new JTextField(15);
		JTextField tempIdField = new JTextField(15);
		JTextField tempRankField = new JTextField(15);

		JPanel tempPane = new JPanel();
		tempPane.add(new JLabel("Name:"));
		tempPane.add(tempNameField);
		tempPane.add(Box.createHorizontalStrut(5)); // a spacer
		tempPane.add(new JLabel("Man#:"));
		tempPane.add(tempIdField);
		tempPane.add(Box.createHorizontalStrut(5)); // a spacer
		tempPane.add(new JLabel("Rank:"));
		tempPane.add(tempRankField);

		int result = JOptionPane.showConfirmDialog(adminUsersPane, tempPane, 
				"Enter Values For New User", JOptionPane.OK_CANCEL_OPTION);
		
		//While loop will continue to run until the cancel button on the dialog is pressed, or valid inputs are entered
		while(result == JOptionPane.OK_OPTION) {
			if(PanelView.adminAddUserToModel(tempNameField.getText(), tempIdField.getText(), tempRankField.getText())) {
				break;
			}
			JOptionPane.showMessageDialog(adminUsersPane, "ERROR\nInputs Left Blank Or User With Man# Already Exists", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
			result = JOptionPane.showConfirmDialog(null, tempPane, 
					"Enter Values For New User", JOptionPane.OK_CANCEL_OPTION);
		}
		switchToAdminEditUsers();
	}
	
	private void adminUpdatePartsAction(int selectedRow) {
		if(!PanelView.inAdminMode()) {
			JOptionPane.showMessageDialog(adminPartsPane, "ADMIN MODE NOT ENABLED\nACCESS DENIED", "ACCESS DENIED", JOptionPane.ERROR_MESSAGE);
			switchToLoginPane();
			return;
		}
		String msgString = "Are you sure you want to update the following part:\n";
		String updateString = PanelView.createUpdatePartString(adminPartTable, selectedRow);
		
		if(updateString.length() < 40) {
			JOptionPane.showMessageDialog(adminPartsPane, "No values in the selected row were updated", "No Updates", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		msgString += updateString;
		
		// yes = 0, no = 1
		int response = JOptionPane.showConfirmDialog(adminPartsPane, msgString, "Confirm Update", JOptionPane.YES_NO_OPTION);
		if(response == 1) {
			return;
		}
		
		PanelView.updatePartFromAdminTable(adminPartTable, selectedRow);
		
	}
	
	
	private void adminUpdateUsersAction(int selectedRow) {
		if(!PanelView.inAdminMode()) {
			JOptionPane.showMessageDialog(adminUsersPane, "ADMIN MODE NOT ENABLED\nACCESS DENIED", "ACCESS DENIED", JOptionPane.ERROR_MESSAGE);
			switchToLoginPane();
			return;
		}
		String msgString = "Are you sure you want to update the following user:\n";
		String updateString = PanelView.createUpdateUserString(adminUserTable, selectedRow);
		
		if(updateString.length() < 40) {
			JOptionPane.showMessageDialog(adminUsersPane, "No values in the selected row were updated", "No Updates", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		msgString += updateString;
		
		// yes = 0, no = 1
		int response = JOptionPane.showConfirmDialog(adminUsersPane, msgString, "Confirm Update", JOptionPane.YES_NO_OPTION);
		if(response == 1) {
			return;
		}
		
		PanelView.updateUserFromAdminTable(adminUserTable, selectedRow);
		
		switchToAdminEditUsers();
		
	}
	
	private void adminUpdateTemplatesAction(int selectedRow) {
		if(!PanelView.inAdminMode()) {
			JOptionPane.showMessageDialog(adminTemplatePane, "ADMIN MODE NOT ENABLED\nACCESS DENIED", "ACCESS DENIED", JOptionPane.ERROR_MESSAGE);
			switchToLoginPane();
			return;
		}
		String msgString = "Are you sure you want to update the following template:\n";
		String updateString = PanelView.createUpdateTemplateString(adminTemplateTable, selectedRow);
		
		if(updateString.length() < 40) {
			JOptionPane.showMessageDialog(adminTemplatePane, "No values in the selected row were updated", "No Updates", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		msgString += updateString;
		
		// yes = 0, no = 1
		int response = JOptionPane.showConfirmDialog(adminTemplatePane, msgString, "Confirm Update", JOptionPane.YES_NO_OPTION);
		if(response == 1) {
			return;
		}
		
		PanelView.updateTemplateFromAdminTable(adminTemplateTable, selectedRow);
		
		switchToAdminEditTemplates();
		
	}
	
	private void adminDeleteTemplateAction(int selectedRow) {
		if(!PanelView.inAdminMode()) {
			JOptionPane.showMessageDialog(adminTemplatePane, "ADMIN MODE NOT ENABLED\nACCESS DENIED", "ACCESS DENIED", JOptionPane.ERROR_MESSAGE);
			switchToLoginPane();
			return;
		}
		String msgString = "Are you sure you want to delete the following template:\n";
		String updateString = PanelView.createDeleteTemplateString(adminTemplateTable, selectedRow);
		
		
		msgString += updateString;
		
		// yes = 0, no = 1
		int response = JOptionPane.showConfirmDialog(adminTemplatePane, msgString, "Confirm Delete", JOptionPane.YES_NO_OPTION);
		if(response == 1) {
			return;
		}
		
		PanelView.deleteTemplateFromAdminTable(adminTemplateTable, selectedRow);
		
		switchToAdminEditTemplates();
		
	}
	
	public boolean checkDepositInputs() {
		//TODO add more checking for inputs
		boolean[] validInputs = {true, true, true, true, true, true, true};
		String msgString = "The Following Inputs Were Left Blank:";
		String msgString2 = "The Following Inputs Contain An Illegal Character(,):";
		boolean[] hasComma = {false, false, false, false, false, false, false};
		
		// Check Job Number Input
		if(jobNumberInput.getText().length() == 0) {
			validInputs[0] = false;
			msgString += "\nJob Number";
		}
		if(jobNumberInput.getText().contains(",")) {
			hasComma[0] = true;
			msgString2 += "\nJob Number";
		}
		
		// Check Aircraft Id Input
		if(aircraftIdInput.getText().length() == 0) {
			validInputs[1] = false;
			msgString += "\nA/C Tail#";
		}
		if(aircraftIdInput.getText().contains(",")) {
			hasComma[1] = true;
			msgString2 += "\nA/C Tail#";
		}
		
		// Check Part Serial Number Input
		if(partSerialNumberInput.getText().length() == 0) {
			validInputs[2] = false;
			msgString += "\nPart Serial";
		}
		if(partSerialNumberInput.getText().contains(",")) {
			hasComma[2] = true;
			msgString2 += "\nPart Serial";
		}
		
		// Check Part Name Input
		if(partNameInput.getText().length() == 0) {
			validInputs[3] = false;
			msgString += "\nPart Name";
		}
		if(partNameInput.getText().contains(",")) {
			hasComma[3] = true;
			msgString2 += "\nPart Name";
		}
		
		// Check Part Number Input
		if(partNumberInput.getText().length() == 0) {
			validInputs[4] = false;
			msgString += "\nPart Number";
		}
		if(partNumberInput.getText().contains(",")) {
			hasComma[4] = true;
			msgString2 += "\nPart Number";
		}
		
		// Check Part Quantity Input
		if(partQuantityInput.getText().length() == 0) {
			validInputs[5] = false;
			msgString += "\nQuantity";
		}
		if(partQuantityInput.getText().contains(",")) {
			hasComma[5] = true;
			msgString2 += "\nQuantity";
		}
		
		// Check Removal Reason Input
		if(partRemovalReasonInput.getText().length() == 0) {
			validInputs[6] = false;
			msgString += "\nRemoval Reason";
		}
		// Removal Reason is allowed to have commas.
		
		boolean allValid = true;
		boolean anyCommas = false;
		
		for(int i = 0; i < 7; i++) {
			// If any values in the array are false, allValid = false
			if(!validInputs[i]) {allValid = false;}
			if(hasComma[i]) {anyCommas = true;}
		}
		
		if(!allValid && anyCommas) {
			msgString += "\n\n";
			msgString += msgString2;
		}
		if(allValid) {
			msgString = "";
			msgString += msgString2;
		}
		
		// If not all values are valid
		if(!allValid) {
			JOptionPane.showMessageDialog(depositTabPane, msgString, "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
		}
		// TODO make sure this works
		return allValid && !anyCommas;
	}
	
	public void confirmTagPrint(String tagString) {
		String msgString = "Did the tag successfully print?";
		int response;
		// yes = 0, no = 1
		response = JOptionPane.showConfirmDialog(depositTabPane, msgString, "Confirm Print", JOptionPane.YES_NO_OPTION);
		while(response != 0) {
			PanelView.printTag(tagString);
			response = JOptionPane.showConfirmDialog(depositTabPane, msgString, "Confirm Print", JOptionPane.YES_NO_OPTION);
		}
	}
	
	public void alertNoPrint(String tagString) {
		String msgString = "Tag Cannot Be Printed\nThe Printer Name Set In Config Can Not Be Found\n\nTag Information:\n";
		msgString += tagString;
		JOptionPane.showMessageDialog(depositTabPane, msgString, "Cannot Print", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void enableProfileCreation() {
		loginPane.getRootPane().setDefaultButton(createProfileButton);
		// enable name input and name label
		nameInput.setEnabled(true);
		nameInput.setVisible(true);
		nameLabel.setEnabled(true);
		nameLabel.setVisible(true);
		// enable rank input and rank label
		rankInput.setEnabled(true);
		rankInput.setVisible(true);
		rankLabel.setEnabled(true);
		rankLabel.setVisible(true);
		// disable man button
		manIdButton.setEnabled(false);
		manIdButton.setVisible(false);
		// enable profile button
		createProfileButton.setEnabled(true);
		createProfileButton.setVisible(true);
	}
	
	public void switchToLogHistoryPane() {
		logHistoryPane.getRootPane().setDefaultButton(null);
		//TODO update disabled panes as more panes are added
		
		logSearchInput.setText("");
		
		// Disable admin pane
		adminLayeredPane.setEnabled(false);
		adminLayeredPane.setVisible(false);
		
		// Enable log history pane
		logHistoryPane.setEnabled(true);
		logHistoryPane.setVisible(true);
				
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
				
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
				
		// Setup log table
		PanelView.setUpLogTable(logTable);
	}
	
	public void switchToWithdrawPane() {
		withdrawPane.getRootPane().setDefaultButton(null);
		//TODO update disabled panes as more panes are added
		// Disable admin pane
		adminLayeredPane.setEnabled(false);
		adminLayeredPane.setVisible(false);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Enable withdraw pane
		withdrawPane.setEnabled(true);
		withdrawPane.setVisible(true);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
		
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
		
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
		
		withdrawSearchInput.setText("");
		// Setup withdraw selection table
		PanelView.setUpWithdrawSelectionTable(withdrawSelectionTable);
		
	}
	
	
	public void switchToDepositPane() {
		depositTabPane.getRootPane().setDefaultButton(null);
		templateSelectionTable.getSelectionModel().setValueIsAdjusting(true);
		//TODO update disabled panes as more panes are added
		// Disable admin pane
		adminLayeredPane.setEnabled(false);
		adminLayeredPane.setVisible(false);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
		
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
		
		// Enable Deposit Pane
		depositTabPane.setEnabled(true);
		depositTabPane.setVisible(true);
		
		// Clear the values of the Deposit Pane Fields
		templateSearchInput.setText("");
		jobNumberInput.setText(PanelView.getCurJob());
		aircraftIdInput.setText(PanelView.getCurAircraft());;
		partSerialNumberInput.setText("");;
		partNameInput.setText("");;
		partNumberInput.setText("");;
		partQuantityInput.setText("");;
		partRemovalReasonInput.setText("");;
		updateTemplateCheckBox.setSelected(false);
		isServiceableCheckBox.setSelected(false);
		
		// Setup template table
		PanelView.setUpTemplateTable(templateSelectionTable);
		
		templateSelectionTable.getSelectionModel().setValueIsAdjusting(false);
		
		// Set first tab to selected tab
		depositTabPane.setSelectedIndex(0);
		
	}
	
	
	
	public void switchToProcessSelection() {
		selectionPane.getRootPane().setDefaultButton(null);
		//TODO update disabled panes as more panes are added
		// Disable admin pane
		adminLayeredPane.setEnabled(false);
		adminLayeredPane.setVisible(false);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Enable Selection Pane
		selectionPane.setEnabled(true);
		selectionPane.setVisible(true);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
	}
	
	public void switchToLoginPane() {
		loginPane.getRootPane().setDefaultButton(manIdButton);
		// Clear fields in PanelView
		PanelView.newLogin();
		
		//TODO update disabled panes as more panes are added
		// Disable admin pane
		adminLayeredPane.setEnabled(false);
		adminLayeredPane.setVisible(false);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Enable login Pane
		loginPane.setEnabled(true);
		loginPane.setVisible(true);
						
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
						
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
		
		// Change elements on login pane
		
		// disable name input and name label
		nameInput.setText("");
		nameInput.setEnabled(false);
		nameInput.setVisible(false);
		nameLabel.setEnabled(false);
		nameLabel.setVisible(false);
		// disable rank input and rank label
		rankInput.setText("");
		rankInput.setEnabled(false);
		rankInput.setVisible(false);
		rankLabel.setEnabled(false);
		rankLabel.setVisible(false);
		// enable man button
		manIdButton.setEnabled(true);
		manIdButton.setVisible(true);
		// disable profile button
		createProfileButton.setEnabled(false);
		createProfileButton.setVisible(false);
		// clear manIdInput
		manIdInput.setText("");
		
	}
	
	private void switchToAdminEditInventory() {
		adminPartsPane.getRootPane().setDefaultButton(null);
		// Enable admin pane
		adminLayeredPane.setEnabled(true);
		adminLayeredPane.setVisible(true);
		adminSelectionPane.setEnabled(false);
		adminSelectionPane.setVisible(false);
		adminPartsPane.setEnabled(true);
		adminPartsPane.setVisible(true);
		adminUsersPane.setEnabled(false);
		adminUsersPane.setVisible(false);
		adminTemplatePane.setEnabled(false);
		adminTemplatePane.setVisible(false);
		
		// Setup admin part table
		PanelView.setUpAdminPartTable(adminPartTable);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
	}
	
	private void switchToAdminEditUsers() {
		adminUsersPane.getRootPane().setDefaultButton(null);
		// Enable admin pane
		adminLayeredPane.setEnabled(true);
		adminLayeredPane.setVisible(true);
		adminSelectionPane.setEnabled(false);
		adminSelectionPane.setVisible(false);
		adminPartsPane.setEnabled(false);
		adminPartsPane.setVisible(false);
		adminUsersPane.setEnabled(true);
		adminUsersPane.setVisible(true);
		adminTemplatePane.setEnabled(false);
		adminTemplatePane.setVisible(false);
		
		// Setup admin part table
		PanelView.setUpAdminUserTable(adminUserTable);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
	}
	
	private void switchToAdminEditTemplates() {
		adminUsersPane.getRootPane().setDefaultButton(null);
		// Enable admin pane
		adminLayeredPane.setEnabled(true);
		adminLayeredPane.setVisible(true);
		adminSelectionPane.setEnabled(false);
		adminSelectionPane.setVisible(false);
		adminPartsPane.setEnabled(false);
		adminPartsPane.setVisible(false);
		adminUsersPane.setEnabled(false);
		adminUsersPane.setVisible(false);
		adminTemplatePane.setEnabled(true);
		adminTemplatePane.setVisible(true);
		
		// Setup admin part table
		PanelView.setUpAdminTemplateTable(adminTemplateTable);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
	}
	
	private void switchToAdminSelectionPanel() {
		adminSelectionPane.getRootPane().setDefaultButton(null);
		// Enable admin pane
		adminLayeredPane.setEnabled(true);
		adminLayeredPane.setVisible(true);
		adminSelectionPane.setEnabled(true);
		adminSelectionPane.setVisible(true);
		adminPartsPane.setEnabled(false);
		adminPartsPane.setVisible(false);
		adminUsersPane.setEnabled(false);
		adminUsersPane.setVisible(false);
		adminTemplatePane.setEnabled(false);
		adminTemplatePane.setVisible(false);
		
		// Disable log history pane
		logHistoryPane.setEnabled(false);
		logHistoryPane.setVisible(false);
		
		// Disable withdraw pane
		withdrawPane.setEnabled(false);
		withdrawPane.setVisible(false);
		
		// Disable login Pane
		loginPane.setEnabled(false);
		loginPane.setVisible(false);
				
		// Disable Selection Pane
		selectionPane.setEnabled(false);
		selectionPane.setVisible(false);
				
		// Disable Deposit Pane
		depositTabPane.setEnabled(false);
		depositTabPane.setVisible(false);
	}
	
	/*
	 * 0: job number
	 * 1: aircraft id
	 * 2: part serial
	 * 3: part name
	 * 4: part number
	 * 5: quantity
	 * 6: removal reason
	 * 7: serviceable
	 */
	public String[] getPartInputs() {
		String[] partInputs = {jobNumberInput.getText(),
				aircraftIdInput.getText(),
				partSerialNumberInput.getText(),
				partNameInput.getText(),
				partNumberInput.getText(),
				partQuantityInput.getText(),
				partRemovalReasonInput.getText(),
				"no"};
		if(isServiceableCheckBox.isSelected()) {
			partInputs[7] = "yes";
		}
		return partInputs;
	}
	
	public void alertSerialExists() {
		String msgString = "A Part With The Entered Serial Number Is Already In The Rack. Please "
				+ "Check The Serial Number Or Withdraw The Existing Part";
		JOptionPane.showMessageDialog(depositTabPane, msgString, "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
	}
}
