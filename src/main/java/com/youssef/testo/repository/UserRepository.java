package com.youssef.testo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.youssef.testo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {	
}
