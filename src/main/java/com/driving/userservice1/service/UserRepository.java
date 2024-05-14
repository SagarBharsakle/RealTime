package com.driving.userservice1.service;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.driving.userservice1.model.Users;




public interface UserRepository extends PagingAndSortingRepository<Users, Integer>{

	public Users findByUsername(String username); 

	public List<Users> getByAddress(String address);
	
	@Query(value = "select * from user where email = :email",nativeQuery = true)
	public Users getUserByEmail( String email);
	
	@Query(value = "select email from user where username = :username",nativeQuery = true)
	public String getEmailByUserName(String username);
	
	@Query(value = "select username from user where address = :address",nativeQuery = true)
	public List<String> getByUsersByAddr(String address);
}
	