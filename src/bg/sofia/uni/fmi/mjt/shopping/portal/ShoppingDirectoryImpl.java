package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
	private static final int DAYS = 30;
	private Map<String, Collection<Offer>> offers = new HashMap<>();

	public List<Offer> addOffersToList(String productName) {
		List<Offer> offersList = new LinkedList<>();
		for (Offer offer : offers.get(productName.toLowerCase())) {
			if (within30days(offer.getDate())) {
				offersList.add(offer);
			}
		}
		return offersList;
	}

	@Override
	public Collection<Offer> findAllOffers(String productName) {
		if (productName == null) {
			throw new IllegalArgumentException();
		}
		if (!offers.containsKey(productName.toLowerCase())) {
			throw new ProductNotFoundException("no product with that name");
		}
		List<Offer> offersList = addOffersToList(productName);
		offersList.sort(new Comparator<Offer>() {

			@Override
			public int compare(Offer off1, Offer off2) {
				return Double.compare(off1.getTotalPrice(), off2.getTotalPrice());
			}
		});
		return offersList;
	}

	public void checkValidProductName(String productName) {
		if (productName == null) {
			throw new IllegalArgumentException();
		}
		if (!offers.containsKey(productName.toLowerCase())) {
			throw new ProductNotFoundException("no product with that name");
		}
	}

	public void checkIfOfferIsFound(Offer minOffer) {
		if (minOffer == null) {
			throw new NoOfferFoundException("no offer found");
		}
	}

	@Override
	public Offer findBestOffer(String productName) {
		checkValidProductName(productName);
		Collection<Offer> offs = offers.get(productName.toLowerCase());
		Offer minOffer = null;
		for (Offer off : offs) {
			if (within30days(off.getDate())) {
				if (minOffer == null || off.getTotalPrice() < minOffer.getTotalPrice()) {
					minOffer = off;
				}
			}
		}
		return minOffer;
	}

	public Map<LocalDate, Collection<Offer>> splitOffersByDate(String productName) {
		Map<LocalDate, Collection<Offer>> offersAtDate = new HashMap<>();
		Collection<Offer> m_offers = offers.get(productName.toLowerCase());
		Collection<Offer> lst = new LinkedList<>();

		for (Offer offer : m_offers) {
			if (!offersAtDate.containsKey(offer.getDate())) {
				offersAtDate.put(offer.getDate(), new LinkedList<>());
			}
			lst = offersAtDate.get(offer.getDate());
			lst.add(offer);
		}
		return offersAtDate;
	}
	
	@Override
	public Collection<PriceStatistic> collectProductStatistics(String productName) {
		checkValidProductName(productName);
		Map<LocalDate, Collection<Offer>> offersAtDate = splitOffersByDate(productName);
		List<PriceStatistic> stats = new LinkedList<>();
		for (Map.Entry<LocalDate, Collection<Offer>> entry : offersAtDate.entrySet()) {
			stats.add(new PriceStatistic(entry.getValue()));
		}
		stats.sort(new Comparator<PriceStatistic>() {

			@Override
			public int compare(PriceStatistic o1, PriceStatistic o2) {
				return o2.getDate().compareTo(o1.getDate());
			}

		});

		return stats;

	}

	@Override
	public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
		checkIfOfferIsFound(offer);
		if (offers.containsKey(offer.getProductName().toLowerCase())) {
			Collection<Offer> offs = offers.get(offer.getProductName().toLowerCase());
			if (offs.contains(offer)) {
				throw new OfferAlreadySubmittedException("already submitted offer");
			}
			offs.add(offer);
		} else {
			Collection<Offer> offs = new HashSet<>();
			offs.add(offer);
			offers.put(offer.getProductName().toLowerCase(), offs);
		}
	}

	private boolean within30days(LocalDate date) {
		return LocalDate.now().minusDays(DAYS).compareTo(date) < 0;
	}

}
