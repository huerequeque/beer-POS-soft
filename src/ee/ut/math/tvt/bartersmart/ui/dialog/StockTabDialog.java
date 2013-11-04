package ee.ut.math.tvt.bartersmart.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemModel;

public class StockTabDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTabDialog.class);

	private SalesSystemModel model;
	
	private JTextField idField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField quantityField;

	private JButton addButton;
	private JButton closeButton;

	public StockTabDialog(Window parentWindow, SalesSystemModel model) {
		super(parentWindow);
		this.model = model;

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setModal(true);
		final JComponent panel = draw();
		this.add(panel);
		
	    setTitle("Payment dialog");

	    // set L&F to the nice Windows style
	    try {
	      UIManager.setLookAndFeel(new WindowsLookAndFeel());

	    } catch (UnsupportedLookAndFeelException e1) {
	      log.warn(e1.getMessage());
	    }

	    // size & location
	    int width = 300;
	    int height = 200;
	    setSize(width, height);
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((screen.width - width) / 2, (screen.height - height) / 2);

	    addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent e) {
	        closeButtonClicked();
	      }
	    });
		
		this.setVisible(true);
	}

	private JComponent draw() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Stock item"));

		// Initialize the textfields
		idField = new JTextField();
		nameField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField();

		// == Add components to the panel

		// - id
		panel.add(new JLabel("Id:"));
		panel.add(idField);
		
		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// - quantity
		panel.add(new JLabel("Quantity:"));
		panel.add(quantityField);

		// Create and add the button
		addButton = new JButton("Add item");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemButtonClicked();
			}
		});

		panel.add(addButton);

		closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeButtonClicked();
			}
		});

		panel.add(closeButton);

		return panel;
	}

	private long getId() {
		try {
			long id = (long)Integer.parseInt(idField.getText());
			if (id < 0){
				throw new IllegalArgumentException("Id has to be positive");
			}
			return id;
		} catch (NumberFormatException ex) {
			return -1;
		} catch (NoSuchElementException ex) {
			return -1;
		} catch (IllegalArgumentException ex) {
			return -1;
		}
	}

	private String getProductName() {
		String name = null;
		try {
			name = nameField.getText();
			return name;
		} catch (NoSuchElementException ex) {
			return name;
		}
	}
	
	private double getPrice() {
		try {
			double price = Double.parseDouble(priceField.getText());
			return price;
		} catch (NumberFormatException ex) {
			return -1;
		} catch (NoSuchElementException ex) {
			return -1;
		}
	}
	
	private int getQuantity() {
		try {
			int amount = Integer.parseInt(quantityField.getText());
			return amount;
		} catch (NumberFormatException ex) {
			return 0;
		} catch (NoSuchElementException ex) {
			return 0;
		}
	}
	
	/** Event handler for the <code>submit purchase</code> event. */
	protected void addItemButtonClicked() {
		if (getId()<0){
			String message = "Id invalid.\n"
					+ "Please try again\n";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
		if (getProductName() == null){
			String message = "Invalid name.\n"
					+ "Please try again\n";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
		
		if (getPrice()<0){
			String message = "Price invalid.\n"
					+ "Please try again\n";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
		
		if (getQuantity()<1){
			String message = "Submitted amount should be more than 0.\n"
					+ "Please try again\n";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			log.info("Item added, id: " + getId() + ", name: " + getProductName()
					+ ", price: " + getPrice() + ", quantity: " + getQuantity());
			log.debug("Contents of the current warehouse:\n"
					+ model.getWarehouseTableModel());
			model.getWarehouseTableModel().addItem(new StockItem(getId(), getProductName(), "", getPrice(), getQuantity()));
		}
	}
	
	/** Event handler for the <code>cancel purchase</code> event. */
	protected void closeButtonClicked() {
		log.info("Add item closed");
		endAction();
	}	
	
    /**
     * Reset dialog fields.
     */
    public void reset() {
        priceField.setText("");
        quantityField.setText("");
    }
    
	private void endAction() {
		reset();
		setAlwaysOnTop(false);
		setVisible(false);
	}

}
