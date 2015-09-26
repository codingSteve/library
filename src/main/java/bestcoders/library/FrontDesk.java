package bestcoders.library;

public class FrontDesk {

    public Item findItemById(final int i) {
	return new Item() {
	    @Override
	    public int getId() {
		return 1;
	    }

	    @Override
	    public String getTitle() {
		return "The Art Of Computer Programming Volumes 1-6";
	    }

	    @Override
	    public ItemType getType() {
		return ItemType.BOOK;
	    }
	};

    }

    public boolean requestCheckout(final Member m, final Item i) {
	final boolean isAvailable = (0 < Library.getStockAvailable(i));
	final boolean checkoutSuccessful = isAvailable && Library.checkout(i, m);
	return checkoutSuccessful;
    }

}
