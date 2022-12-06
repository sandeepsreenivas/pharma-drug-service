package com.pharmabms.service;

import com.pharmabms.dto.CreateStock;
import com.pharmabms.dto.DrugDetails;
import com.pharmabms.dto.StockCreatedResponse;
import com.pharmabms.entity.UserInventory;

public interface UserInventoryService {

    StockCreatedResponse addStock(CreateStock stock);
    UserInventory getUserInventoryDetails(String userId);
    StockCreatedResponse addStockToNewUser(CreateStock stock);
    StockCreatedResponse updateStockToOldUser(CreateStock stock);
    int isDrugDetailsPresent(String userId, DrugDetails drugDetails);

}
