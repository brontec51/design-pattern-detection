package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JPanel;

public class GUI {

	private JFrame frame;
	private JTextField textField;

	private DesignPatternDetector dpd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 520, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(69, 29, 316, 32);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		dpd = new DesignPatternDetector();

		JLabel lblLink = new JLabel("Link:");
		lblLink.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLink.setBounds(24, 36, 46, 14);
		frame.getContentPane().add(lblLink);

		JLabel lblResult = new JLabel("");
		lblResult.setBounds(34, 95, 351, 156);
		frame.getContentPane().add(lblResult);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblResult.setText("hi");
				prepareSourceCode(textField.getText() + "/archive/master.zip");
				dpd.detect();
			}

			private void prepareSourceCode(String link) {

				try {
					Downloader.download(link);
					Unzipper.unzip("res-folder/master.zip");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		btnSubmit.setBounds(405, 34, 89, 23);
		frame.getContentPane().add(btnSubmit);
	}
}
