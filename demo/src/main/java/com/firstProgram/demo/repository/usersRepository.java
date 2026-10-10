package com.firstProgram.demo.repository;

import com.firstProgram.demo.entity.Users;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface usersRepository extends MongoRepository<Users, ObjectId> {
    Users findByUsername(String username);
    Users deleteByUsername(String name);
}
