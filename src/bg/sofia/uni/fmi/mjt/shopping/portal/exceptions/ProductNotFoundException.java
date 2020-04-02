package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class ProductNotFoundException extends IllegalArgumentException {
	public ProductNotFoundException(String txt) {
		super(txt);
	}
}
