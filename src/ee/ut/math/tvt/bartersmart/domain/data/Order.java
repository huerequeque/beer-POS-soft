package ee.ut.math.tvt.bartersmart.domain.data;

import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name = "ORDER")
public class Order implements Cloneable, DisplayableItem {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SALE_DATE")
    private Calendar calendar;
	
	@Transient
    private String dateString;
    
	@Transient
	private String timeString;
    
	@Transient
	private double price;
	
    @OneToMany(mappedBy = "order")
    private List<SoldItem> goods;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SALE_TIME")
    private Date time;
    
    public Order() {
    }
    
    public Order(long id, Calendar time, List<SoldItem> goods) {
        this.id = id;
    	this.setCalendar(time);
        this.time=(Date) time.getTime();
    	this.goods = goods;
        this.price = calculatePrice();
        this.dateString =new SimpleDateFormat("yyyy-MM-dd").format(time.getTime());
        this.timeString =new SimpleDateFormat("HH:mm:ss").format(time.getTime()); 
    }
    
    public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		this.time = (Date) calendar.getTime();
	}

	public Date getTime() {
		return time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDateString() {
		return dateString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTime(Calendar time) {
		this.calendar = time;
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
