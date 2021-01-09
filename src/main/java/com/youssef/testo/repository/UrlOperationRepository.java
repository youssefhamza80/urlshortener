package com.youssef.testo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.youssef.testo.entity.UrlOperation;

@Repository
public interface UrlOperationRepository extends CrudRepository<UrlOperation, Long> {
	int countByUrlIdAndOperation(long urlId, String operation);
	int countByUrlIdAndOperationAndUserName(long urlId, String operation, String userName);
	
	List<UrlOperation> findByUserName(String userName);
}
