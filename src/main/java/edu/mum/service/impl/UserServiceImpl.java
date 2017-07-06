package edu.mum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.dao.GenericDao;
import edu.mum.dao.UserDao;
import edu.mum.domain.User;
import edu.mum.validation.ServiceValidation;

@Service
@Transactional 
public class UserServiceImpl implements edu.mum.service.UserService {
	
 	@Autowired
	private UserDao userDao;
 	
 	@ServiceValidation
 	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void save( User user) {  		
  		userDao.save(user);
 	}
  	
  	
 	public List<User> findAll() {
		return (List<User>)userDao.findAll();
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	@ServiceValidation
 	@PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
  	public User update(User user) {
		 return userDao.update(user);

	}

	
 	public User testRefresh(User user) {
		user.setEmail("Lotta@Doe.com");
		  userDao.save(user);
		
		  return user;
	}


	@Override
 	public User findOne(Long id) {
		 
		return userDao.findOne(id);
	}
 

}
