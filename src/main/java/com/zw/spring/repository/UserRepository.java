package com.zw.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zw.spring.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
}
