package ee.ut.math.tvt.bartersmart.ui.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.bartersmart.domain.controller.SalesDomainController;
import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.domain.exception.DuplicateStockItemNameException;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);
	private SalesDomainController domainController;

	public StockTableModel(SalesDomainController domainController) {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
		this.domainController = domainController;
	}
	
	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 * @throws DuplicateStockItemNameException 
	 */
	public void addItem(final StockItem stockItem) throws DuplicateStockItemNameException {
		
			try {
				StockItem item = getItemById(stockItem.getId());
				item.setQuantity(item.getQuantity() + stockItem.getQuantity());
				log.debug("Found existing item " + stockItem.getName()
						+ " increased quantity by " + stockItem.getQuantity());
			}
			catch (NoSuchElementException e) {
				if (isUniqueName(stockItem.getName())){
					StockItem newItem = domainController.databaseStockItemConvert(stockItem);
					domainController.saveNewStockItem(newItem);
					rows.add(newItem);
					log.debug("Added " + stockItem.getName()
							+ " quantity of " + stockItem.getQuantity());
				}
				else{
					log.debug("Duplicate names not allowed!");
					throw new DuplicateStockItemNameException();
				}
			}
		

		fireTableDataChanged();
	}

	public void updateWarehouse(List<SoldItem> soldGoods) {
		try {
			for (SoldItem soldItem : soldGoods){
				StockItem stockItem = soldItem.getStockItem();
				if (hasEnoughInStock(stockItem, soldItem)){
					stockItem.setQuantity(stockItem.getQuantity() - soldItem.getQuantity());
					log.debug("Found existing item " + soldItem.getName()
							+ " decrease quantity by " + soldItem.getQuantity());
				}
				else log.debug("Not enough in stock!");
			}
		}
		catch (NoSuchElementException e) {
			log.debug("Item not found");
		}
		fireTableDataChanged();
	}
	
	public boolean hasEnoughInStock(StockItem stockItem, SoldItem soldItem) {
		// TODO Auto-generated method stub
		if (stockItem.getQuantity()-soldItem.getQuantity()>=0) return true;
		return false;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    public boolean isUniqueName(String name) throws DuplicateStockItemNameException {
        for (final StockItem item : rows) {
            if (item.getName().equals(name)){
            	return false;
            }
        }
        return true;        
    }

}
