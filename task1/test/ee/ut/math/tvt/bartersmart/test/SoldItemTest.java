package ee.ut.math.tvt.bartersmart.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;


public class SoldItemTest {
		
	private StockItem stockItem;
	
	@Before
	public void setUp() {
		stockItem = new StockItem((long) 1, "Saku", "beer", 2.0);
	}
	
	@After
	public void tearDown() throws Exception {
		stockItem = null;
	}
	
	@Test
    public void testGetSum(){
		SoldItem soldItem = new SoldItem(stockItem, 3);
		assertEquals(soldItem.getSum(), 6.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithZeroQuantity(){
		SoldItem soldItem = new SoldItem(stockItem, 0);
		assertEquals(soldItem.getSum(), 0.0, 0.0001);
	}
	
}
