package edu.mum.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import edu.mum.dao.UserDao;
import edu.mum.domain.User;
import edu.mum.exception.ValidationException;

@Service
@Transactional 
public class UserServiceImpl implements edu.mum.service.UserService {
	
 	@Autowired
	private UserDao userDao;
 	
// 	@Autowired
// 	private Validator validator;
 	
	@Autowired
	MessageSourceAccessor messageAccessor;
	
	@Override
 	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save( User user) {
// 		if(this.validate(user, Default.class))
 			userDao.save(user);
 	}
  	
  	@Override
 	public List<User> findAll() {
		return (List<User>)userDao.findAll();
	}
  	
  	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
  	@Override
 	@PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
  	public User update(User user) {
// 		if(this.validate(user, edu.mum.domain.User.Details.class))
 			return userDao.update(user);
// 		return null;
//		return user;
	}

	@Override
 	public User testRefresh(User user) {
		user.setEmail("Lotta@Doe.com");
		  userDao.save(user);
		
		  return user;
	}


	@Override
 	public User findOne(Long id) {
		 
		return userDao.findOne(id);
	}
 
	@Override
	public Boolean validate(User user, Class<?> group) {
		
       	System.out.println();
       	System.out.println("DOING Validation!");
       	// Spring  : BeanPropertyBindingResult- Default implementation of the Errors and BindingResult interfaces
//        Errors errors = new BeanPropertyBindingResult(user, User.class.getName());
        Validator validator =  Validation.buildDefaultValidatorFactory().getValidator(); 
        Set<ConstraintViolation<Object>> errors = validator.validate(user, group);
//        validator.validate(user, errors);
        
        Boolean validationSuccess = errors.size() == 0 ? true : false;       
       	if (!validationSuccess) {
      	     for (ConstraintViolation<Object> error : errors) {
      	          System.out.println(error.getPropertyPath() + " " +error.getMessage());
      	     }
      	     throw new ValidationException(errors);
      	}
//        if (errors.hasErrors()) {
//      	  List<FieldError> fieldErrors =  errors.getFieldErrors();
//      	  for (FieldError fieldError : fieldErrors) {
//      		  
//      		  System.out.println(messageAccessor.getMessage(fieldError));
//      	  }
//          	throw new ValidationException(errors);
//      	}
       	
    	System.out.println("Validation Success!");
    	return true;
	}
}
