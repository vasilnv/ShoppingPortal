package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	/**
	 * Returns the date for which the statistic is collected.
	 */
	public LocalDate getDate() {
		return this.dateOfStatistic;

	}

	/**
	 * Returns the lowest total price from the offers for this product for the
	 * specific date.
	 */
	public double getLowestPrice() {
		return this.lowestPrice;
	}

	/**
	 * Return the average total price from the offers for this product for the
	 * specific date.
	 */
	public double getAveragePrice() {
		return this.averagePrice;
	}
}
