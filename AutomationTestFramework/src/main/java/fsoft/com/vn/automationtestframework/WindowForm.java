package fsoft.com.vn.automationtestframework;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;


public class WindowForm {

	private JFrame frame;
	private String nameFiles = "";
	JTextArea txtrFileSeleted;
	PrintWriter pw;
	FileOutputStream fos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowForm window = new WindowForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public WindowForm() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 469);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo (null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
		);
		
		JLabel lblAutomationTest = new JLabel("AUTOMATION TEST");
		lblAutomationTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutomationTest.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblFileSeleted = new JLabel("You selected 0 file");
		
		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.showOpenDialog(null);
				File[] files = chooser.getSelectedFiles();
				if (files.length != 0) {
					try {
						
						fos = new FileOutputStream("../file-selected.txt", true);
						pw = new PrintWriter(fos);
					} catch (FileNotFoundException ee) {
						ee.printStackTrace();
					}
					for (int i = 0; i < files.length; i++) {
						nameFiles += files[i].getName() + "; \n";
						pw.println(files[i].getPath());
					}
					pw.close();
					lblFileSeleted.setText("You seleted " + files.length + " files");
					
					txtrFileSeleted.setText(nameFiles);
				}
				
			}
		});
		
		lblFileSeleted.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnRun = new JButton("RUN TEST");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TestListenerAdapter tla = new TestListenerAdapter();
				TestNG testng = new TestNG();
				testng.setTestClasses(new Class[] { TestNGClass.class });
				testng.addListener(tla);
				testng.run();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addComponent(lblFileSeleted, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
				.addComponent(lblAutomationTest, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(348)
					.addComponent(btnChooseFile, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(347))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(282)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(282, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(347, Short.MAX_VALUE)
					.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(337))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblAutomationTest)
					.addGap(43)
					.addComponent(btnChooseFile)
					.addGap(18)
					.addComponent(lblFileSeleted)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
					.addGap(26)
					.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
		);
		
		txtrFileSeleted = new JTextArea();
		scrollPane.setViewportView(txtrFileSeleted);
		txtrFileSeleted.setText("Not file selected");
		txtrFileSeleted.setEditable(false);
		
		txtrFileSeleted.setBackground(new Color(211, 211, 211));
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
	public JFrame getFrame() {
		return frame;
	}
}
