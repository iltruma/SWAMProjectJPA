package it.unifi.ing.swam.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class LoadTest {
	
	private Load l;
	private Float itemWeight = 10f;
	private Float itemVolume = 20f;
	
	@Before
	public void setUp() {
		l = new Load();
	}
	
	@Test
	public void testAddItem(){
		for(int i = 0; i < 10; i++){
			Item item = new Item(UUID.randomUUID().toString());
			item.setWeigth(itemWeight);
			item.setVolume(itemVolume);
			
			l.addItem(item);
			Float totalWeight = (i+1)*itemWeight;
			Float totalVolume = (i+1)*itemVolume;

			
			assertEquals(l.getTotalWeight(), totalWeight);
			assertEquals(l.getTotalVolume(), totalVolume);
		
		}
	}
	
	@Test
	public void testRemoveItem(){
		List<Item> itemList = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Item item = new Item(UUID.randomUUID().toString());
			item.setWeigth(itemWeight);
			item.setVolume(itemWeight);
	
			l.addItem(item);
			itemList.add(item);	
		}
		
		int nItemsRemoved = 0;
		for(Item i: itemList){	
			l.removeItem(i);
			nItemsRemoved++;
			assertEquals(l.getItems().size(), itemList.size() - nItemsRemoved);
		}
	}


}
