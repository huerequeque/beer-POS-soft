package ee.ut.math.tvt.bartersmart.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.bartersmart.domain.data.StockItem;


public class StockItemTest {
		
	private StockItem stockItem;
	
	@Before
	public void setUp() {
		stockItem = new StockItem((long) 1, "Saku", "beer", 2.0, 1);
	}
	
	@After
	public void tearDown() throws Exception {
		stockItem = null;
	}
	
	@Test
    public void testClone(){
		StockItem clone = (StockItem) stockItem.clone();
		assertEquals(stockItem.getId(), clone.getId());
		assertEquals(stockItem.getName(), clone.getName());
		assertEquals(stockItem.getDescription(), clone.getDescription());
		assertEquals(stockItem.getPrice(), clone.getPrice(), 0.0001);
		assertEquals(stockItem.getQuantity(), clone.getQuantity());
	}
	
	@Test
	public void testGetColumn(){
		assertEquals(stockItem.getColumn(0), stockItem.getId());
		assertEquals(stockItem.getColumn(1), stockItem.getName());
		assertEquals(stockItem.getColumn(2), stockItem.getPrice());
		assertEquals(stockItem.getColumn(3), stockItem.getQuantity());
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetNegativeColumn() {
		stockItem.getColumn(-1);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetOutOfBoundsColumn() {
		stockItem.getColumn(4);
	}
	
}
