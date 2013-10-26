package ee.ut.math.tvt.bartersmart.domain.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ee.ut.math.tvt.bartersmart.ui.model.PurchaseInfoTableModel;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
public class Order implements Cloneable, DisplayableItem {

    private Calendar time;
    private String dateString;
    private String timeString;
    private double price;
    private List<SoldItem> goods;
    private long id;
    
    public Order(long id, Calendar time, List<SoldItem> goods) {
        this.id = id;
    	this.time = time;
        this.goods = goods;
        this.price = calculatePrice();
        this.dateString =new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());
        this.timeString =new SimpleDateFormat("HH:mm:ss").format(time.getTime()); 
    }
    
    public String getDateString() {
		return dateString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public void setPrice(double price) {
        this.price = price;
    }

    public double calculatePrice() {
    	double sum = 0;
    	for (SoldItem item : goods){
    		sum+=item.getSum();
    	}
        return sum;
    }

    public double getPrice() {
		return price;
	}
    
    public List<SoldItem> getGoods() {
        return goods;
    }

    public void setGoods(List<SoldItem> goods) {
        this.goods = goods;
    }

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
    
}
