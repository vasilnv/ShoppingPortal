package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class OfferAlreadySubmittedException extends IllegalArgumentException {
	public OfferAlreadySubmittedException(String txt) {
		super(txt);
	}
}
