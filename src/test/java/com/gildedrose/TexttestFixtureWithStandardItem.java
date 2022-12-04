package com.gildedrose;

import com.gildedrose.item.AgedBrieItem;
import com.gildedrose.item.BackstageItem;
import com.gildedrose.item.ConjuredItem;
import com.gildedrose.item.StandardItem;
import com.gildedrose.item.SulfurasItem;

public class TexttestFixtureWithStandardItem {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        StandardItem[] items = new StandardItem[] {
                new StandardItem("+5 Dexterity Vest", 10, 20), //
                new AgedBrieItem("Aged Brie", 2, 0), //
                new StandardItem("Elixir of the Mongoose", 5, 7), //
                new SulfurasItem("Sulfuras, Hand of Ragnaros", 0, 80), //
                new SulfurasItem("Sulfuras, Hand of Ragnaros", -1, 80),
                new BackstageItem("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new BackstageItem("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new BackstageItem("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new ConjuredItem("Conjured Mana Cake", 3, 6) };

        GildedRoseWithStandardItem app = new GildedRoseWithStandardItem(items);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            app.items().forEach(System.out::println);
            System.out.println();
            app.updateQuality();
        }
    }

}
