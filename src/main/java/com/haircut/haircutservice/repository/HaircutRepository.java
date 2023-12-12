package com.haircut.haircutservice.repository;

import com.haircut.haircutservice.model.Haircut;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HaircutRepository extends MongoRepository<Haircut, String> {
}
