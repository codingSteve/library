package bestcoders.library;

import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;


/**
 * Base definition of behaviour 
 * for services supplied by the 
 * Library for members changing the 
 * state of the inventory. 
 */
public interface InventoryService {

    public boolean apply(LibraryMember m, Item i);
}
