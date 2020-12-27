package com.youssef.testo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.youssef.testo.entity.Url;

@Repository
public interface UrlDataRepository extends CrudRepository<Url, Long> {
	List<Url> findByLongUrl(String longUrl);	
}
