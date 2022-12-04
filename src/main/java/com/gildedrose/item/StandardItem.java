package com.gildedrose.item;

import com.gildedrose.Item;

public class StandardItem {

	public static final int MIN_QUALITY = 0;
	public static final int MAX_QUALITY = 50;
	private final Item item;

	public StandardItem(String name, int sellIn, int quality) {

		this.item = new Item(name, sellIn, quality);
	}

	public String getName() {

		return item.name;
	}

	public int getSellIn() {

		return item.sellIn;
	}

	public int getQuality() {

		return item.quality;
	}

	public StandardItem updateQuality() {

		int sellIn = getSellIn() - 1;
		int delta = (sellIn >= 0) ? 1 : 2;
		int quality = checkMinMaxQuality(getQuality() - delta);
		return new StandardItem(getName(), sellIn, quality);
	}

	protected int checkMinMaxQuality(int quality) {

		quality = Math.min(quality, MAX_QUALITY);
		quality = Math.max(quality, MIN_QUALITY);
		return quality;
	}

	@Override
	public String toString() {

		return item.toString();
	}
}
