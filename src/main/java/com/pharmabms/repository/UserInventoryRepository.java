package com.pharmabms.repository;

import com.pharmabms.entity.UserInventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInventoryRepository extends MongoRepository<UserInventory, String> {
}
