package bg.sofia.uni.fmi.mjt.shopping.portal;


import java.time.LocalDate;
import java.util.Collection;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

public class PriceStatistic {
	LocalDate dateOfStatistic;
	double lowestPrice;
	double averagePrice;

	public PriceStatistic(Collection<Offer> offers) {
		double total = Double.MAX_VALUE;
		for (Offer off : offers) {
			if (off.getTotalPrice() < total) {
				total = off.getTotalPrice();
			}
		}
		this.lowestPrice = total;
		
		double tot = 0;
		for (Offer off : offers) {
			tot += off.getTotalPrice();
		}
		
		this.averagePrice = tot / offers.size();
		this.dateOfStatistic = offers.iterator().next().getDate();

	}

	public LocalDate getDate() {
		return this.dateOfStatistic;

	}

	public double getLowestPrice() {
		return this.lowestPrice;
	}

	public double getAveragePrice() {
		return this.averagePrice;
	}
}
