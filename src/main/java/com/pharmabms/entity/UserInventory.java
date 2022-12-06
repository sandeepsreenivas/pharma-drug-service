package com.pharmabms.entity;

import com.pharmabms.dto.Drug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document("user-inventory")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserInventory {

    @Id
    private String userId;
    private Map<String, List<Drug>> drugs;
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

}
