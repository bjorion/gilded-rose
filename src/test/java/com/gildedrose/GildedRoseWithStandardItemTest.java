package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.gildedrose.item.AgedBrieItem;
import com.gildedrose.item.BackstageItem;
import com.gildedrose.item.ConjuredItem;
import com.gildedrose.item.StandardItem;
import com.gildedrose.item.SulfurasItem;

public class GildedRoseWithStandardItemTest {

	private static final String STD_NAME = "standard";
	private static final String AGED_BRIE = "Aged Brie";
	private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
	private static final String CONJURED_NAME = "Conjured Mana Cake";
	private static final String BACKSTAGE_PASSES_NAME = "Backstage passes to a TAFKAL80ETC concert";

	@Test
	void testUpdateQuality_standardItem_qualityDecreased() {

		final int sellIn = 10;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new StandardItem(STD_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(STD_NAME, app.items().get(0).getName());
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 1, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality__standardItems_qualityDecreated() {

		final int value0 = 10;
		final int value1 = 20;

		StandardItem[] items = new StandardItem[] { new StandardItem(STD_NAME, value0, value0),
				new StandardItem(STD_NAME, value1, value1) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(STD_NAME, app.items().get(0).getName());
		assertEquals(value0 - 1, app.items().get(0).getSellIn());
		assertEquals(value0 - 1, app.items().get(0).getQuality());

		assertEquals(STD_NAME, app.items().get(1).getName());
		assertEquals(value1 - 1, app.items().get(1).getSellIn());
		assertEquals(value1 - 1, app.items().get(1).getQuality());
	}

	@Test
	void testUpdateQuality_standardItem_qualityDecreasedGTEmin() {

		final int sellIn = 10;
		final int quality = 1;

		StandardItem[] items = new StandardItem[] { new StandardItem(STD_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 1, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(StandardItem.MIN_QUALITY, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_standardItem_qualityDecreasedTwiceAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new StandardItem(STD_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		// sellIn falls to zero
		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 1, app.items().get(0).getQuality());

		// quality decreases twice as fast
		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(quality - 3, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(sellIn - 3, app.items().get(0).getSellIn());
		assertEquals(quality - 5, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_agedBrie_qualityIncreased() {

		final int sellIn = 10;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new AgedBrieItem(AGED_BRIE, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality + 1, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_agedBrie_qualityIncreasedLTEmax() {

		final int sellIn = 1;
		final int quality = AgedBrieItem.MAX_QUALITY - 1;

		AgedBrieItem agedBrieItem = new AgedBrieItem(AGED_BRIE, sellIn, quality);
		StandardItem[] items = new StandardItem[] { agedBrieItem };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(AgedBrieItem.MAX_QUALITY, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(AgedBrieItem.MAX_QUALITY, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_sulfuras_qualityConstant() {

		final int sellIn = 0;
		final int quality = SulfurasItem.QUALITY;

		StandardItem[] items = new StandardItem[] { new SulfurasItem(SULFURAS_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(sellIn, app.items().get(0).getSellIn());
		assertEquals(SulfurasItem.QUALITY, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(sellIn, app.items().get(0).getSellIn());
		assertEquals(SulfurasItem.QUALITY, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityIncreasedTwiceAfterFirstDeadline() {

		// Note: there is probably a bug in the old code
		final int sellIn = BackstageItem.BACKSTAGE_PASSES_DEADLINE_1 + 2;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new BackstageItem(BACKSTAGE_PASSES_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		// normal period
		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality + 1, app.items().get(0).getQuality());

		// "double" period
		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(quality + 3, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityIncreasedThriceAfterSecondDeadline() {

		// Note: there is probably a bug in the old code
		final int sellIn = BackstageItem.BACKSTAGE_PASSES_DEADLINE_2 + 2;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new BackstageItem(BACKSTAGE_PASSES_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		// "double" period
		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality + 2, app.items().get(0).getQuality());

		// "triple" period
		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(quality + 5, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityZeroAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new BackstageItem(BACKSTAGE_PASSES_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(0, app.items().get(0).getSellIn());
		assertNotEquals(0, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(-1, app.items().get(0).getSellIn());
		assertEquals(0, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_conjured_qualityDecrease() {

		final int sellIn = 10;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new ConjuredItem(CONJURED_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 2, app.items().get(0).getQuality());

		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(quality - 4, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_conjured_qualityDecreaseGTEmin() {

		final int sellIn = 1;
		final int quality = 2;

		StandardItem[] items = new StandardItem[] { new ConjuredItem(CONJURED_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		// quality decrease
		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 2, app.items().get(0).getQuality());

		// min quality reached
		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(StandardItem.MIN_QUALITY, app.items().get(0).getQuality());
	}

	@Test
	void testUpdateQuality_conjured_qualityDecreasedTwiceAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		StandardItem[] items = new StandardItem[] { new ConjuredItem(CONJURED_NAME, sellIn, quality) };
		GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

		// quality decrease
		app.updateQuality();
		assertEquals(sellIn - 1, app.items().get(0).getSellIn());
		assertEquals(quality - 2, app.items().get(0).getQuality());

		// quality decrease twice faster
		app.updateQuality();
		assertEquals(sellIn - 2, app.items().get(0).getSellIn());
		assertEquals(quality - 6, app.items().get(0).getQuality());
	}
}
