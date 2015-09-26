package bestcoders.library.items;

public class Item {

    final int id;
    final String title;
    final ItemType type;

    public Item(final int id, final String title, final ItemType type) {
	super();
	this.id = id;
	this.title = title;
	this.type = type;
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
