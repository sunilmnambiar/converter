package com.sboot.converter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ConversionRepository extends CrudRepository<Conversion, Integer>  {
	
	@Query(nativeQuery = true, value = "SELECT * FROM conversion ORDER BY Date DESC LIMIT 20")
	List<Conversion> findRecent();

}
