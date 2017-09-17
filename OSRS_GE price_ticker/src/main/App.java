package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import javax.swing.JSplitPane;
import java.awt.Panel;
import java.awt.TextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;

public class App {
	
	//swing props
	private JFrame frmOsrsgepriceticker;
	private TextField textField = new TextField();
	private JLabel lblNewLabel = new JLabel("Item");
	private final JLabel lblNewLabel_1 = new JLabel("Item Code");
	private JLabel icon = new JLabel();
	private JLabel today = new JLabel();
	private JLabel price = new JLabel();
	private JLabel day30 = new JLabel();
	private JLabel day90 = new JLabel();
	private JLabel day180 = new JLabel();
	private JLabel description = new JLabel();
	private JLabel tickTime = new JLabel();
	private List<JLabel> labels = new ArrayList<>();
	private JScrollPane scrollPane = new JScrollPane();

	private MainController mainCtrl = new MainController();
	private JSONObject item;
	private int index;
	private Timer timer = new Timer();
	private TickTask tickTask;
	private final JLabel lblTickRate = new JLabel("Tick Rate in Seconds");
	private final JPanel panel_1 = new JPanel();
	private int tickInterval = 5;
	private final JButton btnSaveItemData = new JButton("Save Item Map");
	private HashMap library = null;
	private final JButton button = new JButton("Load Item Map");
	private final JButton button_1 = new JButton("Choose From Library");
	private final JList list = new JList();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmOsrsgepriceticker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOsrsgepriceticker = new JFrame();
		frmOsrsgepriceticker.setTitle("OSRS_GE_price_ticker");
		frmOsrsgepriceticker.getContentPane().setBackground(Color.WHITE);
		btnSaveItemData.setBounds(2, 180, 200, 33);
		btnSaveItemData.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnSaveItemData.setBackground(Color.LIGHT_GRAY);
		btnSaveItemData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainCtrl.saveLibrary(library);
			}
			
		});
		button.setBounds(2, 212, 200, 33);
		button.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		button.setBackground(Color.LIGHT_GRAY);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				library = mainCtrl.mapFromCSV();
				mainCtrl.populateMenu(list,library);
			}
			
		});
		button_1.setBounds(2, 242, 200, 33);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frmOsrsgepriceticker.getContentPane().setLayout(null);
		button_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		button_1.setBackground(Color.LIGHT_GRAY);
		
		frmOsrsgepriceticker.getContentPane().add(button_1);
		frmOsrsgepriceticker.getContentPane().add(button);
		frmOsrsgepriceticker.getContentPane().add(btnSaveItemData);
		
		JButton btnMakeMaptakes = new JButton("Make Map (takes a while)");
		btnMakeMaptakes.setBounds(2, 148, 200, 33);
		btnMakeMaptakes.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnMakeMaptakes.setBackground(Color.LIGHT_GRAY);
		frmOsrsgepriceticker.getContentPane().add(btnMakeMaptakes);
		btnMakeMaptakes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				List<LibraryBuilder>dataSets = mainCtrl.mapDataSets(4, 200);
				List<Thread> threads = mainCtrl.generateLibraryThreads(dataSets);
				int returnCode= mainCtrl.executeLibraryThreads(threads);
				if(returnCode != 0){
					System.err.println("Error : Unable to join threads");
				}
				else {
					library = mainCtrl.reduceDataSets(dataSets);
					library.values().removeIf(Objects::isNull);
				}
			}
			
		});
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(0, 83, 204, 33);
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		frmOsrsgepriceticker.getContentPane().add(textField_1);
		
		JButton btnNewButton = new JButton("Get Item Data");
		btnNewButton.setBounds(2, 116, 200, 33);
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		frmOsrsgepriceticker.getContentPane().add(btnNewButton);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					timer.cancel();
					timer.purge();
					timer = new Timer();
					index = Integer.parseInt(textField.getText());
					item = mainCtrl.getItemData(index);
					mainCtrl.setData(labels, item);
					tickTask = new TickTask(labels,item,timer);
					try{
						tickInterval = Integer.parseInt(textField_1.getText());
					}
					catch(NumberFormatException e2){
						tickInterval = 5;
					}
					timer.scheduleAtFixedRate(tickTask, 0, tickInterval * 1000);
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage()); // function error
				}
			}
		});
		textField.setBounds(0, 28, 204, 33);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		frmOsrsgepriceticker.getContentPane().add(textField);
		panel_1.setBounds(2, 58, 199, 25);
		
		frmOsrsgepriceticker.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		lblTickRate.setBounds(0, 0, 199, 25);
		panel_1.add(lblTickRate);
		lblTickRate.setBackground(Color.LIGHT_GRAY);
		lblTickRate.setHorizontalAlignment(SwingConstants.CENTER);
		lblTickRate.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 782, 27);
		splitPane.setDividerLocation(200);
		frmOsrsgepriceticker.getContentPane().add(splitPane);
		lblNewLabel.setForeground(Color.BLACK);
		
		splitPane.setRightComponent(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		labels.add(lblNewLabel);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		splitPane.setLeftComponent(lblNewLabel_1);
		labels.add(icon);
		icon.setBounds(215, 45, 100, 100);
		frmOsrsgepriceticker.getContentPane().add(icon);
		
		JPanel panel = new JPanel();
		panel.setBounds(327, 40, 449, 181);
		panel.setBackground(Color.WHITE);
		frmOsrsgepriceticker.getContentPane().add(panel);
		panel.setLayout(null);

		today.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		today.setBounds(24, 13, 185, 28);
		labels.add(today);
		panel.add(today);
		
		day30.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		day30.setBounds(24, 43, 185, 28);
		labels.add(day30);
		panel.add(day30);
		
		day90.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		day90.setBounds(24, 71, 185, 28);
		labels.add(day90);
		panel.add(day90);
		
		price.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		price.setBounds(221, 13, 208, 28);
		labels.add(price);
		panel.add(price);
		
		day180.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		day180.setBounds(24, 99, 185, 28);
		labels.add(day180);
		panel.add(day180);
		
		description.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		description.setBounds(24, 140, 413, 28);
		labels.add(description);
		panel.add(description);
		tickTime.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		tickTime.setBounds(221, 54, 208, 28);
		labels.add(tickTime);
		panel.add(tickTime);
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(214, 148, 101, 112);
		frmOsrsgepriceticker.getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(list);

		
		frmOsrsgepriceticker.setBounds(100, 100, 806, 320);
		frmOsrsgepriceticker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
