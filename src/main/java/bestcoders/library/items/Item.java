package bestcoders.library.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO: add identifiers collection
public class Item {
    private static Logger logger = LoggerFactory.getLogger(Item.class);

    final int id;
    final String title;
    final ItemType type;

    public Item(final int id, final String title, final ItemType type) {
	super();
	this.id = id;
	this.title = title;
	this.type = type;
    }

    @Override
    public boolean equals(final Object o) {

	logger.trace("About to test o == {} for equality against this == {} ", o, this);
	final boolean op;
	if (o instanceof Item) {
	    op = ((Item) o).getId() == getId();
	} else {
	    op = false;
	}
	return op;
    }

    public int getId() {
	return id;
    }

    public String getTitle() {
	return title;
    }

    public ItemType getType() {
	return type;
    }

    @Override
    public String toString() {
	return "{" + "id : " + getId() + ", " + "title : \"" + getTitle() + "\", " + "type : " + getType() + "}";

    }

}
