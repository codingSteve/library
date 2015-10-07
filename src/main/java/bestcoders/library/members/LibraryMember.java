package bestcoders.library.members;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bestcoders.library.items.ItemType;

//TODO: should only be available for instantiations by the Library class  
public class LibraryMember {
    private static Logger logger = LoggerFactory.getLogger(LibraryMember.class);

    final int memberNumber;
    final String memberName;
    final Collection<ItemType> permittedItemTypes;

    public LibraryMember(final int memberNumber, final String memberName,
	    final Collection<ItemType> permittedItemTypes) {
	this.memberNumber = memberNumber;
	this.memberName = memberName;
	this.permittedItemTypes = permittedItemTypes;
    }

    @Override
    public boolean equals(final Object o) {

	logger.trace("About to test o == {} for equality against this == {} ", o, this);
	final boolean op;
	if (o instanceof LibraryMember) {
	    op = ((LibraryMember) o).getMemberNumber() == getMemberNumber();
	} else {
	    op = false;
	}
	return op;
    }

    public String getMemberName() {
	return memberName;
    }

    public int getMemberNumber() {
	return memberNumber;
    }

    public Collection<ItemType> getPermittedItemTypes() {
	return permittedItemTypes;
    }

    @Override
    public String toString() {
	// javac optimises out the string concatenations to StringBuilder calls.
	// > javap -c LibraryMember.class 
	// ... 
	// 81        9: aload_0
	// 82       10: getfield      #33                 // Field memberNumber:I
	// 83       13: invokevirtual #74                 // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
	// 84       16: ldc           #78                 // String , memberName : \"
	// 85       18: invokevirtual #80                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
	return "{memberNumber : " + memberNumber + ", memberName : \"" + memberName + "\"}";

    }
}
