package ee.ut.math.tvt.bartersmart.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.bartersmart.domain.data.Order;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();
	
	public void saveStockItem(StockItem stockItem){
		session.beginTransaction();
		StockItem newItem = new StockItem();
		newItem.setName(stockItem.getName());
		newItem.setPrice(stockItem.getPrice());
		newItem.setDescription(stockItem.getDescription());
		newItem.setQuantity(stockItem.getQuantity());
		session.save(newItem);
		session.getTransaction().commit();
		
	}
	
	public void saveOrder(Order order){
		session.beginTransaction();
		Order newOrder = new Order();
		newOrder.setCalendar(order.getCalendar());
		newOrder.setPrice(order.getPrice());
		newOrder.setGoods(order.getGoods());
		session.save(newOrder);
		session.getTransaction().commit();
		
	}
	
	public void saveSoldItem(SoldItem soldItem){
		session.beginTransaction();
		SoldItem newItem = new SoldItem();
		newItem.setStockItem(soldItem.getStockItem());
		newItem.setOrder(soldItem.getOrder());
		newItem.setQuantity(soldItem.getQuantity());
		session.save(newItem);
		session.getTransaction().commit();
		
	}
	
	public List<StockItem> getStockItems() {
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}

	public List<Order> getOrders() {
		//return Collections.checkedList(session.createQuery("from Order").list(), Order.class);
		List<Order> result = session.createQuery("from Order").list();
		return result;
	}

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}

}