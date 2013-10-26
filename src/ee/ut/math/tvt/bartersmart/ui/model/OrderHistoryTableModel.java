package ee.ut.math.tvt.bartersmart.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.ui.SalesSystemUI;

/**
 * Purchase history details model.
 */
public class OrderHistoryTableModel extends SalesSystemTableModel<Order> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(OrderHistoryTableModel.class);
	
	public OrderHistoryTableModel() {
		super(new String[] { "Date", "Time", "Price"});
	}

	@Override
	protected Object getColumnValue(Order order, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return order.getDateString();
		case 1:
			return order.getTimeString();
		case 2:
			return order.getPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Order order : rows) {
			buffer.append(order.getDateString() + "\t");
			buffer.append(order.getTimeString() + "\t");
			buffer.append(order.getPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    /**
     * Add new StockItem to table.
     */
    public void addOrder(final Order order) {
        /**
         * XXX In case such stockItem already exists increase the quantity of the
         * existing stock.
         */
        
        rows.add(order);
        System.out.println("Added order on " + order.getDateString() + " at " + order.getTimeString());
        log.debug("Added order on " + order.getDateString() + " at " + order.getTimeString());
        fireTableDataChanged();
    }

}
