package com.shi.service;

import java.io.Serializable;
import com.shi.entity.User;

public interface UserMngService {
 
	public User find();
	
	public Serializable save(User user);
}
