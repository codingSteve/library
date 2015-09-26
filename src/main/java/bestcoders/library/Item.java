package bestcoders.library;

public abstract class Item {

    abstract int getId();

    abstract String getTitle();

    abstract ItemType getType();

    @Override
    public String toString() {
	return "{" + "id : " + getId() + ", " + "title : \"" + getTitle() + "\", " + "type : " + getType() + "}";

    }

}
