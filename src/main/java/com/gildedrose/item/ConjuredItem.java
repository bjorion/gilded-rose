package com.gildedrose.item;

public class ConjuredItem extends StandardItem {

	public ConjuredItem(String name, int sellIn, int quality) {
		
		super(name, sellIn, quality);
	}

	@Override
	public StandardItem updateQuality() {
		
		int sellIn = getSellIn() - 1;
		int delta = (sellIn >= 0) ? 2 : 4;
		int quality = checkMinMaxQuality(getQuality() - delta);
		return new ConjuredItem(getName(), sellIn, quality);
	}
}
