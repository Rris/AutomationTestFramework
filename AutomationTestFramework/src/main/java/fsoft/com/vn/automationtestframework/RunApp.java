package fsoft.com.vn.automationtestframework;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RunApp.
 */
public class RunApp {

	/** The frame. */
	private JFrame frame;
	
	/** The pw. */
	private PrintWriter pw;
	
	/** The fos. */
	private FileOutputStream fos;
	
	/** The chooser. */
	private JFileChooser chooser;
	
	/** The files. */
	private File[] files;
	
	/** The table. */
	private JTable table;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunApp window = new RunApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Instantiates a new run app.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
	 */
	public RunApp() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		initialize();
	}

	/**
	 * Initialize.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws UnsupportedLookAndFeelException the unsupported look and feel exception
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 469);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE));

		JLabel lblAutomationTest = new JLabel("AUTOMATION TEST");
		lblAutomationTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutomationTest.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblFileSeleted = new JLabel("You selected 0 file");

		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.showOpenDialog(null);
				files = chooser.getSelectedFiles();
				if (files.length != 0) {
					for (int i = 0; i < files.length; i++) {
						((DefaultTableModel)table.getModel()).addRow(new Object[]{files[i].getPath()});
					}
					lblFileSeleted.setText("You seleted " + table.getRowCount() + " files");
				}
			}
		});
		lblFileSeleted.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnRun = new JButton("RUN TEST");
		btnRun.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				if(table.getRowCount() == 0){
					JOptionPane.showMessageDialog(frame, "You need choose file to test.");
				}else{
					try {
						fos = new FileOutputStream("../file-selected.txt", true);
						pw = new PrintWriter(fos);
					} catch (FileNotFoundException ee) {
						ee.printStackTrace();
					}
					for (int i = 0; i < table.getRowCount(); i++) {
						pw.println(table.getValueAt(i, 0));
					}
					pw.close();
					TestListenerAdapter tla = new TestListenerAdapter();
					TestNG testng = new TestNG();
					testng.setTestClasses(new Class[] { TestNGClass.class });
					testng.addListener(tla);
					testng.run();
				}
				
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1){
					JOptionPane.showConfirmDialog(frame, "Choose file to delete");
				}else{
					int[] index = table.getSelectedRows();
					for (int i = 0; i < index.length; i++) {
						if(i == 0){
							((DefaultTableModel)table.getModel()).removeRow(index[i]);
						}else{
							((DefaultTableModel)table.getModel()).removeRow(index[i] - i);
						}
						lblFileSeleted.setText("You seleted " + table.getRowCount() + " files");
					}
					
				}
				
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblFileSeleted, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
				.addComponent(lblAutomationTest, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(348)
					.addComponent(btnChooseFile, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(347))
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap(347, Short.MAX_VALUE)
							.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addGap(121)
							.addComponent(btnNewButton))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addGap(127)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 540, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(127, Short.MAX_VALUE))
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(26)
							.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)))
					.addGap(25))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Files Name"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}

	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}
}
