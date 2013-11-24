package ee.ut.math.tvt.bartersmart.ui.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.bartersmart.domain.data.Order;


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
			return getDateString(order.getCalendar());
		case 1:
			return getTimeString(order.getTime());
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
			buffer.append(getDateString(order.getCalendar()) + "\t");
			buffer.append(getTimeString(order.getTime()) + "\t");
			buffer.append(order.getPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public String getDateString(Calendar time){
		String dateString =new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());
		return dateString;
	}

	public String getTimeString(Date time){
		String timeString =new SimpleDateFormat("HH:mm:ss").format(time.getTime());
		return timeString;
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
        log.debug("Added order on " + getDateString(order.getCalendar()) + " at " +getTimeString(order.getTime()));
        fireTableDataChanged();
    }

}
