package ee.ut.math.tvt.bartersmart.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;


public class OrderTest {
		
	private List<SoldItem> goods1;
	private List<SoldItem> goods2;
	
	@Before
	public void setUp() {
		StockItem stockItem1 = new StockItem((long) 1, "Saku", "beer", 2.0);
		StockItem stockItem2 = new StockItem((long) 2, "fries", "food", 3.0);
		SoldItem soldItem1 = new SoldItem(stockItem1, 2);
		SoldItem soldItem2 = new SoldItem(stockItem2, 1);
		goods1 = new ArrayList<SoldItem>();
		goods1.add(soldItem1);
		goods2 = new ArrayList<SoldItem>();
		goods2.add(soldItem1);
		goods2.add(soldItem2);
	}
	
	@After
	public void tearDown() throws Exception {
		goods1.clear();
		goods2.clear();
	}
	
	@Test
    public void testAddSoldItem(){
		Order order = new Order(1, Calendar.getInstance(), goods2);
		StockItem stockItem = new StockItem((long) 3, "cola", "non-alc", 2.0);
		SoldItem soldItem = new SoldItem(stockItem, 1);
		order.addSoldItem(soldItem);
		assertEquals(order.getGoods().get(order.getGoods().size()-1), soldItem);
		assertEquals(order.getPrice(), 9.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithNoItems(){
		Order order = new Order(1, Calendar.getInstance(), new ArrayList<SoldItem>());
		assertEquals(order.getGoods().size(), 0);
		assertEquals(order.getPrice(), 0.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithOneItem(){
		Order order = new Order(1, Calendar.getInstance(), goods1);
		assertEquals(order.getGoods().size(), 1);
		assertEquals(order.getPrice(), 4.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		Order order = new Order(1, Calendar.getInstance(), goods2);
		assertEquals(order.getGoods().size(), 2);
		assertEquals(order.getPrice(), 7.0, 0.0001);
		
	}

}
