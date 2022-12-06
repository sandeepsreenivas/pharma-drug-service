package com.pharmabms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Drug {

    private String medicineName;
    private String drugName;
    private String manufacturer;
    private int unitsInStock;
    private double unitPrice;
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

}
