package com.gildedrose;

import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES_DEADLINE_1;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES_DEADLINE_2;
import static com.gildedrose.GildedRose.CONJURED;
import static com.gildedrose.GildedRose.MAX_QUALITY;
import static com.gildedrose.GildedRose.MIN_QUALITY;
import static com.gildedrose.GildedRose.SULFURAS;
import static com.gildedrose.GildedRose.SULFURAS_QUALITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class GildedRoseTest {

	private static final String STD_NAME = "standard";
	private static final String SULFURAS_SUBTYPE = SULFURAS + ", Hand of Ragnaros";
	private static final String CONJURED_SUBTYPE = CONJURED + " Mana Cake";
	private static final String BACKSTAGE_PASSES_SUBTYPE = BACKSTAGE_PASSES + " to a TAFKAL80ETC concert";

	@Test
	void testUpdateQuality_standardItem_qualityDecreased() {

		final int sellIn = 10;
		final int quality = 10;

		Item[] items = new Item[] { new Item(STD_NAME, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(STD_NAME, app.getItem(0).name);
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 1, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality__standardItems_qualityDecreated() {

		final int value0 = 10;
		final int value1 = 20;

		Item[] items = new Item[] { new Item(STD_NAME, value0, value0), new Item(STD_NAME, value1, value1) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(STD_NAME, app.getItem(0).name);
		assertEquals(value0 - 1, app.getItem(0).sellIn);
		assertEquals(value0 - 1, app.getItem(0).quality);

		assertEquals(STD_NAME, app.getItem(1).name);
		assertEquals(value1 - 1, app.getItem(1).sellIn);
		assertEquals(value1 - 1, app.getItem(1).quality);
	}

	@Test
	void testUpdateQuality_standardItem_qualityDecreasedGTEmin() {

		final int sellIn = 10;
		final int quality = 1;

		Item[] items = new Item[] { new Item(STD_NAME, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 1, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(MIN_QUALITY, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_standardItem_qualityDecreasedTwiceAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		Item[] items = new Item[] { new Item(STD_NAME, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		// quality falls to zero
		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 1, app.getItem(0).quality);

		// quality decreases twice as fast
		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(quality - 3, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(sellIn - 3, app.getItem(0).sellIn);
		assertEquals(quality - 5, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_agedBrie_qualityIncreased() {

		final int sellIn = 10;
		final int quality = 10;

		Item[] items = new Item[] { new Item(AGED_BRIE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality + 1, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_agedBrie_qualityIncreasedLTEmax() {

		final int sellIn = 1;
		final int quality = MAX_QUALITY - 1;

		Item[] items = new Item[] { new Item(AGED_BRIE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(MAX_QUALITY, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(MAX_QUALITY, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_sulfuras_qualityConstant() {

		final int sellIn = 0;
		final int quality = SULFURAS_QUALITY;

		Item[] items = new Item[] { new Item(SULFURAS_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(sellIn, app.getItem(0).sellIn);
		assertEquals(SULFURAS_QUALITY, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(sellIn, app.getItem(0).sellIn);
		assertEquals(SULFURAS_QUALITY, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityIncreasedTwiceAfterFirstDeadline() {

		// Note: there is probably a bug in the old code
		final int sellIn = BACKSTAGE_PASSES_DEADLINE_1 + 2;
		final int quality = 10;

		Item[] items = new Item[] { new Item(BACKSTAGE_PASSES_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		// normal period
		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality + 1, app.getItem(0).quality);

		// "double" period
		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(quality + 3, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityIncreasedThriceAfterSecondDeadline() {

		// Note: there is probably a bug in the old code
		final int sellIn = BACKSTAGE_PASSES_DEADLINE_2 + 2;
		final int quality = 10;

		Item[] items = new Item[] { new Item(BACKSTAGE_PASSES_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		// "double" period
		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality + 2, app.getItem(0).quality);

		// "triple" period
		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(quality + 5, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_backstagePasses_qualityZeroAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		Item[] items = new Item[] { new Item(BACKSTAGE_PASSES_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(0, app.getItem(0).sellIn);
		assertNotEquals(0, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(-1, app.getItem(0).sellIn);
		assertEquals(0, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_conjured_qualityDecrease() {

		final int sellIn = 10;
		final int quality = 10;

		Item[] items = new Item[] { new Item(CONJURED_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 2, app.getItem(0).quality);

		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(quality - 4, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_conjured_qualityDecreaseGTEmin() {

		final int sellIn = 1;
		final int quality = 2;

		Item[] items = new Item[] { new Item(CONJURED_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		// quality decrease
		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 2, app.getItem(0).quality);

		// min quality reached
		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(MIN_QUALITY, app.getItem(0).quality);
	}

	@Test
	void testUpdateQuality_conjured_qualityDecreasedTwiceAfterPeremption() {

		final int sellIn = 1;
		final int quality = 10;

		Item[] items = new Item[] { new Item(CONJURED_SUBTYPE, sellIn, quality) };
		GildedRose app = new GildedRose(items);

		// quality decrease
		app.updateQualityRefactored();
		assertEquals(sellIn - 1, app.getItem(0).sellIn);
		assertEquals(quality - 2, app.getItem(0).quality);

		// quality decrease twice faster
		app.updateQualityRefactored();
		assertEquals(sellIn - 2, app.getItem(0).sellIn);
		assertEquals(quality - 6, app.getItem(0).quality);
	}
}
