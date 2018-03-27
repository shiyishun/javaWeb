package com.shi.dao.impl;

import org.springframework.stereotype.Repository;


import com.shi.dao.UserMngDao;
import com.shi.entity.User;


@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserMngDao{


}
