package bestcoders.library;

public class LibraryMember extends Member {

    public final int memberNumber;
    public final String memberName;

    public LibraryMember(final int memberNumber, final String memberName) {
	super();
	this.memberNumber = memberNumber;
	this.memberName = memberName;
    }

    @Override
    public String toString() {
	return "{memberNumber : " + memberNumber + ", memberName : \"" + memberName + "\"}";
    }
}
