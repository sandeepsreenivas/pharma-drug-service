package com.pharmabms.controller;

import com.pharmabms.dto.CreateStock;
import com.pharmabms.dto.StockCreatedResponse;
import com.pharmabms.service.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmabms/drug")
public class UserInventoryController {

    @Autowired
    private UserInventoryService userInventoryService;

    @PostMapping("/add")
    public ResponseEntity<StockCreatedResponse> addStock(@RequestBody CreateStock stock) {
        StockCreatedResponse response = userInventoryService.addStock(stock);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
