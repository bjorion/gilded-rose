package com.gildedrose;

public class Item2 {
	
	private final Item item;
	
    public Item2(String name, int sellIn, int quality) {
    
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
    
    public Item2 decreaseSellIn(Item2 item2) {
    	
    	return new Item2(item2.getName(), item2.getSellIn() - 1, item2.getQuality());
    }
    
    public Item2 decreaseQuality(Item2 item2, int deltaQuality) {
    	
    	return new Item2(item2.getName(), item2.getSellIn(), item2.getQuality() - deltaQuality);
    }
    
    @Override
    public String toString() {
    	
    	return item.toString();
    }
}
