package com.gildedrose.item;

public class BackstageItem extends StandardItem {
	
	public static final int BACKSTAGE_PASSES_DEADLINE_1 = 10;
	public static final int BACKSTAGE_PASSES_DEADLINE_2 = 5;

	public BackstageItem(String name, int sellIn, int quality) {
		
		super(name, sellIn, quality);
	}

	@Override
	public StandardItem updateQuality() {

		int sellIn = getSellIn() - 1;
		int quality = getQuality();
		if (sellIn > BACKSTAGE_PASSES_DEADLINE_1) {
			quality += 1;
		} else if (sellIn > BACKSTAGE_PASSES_DEADLINE_2) {
			quality += 2;
		} else if (sellIn >= 0) {
			quality += 3;
		} else {
			quality = StandardItem.MIN_QUALITY;
		}
		quality = checkMinMaxQuality(quality);
		return new BackstageItem(getName(), sellIn, quality);
	}
}
