package com.youssef.testo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.youssef.testo.entity.UrlOperation;

@Repository
public interface UrlOperationRepository extends CrudRepository<UrlOperation, Long> {
	int countByUrlIdAndOperation(long urlId, String operation);
	int countByUrlIdAndOperationAndUserId(long urlId, String operation, long userId);
}
