package com.gildedrose.item;

public class SulfurasItem extends StandardItem {

	public static final int QUALITY = 80;

	public SulfurasItem(String name, int sellIn, int quality) {

		// we may to check whether quality = 80
		super(name, sellIn, quality);
	}

	@Override
	public StandardItem updateQuality() {

		return this;
	}

}
