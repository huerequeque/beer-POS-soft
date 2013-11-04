package ee.ut.math.tvt.bartersmart.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
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

import ee.ut.math.tvt.bartersmart.domain.controller.SalesDomainController;
import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemModel;

public class PurchaseTabDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseTabDialog.class);

	private final SalesDomainController domainController;

	private SalesSystemModel model;

	private JTextField sumField;
	private JTextField paidAmountField;
	private JTextField changeField;

	private JButton acceptPaymentButton;
	private JButton cancelPaymentButton;

	public PurchaseTabDialog(Window parentWindow, SalesDomainController controller,
			SalesSystemModel model) {
		super(parentWindow);
		this.domainController = controller;
		this.model = model;

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setModal(true);
		final JComponent panel = draw(controller);
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
	        cancelPaymentButtonClicked();
	      }
	    });
		
		this.setVisible(true);
	}

	private JComponent draw(SalesDomainController domainController) {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		// Initialize the textfields
		sumField = new JTextField(domainController.getCurrentPurchasePrice()
				+ "");
		paidAmountField = new JTextField();
		changeField = new JTextField();

		// Fill the dialog fields if the bar code text field loses focus
		paidAmountField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fillDialogFields();
			}
		});

		changeField.setEditable(false);
		sumField.setEditable(false);

		// == Add components to the panel

		// - bar code
		panel.add(new JLabel("Total sum:"));
		panel.add(sumField);

		// - amount
		panel.add(new JLabel("Amount paid:"));
		panel.add(paidAmountField);

		// - name
		panel.add(new JLabel("Change:"));
		panel.add(changeField);

		// Create and add the button
		acceptPaymentButton = new JButton("Accept payment");
		acceptPaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptPaymentButtonClicked();
			}
		});

		panel.add(acceptPaymentButton);

		cancelPaymentButton = new JButton("Cancel");
		cancelPaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPaymentButtonClicked();
			}
		});

		panel.add(cancelPaymentButton);

		return panel;
	}

	public void fillDialogFields() {
		double amountPaid = getAmountPaid();
		double sum = Double.parseDouble(sumField.getText());
		if (amountPaid >= sum) {
			changeField.setText((amountPaid - sum + ""));
		} else {
			changeField.setText("ERROR");
		}
	}

	private double getAmountPaid() {
		try {
			double amount = Double.parseDouble(paidAmountField.getText());
			return amount;
		} catch (NumberFormatException ex) {
			return 0;
		} catch (NoSuchElementException ex) {
			return 0;
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void acceptPaymentButtonClicked() {
		fillDialogFields();
		if (changeField.getText().equals("ERROR")){
			String message = "Submitted amount is either invalid or less than the sum.\n"
					+ "Please try again\n";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {
				log.debug("Contents of the current basket:\n"
						+ model.getCurrentPurchaseTableModel());
				domainController.submitCurrentPurchase(model
						.getCurrentPurchaseTableModel().getTableRows());
				model.getOrderHistoryTableModel().addOrder(new Order
						(domainController.getLastId(), Calendar.getInstance(), model
						.getCurrentPurchaseTableModel().getTableRows()));
				model.getWarehouseTableModel().updateWarehouse(model
						.getCurrentPurchaseTableModel().getTableRows());
				domainController.finalizePurchase();
				endPayment();
				model.getCurrentPurchaseTableModel().clear();
			} catch (VerificationFailedException e1) {
				log.error(e1.getMessage());
				reset();
			}
		}
	}
	
	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPaymentButtonClicked() {
		log.info("Payment cancelled");
		endPayment();
	}	
	
    /**
     * Reset dialog fields.
     */
    public void reset() {
        paidAmountField.setText("");
        changeField.setText("");
    }
    
	private void endPayment() {
		log.info("Payment complete");
		reset();
		setAlwaysOnTop(false);
		setVisible(false);
	}

}
