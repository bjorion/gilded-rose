package com.gildedrose;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gildedrose.item.StandardItem;

public class GildedRoseWithStandardItem {

	private List<StandardItem> items;

	public GildedRoseWithStandardItem(StandardItem[] items) {

		this.items = Arrays.asList(items);
	}

	public List<StandardItem> items() {

		return items;
	}

	public void updateQuality() {

		items = items.stream().map(e -> e.updateQuality()).collect(Collectors.toUnmodifiableList());
	}

}
