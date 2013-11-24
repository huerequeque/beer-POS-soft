package ee.ut.math.tvt.bartersmart.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.ui.model.OrderHistoryTableModel;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemTableModel;


public class OrderHistoryTableModelTest {

	private SalesSystemTableModel<Order> orderHistoryTableModel;
	private List<Order> orders;
	private Calendar cal1;
	private Calendar cal2;
	private Order order1;
	private Order order2;

	@Before
	public void setUp() {
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
		try {
			cal1.setTime(sdf.parse("Fri Nov 22 17:36:12 2013"));
			cal2.setTime(sdf.parse("Sat Nov 23 16:02:37 2013"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StockItem stockItem1 = new StockItem((long) 1, "Saku", "beer", 2.0);
		StockItem stockItem2 = new StockItem((long) 2, "fries", "food", 3.0);
		SoldItem soldItem1 = new SoldItem(stockItem1, 2);
		SoldItem soldItem2 = new SoldItem(stockItem2, 1);
		List <SoldItem> goods1 = new ArrayList<SoldItem>();
		goods1.add(soldItem1);
		goods1.add(soldItem2);
		order1 = new Order(1, cal1, goods1);

		StockItem stockItem3 = new StockItem((long) 3, "A le coq", "beer", 2.0);
		SoldItem soldItem3 = new SoldItem(stockItem3, 3);
		List <SoldItem> goods2 = new ArrayList<SoldItem>();
		goods2.add(soldItem3);
		order2 = new Order(2, cal2, goods2);

		orders = new ArrayList<Order>();
		orders.add(order1);
		orders.add(order2);

		orderHistoryTableModel = new OrderHistoryTableModel();
		orderHistoryTableModel.populateWithData(orders);
	}

	@After
	public void tearDown(){
		orderHistoryTableModel = null;
		orders.clear();
		cal1 = null;
		cal2 = null;
		order1 = null;
		order2 = null;

	}

	@Test
	public void testDateString(){
		assertEquals(orderHistoryTableModel.getDateString(cal1), "2013-11-22");
	}

	@Test
	public void testTimeString() {
		assertEquals(orderHistoryTableModel.getTimeString(cal2.getTime()), "16:02:37");
	} 



}
