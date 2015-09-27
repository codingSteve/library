package bestcoders.library;

import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

public interface InventoryService {

    public boolean apply(LibraryMember m, Item i);
}
