package com.techrocking.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techrocking.inventory.kafka.source.ItemFethcedEventSource;
import com.techrocking.inventory.kafka.source.itemOutOfStockEventSource;

@Service
public class InventoryService {
	@Autowired
	private ItemFethcedEventSource itemFethcedEventSource;
	
	@Autowired
	private itemOutOfStockEventSource itemOutOfStockEventSource;
	
	public void fetchItem(Long orderId) {
		//inventory service will call order-service to getitemid for orderid
		
		Long itemId = getItemFromOrderService(orderId);
		Boolean isInStock = isItemExistsInItemDatabase(itemId);
		
		if(isInStock) {
			System.out.println("inventory-service: InStock we have this item:" + itemId);			
			itemFethcedEventSource.publishItemFetchedEvent(orderId, itemId);
		}
		else {
			System.out.println("inventory-service: InStock we dont have this item:" + itemId);
			itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemId);					
		}
			
	}
	
	public void compensateItem(Long orderId) {
		Long itemId = getItemFromOrderService(orderId);// itemId is accessed via orderId
		
		itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemId);
	}
    public Long getItemFromOrderService(Long orderId) {
    	//Make a rest call to order-service to find corr item
    	Long itemId = 456L;
    	
    	return itemId;
    }
    
    private boolean isItemExistsInItemDatabase(Long itemId) {
		return true;
	}
}
