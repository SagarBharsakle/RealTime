package com.driving.userservice1.service;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.driving.userservice1.dto.Orders;
import com.driving.userservice1.dto.userDto;
import com.driving.userservice1.model.Users;



@Service
public class UserService {
		
	@Autowired
	UserRepository repo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${orderserivce.url}")
	String orderServiceAddress;
	
	
	public String getGreet() {
		return restTemplate.getForObject(orderServiceAddress+"/greet", String.class);
	}
	

	public userDto getOrders(String username) {
		Users user = getUserByName(username);
		
		userDto dto = new userDto();
		
		dto.setUid(user.getUid());
		dto.setUsername(user.getUsername());
		dto.setAddress(user.getAddress());
		dto.setEmail(user.getEmail());
		dto.setContact(user.getContact());
		
		List<Orders> orders = restTemplate.getForObject(orderServiceAddress+"/orders/"+username,List.class);
		
		dto.setOrders(orders);
		
		return dto;
	}
	
	public List<Users> getUsers(){
		return (List<Users>) repo.findAll();
	}
	
	
	public Users getUserById(int uid) {
		return repo.findById(uid)
		.orElseThrow();
	}
	
	public Users saveUser(Users user) {
		return repo.save(user);
	}
	
	
	public Users getUserByName(String username) {
		return repo.findByUsername(username);
	}
	
	public Users getUserByEmail(String email) {
		return repo.getUserByEmail(email);
	}
	
	public String getEmailByUserName(String username) {
		return repo.getEmailByUserName(username);
	}
	
	public List<Users> getUserByAddres(String addr) {
		return repo.getByAddress(addr);
	}
	
	public List<String> getUsernameByAddr(String addr){
		return repo.getByUsersByAddr(addr);
	}
	
	public List<Users> getUsersByPage(int pageNumber,int pageSize){
		 Pageable paging = PageRequest.of(pageNumber, pageSize);
		  Page<Users> page= repo.findAll(paging);
		return page.toList();
	}
	
	public List<Users> getUserSortByUsername(String sortby){
		return (List<Users>) repo.findAll(Sort.by(sortby));
	}
	
	public List<Users> getUserSortByUsernameDescending(String sortby){
		return (List<Users>) repo.findAll(Sort.by(sortby).descending());
	}
	
	public Users updateUser(int uid , Users usr) {
		
		Users existingUser =getUserById(uid);

		existingUser.setUsername(usr.getUsername());
		existingUser.setAddress(usr.getAddress());
		existingUser.setContact(usr.getContact());
		existingUser.setEmail(usr.getEmail());
		
		return repo.save(existingUser);
	}
	
	public Users updatePartially(int uid, Map<String, Object> feilds) {
		Users existingUser =getUserById(uid);
		
		feilds.forEach((k, v) -> {
			Field feild = ReflectionUtils.findField(Users.class, k);
			feild.setAccessible(true);
			ReflectionUtils.setField(feild, existingUser, v);
		});
		return repo.save(existingUser);
	}
	
	public String deleteUser(int uid) {
		repo.deleteById(uid);
		return "User Deleted Succefully";
	}
	
}