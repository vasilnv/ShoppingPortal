package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import javax.print.attribute.standard.MediaSize.Other;

public class PremiumOffer implements Offer {
	private static final int PRECISION = 2;
	private static final int PERCENT = 100;
	private String productName = "";
	private LocalDate date;
	private String description = "";
	private double price = 0;
	private double shippingPrice = 0;
	private double discount = 0.00;

	public PremiumOffer(String productName, LocalDate date, String description, 
			double price, double shippingPrice, double discount) {
		this.productName = productName;
		this.date = date;
		this.description = description;
		this.price = price;
		this.shippingPrice = shippingPrice;
		if (discount >= 0 && discount <= PERCENT) {
			this.discount = discount;
	        BigDecimal bd = new BigDecimal(this.discount).setScale(PRECISION, RoundingMode.HALF_UP);
			this.discount = bd.doubleValue();
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getProductName() {
		return this.productName;
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public double getShippingPrice() {
		return this.shippingPrice;
	}

	@Override
	public double getTotalPrice() {
		return (this.price + this.shippingPrice) * ((PERCENT - this.discount) / PERCENT);
	}

	public double getDiscount() {
		return this.discount;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, this.getTotalPrice(), productName.toLowerCase());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Offer))
			return false;
		Offer other = (Offer) obj;
		return Objects.equals(date, other.getDate())
				&& Double.doubleToLongBits(this.getTotalPrice()) == Double.doubleToLongBits(other.getTotalPrice())
				&& Objects.equals(productName.toLowerCase(), other.getProductName().toLowerCase());
	}



}
