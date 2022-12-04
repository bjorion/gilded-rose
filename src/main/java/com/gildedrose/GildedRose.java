package com.gildedrose;

public class GildedRose {

	public static final int MIN_QUALITY = 0;
	public static final int MAX_QUALITY = 50;
	public static final int SULFURAS_QUALITY = 80;
	public static final int BACKSTAGE_PASSES_DEADLINE_1 = 10;
	public static final int BACKSTAGE_PASSES_DEADLINE_2 = 5;

	public static final String AGED_BRIE = "Aged Brie";
	public static final String SULFURAS = "Sulfuras";
	public static final String BACKSTAGE_PASSES = "Backstage passes";
	public static final String CONJURED = "Conjured";

	private Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public Item getItem(int index) {
		return items[index];
	}

	public void updateQualityRefactored() {

		for (int i = 0; i < items.length; i++) {
			Item item = getItem(i);
			String name = item.name;
			if (name.startsWith(AGED_BRIE)) {
				item.sellIn--;
				item.quality = computeDailyQualityAgedBrie(item);
			} else if (name.startsWith(SULFURAS)) {
				// sellIn constant
				item.quality = computeDailyQualitySulfuras(item);
			} else if (name.startsWith(BACKSTAGE_PASSES)) {
				item.sellIn--;
				item.quality = computeDailyQualityBackstage(item);
			} else if (name.startsWith(CONJURED)) {
				item.sellIn--;
				item.quality = computeDailyQualityConjured(item);
			} else {
				item.sellIn--;
				item.quality = computeDailyQualityDefault(item);
			}
		}
	}

	private int computeDailyQualityDefault(Item item) {

		int delta = (item.sellIn >= 0) ? 1 : 2;
		int quality = item.quality - delta;
		return checkMinMaxQuality(quality);
	}

	private int computeDailyQualityAgedBrie(Item item) {

		int quality = item.quality + 1;
		return checkMinMaxQuality(quality);
	}

	private int computeDailyQualitySulfuras(Item item) {

		// quality always constant
		return item.quality;
	}

	private int computeDailyQualityBackstage(Item item) {

		int sellIn = item.sellIn;
		int quality = item.quality;
		if (sellIn > BACKSTAGE_PASSES_DEADLINE_1) {
			quality += 1;
		} else if (sellIn > BACKSTAGE_PASSES_DEADLINE_2) {
			quality += 2;
		} else if (sellIn >= 0) {
			quality += 3;
		} else {
			quality = 0;
		}
		return checkMinMaxQuality(quality);
	}

	private int computeDailyQualityConjured(Item item) {

		int delta = (item.sellIn >= 0) ? 2 : 4;
		int quality = item.quality - delta;
		return checkMinMaxQuality(quality);
	}

	private int checkMinMaxQuality(int quality) {

		if (quality < MIN_QUALITY) {
			quality = MIN_QUALITY;
		}
		if (quality > MAX_QUALITY) {
			quality = MAX_QUALITY;
		}
		return quality;
	}

	@Deprecated
	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {
			if (!items[i].name.equals("Aged Brie")
					&& !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
				if (items[i].quality > 0) {
					if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
						items[i].quality = items[i].quality - 1;
					}
				}
			} else {
				if (items[i].quality < 50) {
					items[i].quality = items[i].quality + 1;

					if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
						if (items[i].sellIn < 11) {
							if (items[i].quality < 50) {
								items[i].quality = items[i].quality + 1;
							}
						}

						if (items[i].sellIn < 6) {
							if (items[i].quality < 50) {
								items[i].quality = items[i].quality + 1;
							}
						}
					}
				}
			}

			if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
				items[i].sellIn = items[i].sellIn - 1;
			}

			if (items[i].sellIn < 0) {
				if (!items[i].name.equals("Aged Brie")) {
					if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
						if (items[i].quality > 0) {
							if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
								items[i].quality = items[i].quality - 1;
							}
						}
					} else {
						items[i].quality = items[i].quality - items[i].quality;
					}
				} else {
					if (items[i].quality < 50) {
						items[i].quality = items[i].quality + 1;
					}
				}
			}
		}
	}
}