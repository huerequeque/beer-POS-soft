package ee.ut.math.tvt.bartersmart.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @Column(name = "sale_id")
//    private Long saleId;
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = "STOCKITEM_ID")
    private StockItem stockItem;
    
	@ManyToOne
    @JoinColumn(name = "SALE_ID", nullable = false)
    private Order order;
    
	@Column(name = "name")
    private String name;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "ITEMPRICE")
    private double price;
    
    public SoldItem(StockItem stockItem, int quantity) {
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        
    }
    
    
    public Long getId() {
        return id;
    }

//    @Column(name = "sale_id")
//    public Long getStockItemId() {
//        return stockItem.getId();
//    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
}
