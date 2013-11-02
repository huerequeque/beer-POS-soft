package ee.ut.math.tvt.bartersmart.domain.controller;

import java.util.List;

import ee.ut.math.tvt.bartersmart.domain.data.SoldItem;
import ee.ut.math.tvt.bartersmart.domain.data.StockItem;
import ee.ut.math.tvt.bartersmart.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.bartersmart.ui.model.SalesSystemModel;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {
	
	public double getCurrentPurchasePrice();

	public void setCurrentPurchasePrice(double currentPurchasePrice);

    /**
     * Load the current state of the warehouse.
     * 
     * @return List of ${link
     *         ee.ut.math.tvt.bartersmart.domain.data.StockItem}s.
     */
    public List<StockItem> loadWarehouseState();

    // business processes
    /**
     * Initiate new business transaction - purchase of the goods.
     * 
     * @throws VerificationFailedException
     */
    public void startNewPurchase() throws VerificationFailedException;

    /**
     * Rollback business transaction - purchase of goods.
     * 
     * @throws VerificationFailedException
     */
    public void cancelCurrentPurchase() throws VerificationFailedException;

    /**
     * Commit business transaction - purchsae of goods.
     * 
     * @param goods
     *            Goods that the buyer has chosen to buy.
     * @throws VerificationFailedException
     */
    public void submitCurrentPurchase(List<SoldItem> goods)
            throws VerificationFailedException;

	public long getLastId();
	
	public void finalizePurchase();
	
	public void endSession();
    
}
