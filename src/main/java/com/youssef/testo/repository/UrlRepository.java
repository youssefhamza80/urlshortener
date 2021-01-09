package com.youssef.testo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.youssef.testo.entity.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
	Optional<Url> findByLongUrl(String longUrl);
	
	
	@Query("SELECT a from Url a where urlId IN (SELECT urlId FROM UrlOperation where userName=:userName)")	
	List<Url> findByUserName(@Param(value = "userName") String userName);
}
