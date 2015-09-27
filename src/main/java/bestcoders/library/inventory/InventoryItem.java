package bestcoders.library.inventory;

import java.util.Date;

import bestcoders.library.items.Item;

public class InventoryItem {

    final Item item;
    final int quantity;
    final Long purchaseDate;
    Long retirementDate;

    public InventoryItem(final Item item, final int quantity) {

	this(item, quantity, new Date().getTime());
    }

    public InventoryItem(final Item item, final int quantity, final long purchaseDate) {
	this(item, quantity, purchaseDate, null);
    }

    public InventoryItem(final Item item, final int quantity, final long purchaseDate, final Long retirementDate) {
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

    public long getRetirementDate() {
	return retirementDate;
    }

}
