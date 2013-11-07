package ee.ut.math.tvt.bartersmart.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.bartersmart.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.bartersmart.domain.controller.SalesDomainController;
import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemModel;
import ee.ut.math.tvt.bartersmart.util.HibernateUtil;
import ee.ut.math.tvt.bartersmart.service.HibernateDataService;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	private double currentPurchasePrice;
	private long lastId;
	private final HibernateDataService service;
	
	public SalesDomainControllerImpl(HibernateDataService service) {
		this.service=service;
	}

	public double getCurrentPurchasePrice() {
		return currentPurchasePrice;
	}

	public void setCurrentPurchasePrice(double currentPurchasePrice) {
		this.currentPurchasePrice = currentPurchasePrice;
	}

	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
		for (SoldItem item : goods){
			currentPurchasePrice+=item.getSum();
		}
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
		currentPurchasePrice=0;
	}
	
	public void finalizePurchase(Order order, List<SoldItem> soldItems) {
		lastId+=1;
		service.saveOrder(order);
		for (SoldItem item: soldItems){
			item.setOrder(order);
			service.saveOrder(order);
		}
	}
	
	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
		currentPurchasePrice=0;
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = new ArrayList<StockItem>();

//		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
//		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
//	    StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sauseges", 15.0, 12);
//	    StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);
//
//		dataset.add(chips);
//		dataset.add(chupaChups);
//		dataset.add(frankfurters);
//		dataset.add(beer);
		
		HibernateDataService service = new HibernateDataService();
        dataset = service.getStockItems();
		
		return dataset;
	}

	public long getLastId() {
		return lastId;
	}

	public void setLastId(long lastId) {
		this.lastId = lastId;
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}

	@Override
	public List<Order> loadOrderHistory() {
		// TODO Auto-generated method stub
		List<Order> dataset = new ArrayList<Order>();
		
		HibernateDataService service = new HibernateDataService();
        dataset = service.getOrders();
		
		return dataset;
	}
}
