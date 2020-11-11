package de.Gruppe3.DBGruppenprojekt;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoDBRepository extends MongoRepository<Vehicle, Integer> {
    @Query
    Vehicle findById(String id);
}