package bestcoders.library;

import java.util.Collection;

import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

/**
 * Interface covering readonly operations for a given member e.g.
 *
 * which items can I borrow?
 *
 * which items are overdue?
 * 
 * @author stevenpowell
 *
 */
public interface MemberItemReport {

    public Collection<Item> apply(LibraryMember m);
}
