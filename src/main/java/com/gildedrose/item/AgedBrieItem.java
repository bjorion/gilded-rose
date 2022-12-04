package com.gildedrose.item;

public class AgedBrieItem extends StandardItem {

	public AgedBrieItem(String name, int sellIn, int quality) {
		
		super(name, sellIn, quality);
	}

	@Override
	public StandardItem updateQuality() {

		int sellIn = getSellIn() - 1;
		int delta = 1;
		int quality = checkMinMaxQuality(getQuality() + delta);
		return new AgedBrieItem(getName(), sellIn, quality);
	}
}
