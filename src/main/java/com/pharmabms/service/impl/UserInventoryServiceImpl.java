package com.pharmabms.service.impl;

import com.pharmabms.dto.CreateStock;
import com.pharmabms.dto.Drug;
import com.pharmabms.dto.DrugDetails;
import com.pharmabms.dto.StockCreatedResponse;
import com.pharmabms.entity.UserInventory;
import com.pharmabms.repository.UserInventoryRepository;
import com.pharmabms.service.UserInventoryService;
import com.pharmabms.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserInventoryServiceImpl implements UserInventoryService {

    @Autowired
    private UserInventoryRepository repository;

    @Override
    public StockCreatedResponse addStock(CreateStock stock) {
        UserInventory userInventory = getUserInventoryDetails(stock.getUserId());
        if (null != userInventory) {
            return updateStockToOldUser(stock);
        }
        return addStockToNewUser(stock);
    }

    @Override
    public UserInventory getUserInventoryDetails(String userId) {
        Optional<UserInventory> userInventory = repository.findById(userId);
        return userInventory.orElse(null);
    }

    @Override
    public StockCreatedResponse addStockToNewUser(CreateStock stock) {
        UserInventory userInventory = new UserInventory();
        userInventory.setUserId(stock.getUserId());
        Map<String, List<Drug>> drugMap = new HashMap<>();
        stock.getDrugsList().forEach(drugDetails -> {
            String drugName = drugDetails.getDrugName();
            Drug drug = new Drug();
            drug.setDrugName(drugName);
            drug.setMedicineName(drugDetails.getMedicineName());
            drug.setManufacturer(drugDetails.getManufacturer());
            drug.setUnitPrice(drugDetails.getUnitPrice());
            drug.setUnitsInStock(drugDetails.getUnitsInStock());
            drug.setCreatedTime(LocalDateTime.now());
            drug.setLastUpdatedTime(LocalDateTime.now());
            if (drugMap.containsKey(drugName)) {
                drugMap.get(drugName).add(drug);
            } else {
                List<Drug> drugList = new ArrayList<>();
                drugList.add(drug);
                drugMap.put(drugName, drugList);
            }
        });
        userInventory.setDrugs(drugMap);
        userInventory.setCreatedTime(LocalDateTime.now());
        userInventory.setLastUpdatedTime(LocalDateTime.now());
        repository.save(userInventory);
        return new StockCreatedResponse(LocalDateTime.now(), Constants.STOCK_ADDED_NEW_USER);
    }

    @Override
    public StockCreatedResponse updateStockToOldUser(CreateStock stock) {
        UserInventory userInventory = getUserInventoryDetails(stock.getUserId());
        Map<String, List<Drug>> drugMap = userInventory.getDrugs();
        stock.getDrugsList().forEach(drugDetails -> {
            int drugIndex = isDrugDetailsPresent(stock.getUserId(), drugDetails);
            Drug drug = new Drug();
            String drugName = drugDetails.getDrugName();
            drug.setDrugName(drugName);
            drug.setMedicineName(drugDetails.getMedicineName());
            drug.setManufacturer(drugDetails.getManufacturer());
            drug.setUnitPrice(drugDetails.getUnitPrice());
            if (drugIndex != -1) {
                int updatedStock = drugMap.get(drugDetails.getDrugName()).get(drugIndex).getUnitsInStock()
                        + drugDetails.getUnitsInStock();
                drug.setUnitsInStock(updatedStock);
                drug.setLastUpdatedTime(LocalDateTime.now());
                drugMap.get(drugDetails.getDrugName()).set(drugIndex, drug);
            } else if (drugMap.containsKey(drugDetails.getDrugName())) {
                drug.setUnitsInStock(drugDetails.getUnitsInStock());
                drug.setCreatedTime(LocalDateTime.now());
                drug.setLastUpdatedTime(LocalDateTime.now());
                drugMap.get(drugDetails.getDrugName()).add(drug);
            } else {
                drug.setUnitsInStock(drugDetails.getUnitsInStock());
                drug.setCreatedTime(LocalDateTime.now());
                drug.setLastUpdatedTime(LocalDateTime.now());
                drugMap.put(drugDetails.getDrugName(), List.of(drug));
            }
        });
        userInventory.setDrugs(drugMap);
        userInventory.setLastUpdatedTime(LocalDateTime.now());
        repository.save(userInventory);
        return new StockCreatedResponse(LocalDateTime.now(), Constants.STOCK_ADDED_OLD_USER);
    }

    @Override
    public int isDrugDetailsPresent(String userId, DrugDetails drugDetails) {
        UserInventory inventory = getUserInventoryDetails(userId);
        if (inventory != null) {
            Map<String, List<Drug>> drugMap = inventory.getDrugs();
            if (drugMap.containsKey(drugDetails.getDrugName())) {
                List<Drug> medicinesOfSameDrug = drugMap.get(drugDetails.getDrugName());
                for (int i = 0; i < medicinesOfSameDrug.size(); i++) {
                    Drug drug = medicinesOfSameDrug.get(i);
                    if (drug.getMedicineName().equals(drugDetails.getMedicineName())
                            && drug.getManufacturer().equals(drugDetails.getManufacturer())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
