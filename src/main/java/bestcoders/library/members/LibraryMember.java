package bestcoders.library.members;

import java.util.Collection;

import bestcoders.library.items.ItemType;

public class LibraryMember {

    public final int memberNumber;
    public final String memberName;
    public final Collection<ItemType> permittedItemTypes;

    public LibraryMember(final int memberNumber, final String memberName,
	    final Collection<ItemType> permittedItemTypes) {
	this.memberNumber = memberNumber;
	this.memberName = memberName;
	this.permittedItemTypes = permittedItemTypes;
    }

    @Override
    public String toString() {
	return "{memberNumber : " + memberNumber + ", memberName : \"" + memberName + "\"}";
    }
}
