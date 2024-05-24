package PIC;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GUI_Practice extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static int lastRow = -1;
	public static boolean fileLoaded = false;
	public static boolean runProgramm = false;
	public static boolean isPort = false;
	public static String filepath = "";

	static String[] columnSPR = new String[] {
            "Adresse", "Bank1", "Bank2"
          };
    
    static String[] columnsGPR = new String[] {
            "Adresse", "Bank1", "Bank2"      
          };
    
    static String[] columnStack = new String[] {
            "Stack", "Wert"      
          };
    
    static String[] columnLST = new String[] {
            "", "", "File content"
    };
    
    public static Object[][] data = new Object [][] {
        {"0x00", 0, 0},
        {"0x01", 0, 0},
        {"0x02", 0, 0},
        {"0x03", 0, 0},
        {"0x04", 0, 0},
        {"0x05", 0, 0},
        {"0x06", 0, 0},
        {"0x07", 0, 0},
        {"0x08", 0, 0},
        {"0x09", 0, 0},
        {"0x0A", 0, 0},
        {"0x0B", 0, 0}
    };
    
    public static Object[][] data2 = new Object[][] {
        {"0x0C", 0, 0},
        {"0x0D", 0, 0},
        {"0x0E", 0, 0},
        {"0x0F", 0, 0},
        {"0x10", 0, 0},
        {"0x11", 0, 0},
        {"0x12", 0, 0},
        {"0x13", 0, 0},
        {"0x14", 0, 0},
        {"0x15", 0, 0},
        {"0x16", 0, 0},
        {"0x17", 0, 0},
        {"0x18", 0, 0},
        {"0x19", 0, 0},
        {"0x1A", 0, 0},
        {"0x1B", 0, 0},
        {"0x1C", 0, 0},
        {"0x1D", 0, 0},
        {"0x1E", 0, 0},
        {"0x1F", 0, 0},
        {"0x20", 0, 0},
        {"0x21", 0, 0},
        {"0x22", 0, 0},
        {"0x23", 0, 0},
        {"0x24", 0, 0},
        {"0x25", 0, 0},
        {"0x26", 0, 0},
        {"0x27", 0, 0},
        {"0x28", 0, 0},
        {"0x29", 0, 0},
        {"0x30", 0, 0},
        {"0x31", 0, 0},
        {"0x32", 0, 0},
        {"0x33", 0, 0},
        {"0x34", 0, 0},
        {"0x35", 0, 0},
        {"0x36", 0, 0},
        {"0x37", 0, 0},
        {"0x38", 0, 0},
        {"0x39", 0, 0},
        {"0x3A", 0, 0},
        {"0x3B", 0, 0},
        {"0x3C", 0, 0},
        {"0x3D", 0, 0},
        {"0x3E", 0, 0},
        {"0x3F", 0, 0},
        {"0x40", 0, 0},
        {"0x41", 0, 0},
        {"0x42", 0, 0},
        {"0x43", 0, 0},
        {"0x44", 0, 0},
        {"0x45", 0, 0},
        {"0x46", 0, 0},
        {"0x47", 0, 0},
        {"0x48", 0, 0},
        {"0x49", 0, 0},
        {"0x4A", 0, 0},
        {"0x4B", 0, 0},
        {"0x4C", 0, 0},
        {"0x4D", 0, 0},
        {"0x4E", 0, 0},
        {"0x4F", 0, 0},
        {"0x50", 0, 0},
        {"0x51", 0, 0},
        {"0x52", 0, 0},
        {"0x53", 0, 0},
        {"0x54", 0, 0},
        {"0x55", 0, 0},
        {"0x56", 0, 0},
        {"0x57", 0, 0},
        {"0x58", 0, 0},
        {"0x59", 0, 0},
        {"0x5A", 0, 0},
        {"0x5B", 0, 0},
        {"0x5C", 0, 0},
        {"0x5D", 0, 0},
        {"0x5E", 0, 0},
        {"0x5F", 0, 0},
        {"0x60", 0, 0},
        {"0x61", 0, 0},
        {"0x62", 0, 0},
        {"0x63", 0, 0},
        {"0x64", 0, 0},
        {"0x65", 0, 0},
        {"0x66", 0, 0},
        {"0x67", 0, 0},
        {"0x68", 0, 0},
        {"0x69", 0, 0},
        {"0x6A", 0, 0},
        {"0x6B", 0, 0},
        {"0x6C", 0, 0},
        {"0x6D", 0, 0},
        {"0x6E", 0, 0},
        {"0x6F", 0, 0},
        {"0x70", 0, 0},
        {"0x71", 0, 0},
        {"0x72", 0, 0},
        {"0x73", 0, 0},
        {"0x74", 0, 0},
        {"0x75", 0, 0},
        {"0x76", 0, 0},
        {"0x77", 0, 0},
        {"0x78", 0, 0},
        {"0x79", 0, 0},
        {"0x7A", 0, 0},
        {"0x7B", 0, 0},
        {"0x7C", 0, 0},
        {"0x7D", 0, 0},
        {"0x7E", 0, 0},
        {"0x7F", 0, 0}
        
    };
    
    public static Object[][] data3 = new Object[][] {
        {"Stack 0", "-"},
        {"Stack 1", "-"},  
        {"Stack 2", "-"},  
        {"Stack 3", "-"},  
        {"Stack 4", "-"},  
        {"Stack 5", "-"},  
        {"Stack 6", "-"},
        {"Stack 7", "-"}
    };
    
    public static Object[][] data4 = new Object[1024][3];
    
    static JTable tableSPR = new JTable(data, columnSPR);
    static JTable table = new JTable(data2, columnsGPR);
    static JTable tableStack = new JTable(data3, columnStack);
    static JTable tableLST = new JTable();
    static String fileInput = "";
    static boolean isLoaded = false;
	
//-----------------------------------------------------------------------------------	
    
	/**
	 * Launch the application.
	 */
    public void main() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI_Practice frame = new GUI_Practice();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//-----------------------------------------------------------------------------------	
    
	/**
	 * Create the frame.
	 */
	public GUI_Practice() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1700, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//-----------------------------------------------------------------------------------			
		JScrollPane spSPR = new JScrollPane();
		spSPR.setBounds(1376, 61, 265, 218);
		spSPR.setViewportView(tableSPR);
		contentPane.add(spSPR);
		
//-----------------------------------------------------------------------------------	   
		JScrollPane spGPR = new JScrollPane();
		spGPR.setBounds(1376, 312, 265, 477);
		spGPR.setViewportView(table);
		contentPane.add(spGPR);
        
//-----------------------------------------------------------------------------------	
        
		JScrollPane spStack = new JScrollPane();
		spStack.setBounds(37, 636, 259, 153);
		spStack.setViewportView(tableStack);
		contentPane.add(spStack);
		
//-----------------------------------------------------------------------------------		
		
		JScrollPane spLST = new JScrollPane();
		spLST.setBounds(329, 61, 1012, 728);
		
		DefaultTableModel model = new DefaultTableModel();
		tableLST.setModel(model);
		
		model.addColumn("");
		model.addColumn("");
		model.addColumn("File content");
		
		tableLST.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableLST.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableLST.getColumnModel().getColumn(2).setPreferredWidth(3000);
		
		spLST.setViewportView(tableLST);
		contentPane.add(spLST);
		
//-----------------------------------------------------------------------------------			
		
		JButton runButton = new JButton("Run");
		runButton.setBounds(329, 27, 89, 23);
		contentPane.add(runButton);
		
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event1) {
				runProgramm = true;	
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JButton stopButton = new JButton("Stop");
		stopButton.setBounds(428, 27, 89, 23);
		contentPane.add(stopButton);
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event2) {
				runProgramm = false;
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JButton stepButton = new JButton("Step");
		stepButton.setBounds(527, 27, 89, 23);
		contentPane.add(stepButton);
		
//-----------------------------------------------------------------------------------	
		
		JButton resetButton = new JButton("Reset");
		resetButton.setBounds(627, 27, 89, 23);
		contentPane.add(resetButton);
		
//-----------------------------------------------------------------------------------	
		
		JButton loadButton = new JButton("Load");
		loadButton.setBounds(1251, 27, 89, 23);
		contentPane.add(loadButton);
		
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event5) {
				if(event5.getSource() == loadButton) {
					JFileChooser filechooser = new JFileChooser();
					int i = filechooser.showOpenDialog(loadButton);
					
					if(i == JFileChooser.APPROVE_OPTION) {
						File file = filechooser.getSelectedFile();
						filepath = file.getPath();
						
						try {
							FileReader filereader = new FileReader(filepath);
							BufferedReader bufferedReader = new BufferedReader(filereader);
							
							String line = "";
							while ((line = bufferedReader.readLine()) != null) {
				            	
								model.addRow(new Object[] {"", "", line});
				            }

				            bufferedReader.close();
				            fileLoaded = true;
				             
						}
						catch (IOException e) {
				            e.printStackTrace();
				            }
					}
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox ra0CheckBox = new JCheckBox("RA0");
		ra0CheckBox.setBounds(55, 175, 67, 23);
		contentPane.add(ra0CheckBox);
		
		ra0CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra0CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 0);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 0);
					updateColumnAtRow(5);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox ra1CheckBox = new JCheckBox("RA1");
		ra1CheckBox.setBounds(55, 224, 67, 23);
		contentPane.add(ra1CheckBox);
		
		ra1CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra1CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 1);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 1);
					updateColumnAtRow(5);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox ra2CheckBox = new JCheckBox("RA2");
		ra2CheckBox.setBounds(55, 271, 67, 23);
		contentPane.add(ra2CheckBox);
		
		ra2CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra2CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 2);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 2);
					updateColumnAtRow(5);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox ra3CheckBox = new JCheckBox("RA3");
		ra3CheckBox.setBounds(55, 325, 67, 23);
		contentPane.add(ra3CheckBox);
		
		ra3CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra3CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 3);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 3);
					updateColumnAtRow(5);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox ra4CheckBox = new JCheckBox("RA4");
		ra4CheckBox.setBounds(55, 378, 67, 23);
		contentPane.add(ra4CheckBox);
		
		ra4CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra4CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 4);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 4);
					updateColumnAtRow(5);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb0CheckBox = new JCheckBox("RB0");
		rb0CheckBox.setBounds(240, 175, 67, 23);
		contentPane.add(rb0CheckBox);
		
		rb0CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb0CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 0);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 0);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb1CheckBox = new JCheckBox("RB1");
		rb1CheckBox.setBounds(240, 224, 67, 23);
		contentPane.add(rb1CheckBox);
		
		rb1CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb1CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 1);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 1);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb2CheckBox = new JCheckBox("RB2");
		rb2CheckBox.setBounds(240, 271, 67, 23);
		contentPane.add(rb2CheckBox);
		
		rb2CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb2CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 2);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 2);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb3CheckBox = new JCheckBox("RB3");
		rb3CheckBox.setBounds(240, 325, 67, 23);
		contentPane.add(rb3CheckBox);
		
		rb3CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb3CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 3);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 3);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb4CheckBox = new JCheckBox("RB4");
		rb4CheckBox.setBounds(240, 378, 67, 23);
		contentPane.add(rb4CheckBox);
		
		
		rb4CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb4CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 4);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 4);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb5CheckBox = new JCheckBox("RB5");
		rb5CheckBox.setBounds(240, 428, 67, 23);
		contentPane.add(rb5CheckBox);
		
		rb5CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb5CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 5);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 5);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb6CheckBox = new JCheckBox("RB6");
		rb6CheckBox.setBounds(240, 481, 67, 23);
		contentPane.add(rb6CheckBox);
		
		rb6CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb6CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 6);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 6);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
		
//-----------------------------------------------------------------------------------	
		
		JCheckBox rb7CheckBox = new JCheckBox("RB7");
		rb7CheckBox.setBounds(240, 536, 67, 23);
		contentPane.add(rb7CheckBox);
		
		
		rb7CheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb7CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 7);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 7);
					updateColumnAtRow(6);
					isPort = false;
					
				}
				
			}
		});
//-----------------------------------------------------------------------------------	
		
		JLabel timerLabel = new JLabel("Timer");
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		timerLabel.setBounds(37, 61, 248, 49);
		contentPane.add(timerLabel);
	}
	
//-----------------------------------------------------------------------------------	
	
	public static void updateTabelle() {
        
        for(int i = 0; i < data.length; i++) {
        	int number = Datenspeicher.getRegister(0, i);
        	String binaryStr = Integer.toBinaryString(number);
        	
        	while(binaryStr.length() < 8) {
        		binaryStr = "0" + binaryStr;
        	}
        	
        	tableSPR.setValueAt(binaryStr, i, 1);
        	
        }
        
        for(int i = 0; i < data.length; i++) {
        	int number = Datenspeicher.getRegister(1, i);
        	String binaryStr = Integer.toBinaryString(number);
        	
        	while(binaryStr.length() < 8) {
        		binaryStr = "0" + binaryStr;
        	}
        	
        	tableSPR.setValueAt(binaryStr, i, 2);
        	
        }
        
        
        for(int i = 0; i < data2.length; i++) {
        	int number = Datenspeicher.PullDataFromRegister(i);
        	String hexStr = Integer.toHexString(number);
        	
        	while(hexStr.length() < 2) {
        		hexStr = "0" + hexStr;
        	}
        	
        	hexStr = "0x" + hexStr;
        	
            table.setValueAt(hexStr, i, 1);
            table.setValueAt(hexStr, i, 2);
        }
        
        if(Stack.currentSize > 0) {
        	int number = Stack.top();
        	String hexStr = Integer.toHexString(number);
        	
        	while(hexStr.length() < 2) {
        		hexStr = "0" + hexStr;
        	}
        	
        	hexStr = "0x" + hexStr;
        	
        	tableStack.setValueAt(hexStr, Stack.currentSize - 1, 1);
        }
        
    }
	
//-----------------------------------------------------------------------------------	
	
	public static void updateTabelleStackRemove() {
		tableStack.setValueAt("-", Stack.currentSize - 1, 1);
	}
	
//-----------------------------------------------------------------------------------	
	
	public static void updateRow(int row) {
		
		if(lastRow == -1) {
			tableLST.setValueAt("----->", row, 1);
			lastRow = row;
		}
		else {
			tableLST.setValueAt("", lastRow, 1);
			tableLST.setValueAt("----->", row, 1);
			lastRow = row;
		}
		
	}
	
	public static void updateColumnAtRow(int register) {
		int number = Datenspeicher.getRegister(0, register);
    	String binaryStr = Integer.toBinaryString(number);
    	
    	while(binaryStr.length() < 8) {
    		binaryStr = "0" + binaryStr;
    	}
    	
    	tableSPR.setValueAt(binaryStr, register, 1);
	}
	
	
	public static void setContentToZero() {
		for(int i = 0; i < data.length; i++) {
        	int number = 0;
        	String binaryStr = Integer.toBinaryString(number);
        	
        	while(binaryStr.length() < 8) {
        		binaryStr = "0" + binaryStr;
        	}
        	
        	tableSPR.setValueAt(binaryStr, i, 1);
        	tableSPR.setValueAt(binaryStr, i, 2);
        	
        }
		
		
		for(int i = 0; i < data2.length; i++) {
        	int number = 0;
        	String hexStr = Integer.toHexString(number);
        	
        	while(hexStr.length() < 2) {
        		hexStr = "0" + hexStr;
        	}
        	
        	hexStr = "0x" + hexStr;
        	
            table.setValueAt(hexStr, i, 1);
            table.setValueAt(hexStr, i, 2);
        }
	}
}

















