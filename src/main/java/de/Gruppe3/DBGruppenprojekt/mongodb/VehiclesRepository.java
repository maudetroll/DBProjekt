package de.Gruppe3.DBGruppenprojekt.mongodb;

import de.Gruppe3.DBGruppenprojekt.Vehicles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VehiclesRepository extends MongoRepository<Vehicles, Integer> {
    @Query
    Vehicles findById(String id);
}
