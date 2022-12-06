package com.pharmabms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DrugDetails {

    private String medicineName;
    private String drugName;
    private String manufacturer;
    private int unitsInStock;
    private double unitPrice;

}
