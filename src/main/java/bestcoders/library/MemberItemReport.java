package bestcoders.library;

import java.util.Collection;

import bestcoders.library.items.Item;
import bestcoders.library.members.LibraryMember;

public interface MemberItemReport {

    public Collection<Item> apply(LibraryMember m);
}
