package ee.ut.math.tvt.bartersmart.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
    // TODO - implement!
	
	private static final Logger log = Logger.getLogger(PurchaseTab.class);
	
	private JTable historyTable;
	private JPanel basketPane;

	private SalesSystemModel model;
	private PurchaseInfoTableModel orderModel;

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
		orderModel = new PurchaseInfoTableModel();
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(drawStockMainPane(), getConstraintsForHistoryPanel());

		// Add the main purchase-panel
		panel.add(drawBasketPane(), getConstraintsForHistoryPanel());

		return panel;
	}
	
	  // table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		historyTable = new JTable(model.getOrderHistoryTableModel());

		JTableHeader header = historyTable.getTableHeader();
		header.setReorderingAllowed(false);
		
		historyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
	        	if (historyTable.getSelectedRow()>-1){
	        		orderModel.clear();
	        		List<SoldItem> goods = getOrderById((long)historyTable.getSelectedRow()+1).getGoods();
	        		for (SoldItem item : goods){
	        			orderModel.addItem(item);
	        		}
	        	}
	        }
	    });

		JScrollPane scrollPane = new JScrollPane(historyTable);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Orders"));
		return panel;
	}
	
	private JComponent drawBasketPane() {

        // Create the basketPane
        basketPane = new JPanel();
        basketPane.setLayout(new GridBagLayout());
        basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

        // Create the table, put it inside a scollPane,
        // and add the scrollPane to the basketPanel.
        JTable orderDetailsTable = new JTable(orderModel);
        JScrollPane scrollPane = new JScrollPane(orderDetailsTable);

        basketPane.add(scrollPane, getBacketScrollPaneConstraints());

        return basketPane;
    }
	
    private Order getOrderById(long id) {
    	return model.getOrderHistoryTableModel().getItemById(id);
    }
	
    private GridBagConstraints getBacketScrollPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        return gc;
    }


	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForHistoryPanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

}