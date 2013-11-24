package ee.ut.math.tvt.bartersmart.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.bartersmart.domain.controller.SalesDomainController;
import ee.ut.math.tvt.bartersmart.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.domain.exception.DuplicateStockItemNameException;
import ee.ut.math.tvt.bartersmart.service.HibernateDataService;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemTableModel;
import ee.ut.math.tvt.bartersmart.ui.model.StockTableModel;


public class StockTableModelTest {
		
	private SalesSystemTableModel<StockItem> stockTableModel;
	private StockItem stockItem1;
	private StockItem stockItem2;
	
	@Before
	public void setUp() {
		stockTableModel = new StockTableModel();

		stockItem1 = new StockItem((long) 1, "Saku", "beer", 2.0, 2);
		stockItem2 = new StockItem((long) 2, "fries", "food", 3.0, 10);
		List<StockItem> goods = new ArrayList<StockItem>();
		goods.add(stockItem1);
		goods.add(stockItem2);
		
		stockTableModel.populateWithData(goods);

	}
	
	@After
	public void tearDown() {
		stockTableModel = null;
	}
	
	@Test
    public void testValidateNameUniqueness() throws DuplicateStockItemNameException{
		assertTrue(stockTableModel.isUniqueName("A le coq"));
		assertFalse(stockTableModel.isUniqueName("Saku"));
	}
	
	@Test
    public void testHasEnoughInStock() {
    	SoldItem soldItem1 = new SoldItem(stockItem1, 3);
    	SoldItem soldItem2 = new SoldItem(stockItem2, 5);
		assertFalse(stockTableModel.hasEnoughInStock(stockItem1, soldItem1));
		assertTrue(stockTableModel.hasEnoughInStock(stockItem2, soldItem2));
	} 
    
	@Test
    public void testGetItemByIdWhenItemExists() {
		assertEquals(stockTableModel.getItemById(2),stockItem2);
	}
	
	@Test(expected = NoSuchElementException.class)
    public void testGetItemByIdWhenThrowsException() {
		stockTableModel.getItemById(3);
	}
	
}
