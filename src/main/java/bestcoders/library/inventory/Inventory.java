package bestcoders.library.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.items.Item;

public class Inventory {
    private static Logger logger = LoggerFactory.getLogger(Inventory.class);

    List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();

    public void addToInventory(final Item item, final int quantity) {
	logger.debug("About to add {} new {}(s)", quantity, item);
	inventoryItems.add(new InventoryItem(item, quantity));
    }

    public Collection<InventoryItem> getFullCatalogue() {
	return Collections.unmodifiableList(inventoryItems);
    }

}
