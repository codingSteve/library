package bestcoders.library.inventory;

import bestcoders.library.BusinessDate;
import bestcoders.library.items.Item;

public class InventoryItem {

    final Item item;
    final int quantity;
    final BusinessDate purchaseDate;
    BusinessDate retirementDate;

    public InventoryItem(final Item item, final int quantity) {

	this(item, quantity, BusinessDate.getCurrentDate());
    }

    public InventoryItem(final Item item, final int quantity, final BusinessDate purchaseDate) {
	this(item, quantity, purchaseDate, null);
    }

    public InventoryItem(final Item item, final int quantity, final BusinessDate purchaseDate,
	    final BusinessDate retirementDate) {
	super();
	this.item = item;
	this.quantity = quantity;
	this.purchaseDate = purchaseDate;
	this.retirementDate = retirementDate;
    }

    public Item getItem() {
	return item;
    }

    public int getQuantity() {
	return quantity;
    }

    public BusinessDate getRetirementDate() {
	return retirementDate;
    }

}
