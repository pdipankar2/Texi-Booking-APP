package com.jtc.india.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtc.india.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity,Integer> {

	
	//select * from user_table where uname=:uname
	public UserEntity findByUname(String uname);
	
	
}
