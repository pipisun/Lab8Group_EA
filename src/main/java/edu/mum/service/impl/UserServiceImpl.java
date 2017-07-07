package edu.mum.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import edu.mum.dao.GenericDao;
import edu.mum.dao.UserDao;
import edu.mum.domain.Details;
import edu.mum.domain.User;
import edu.mum.exception.ValidationException;
import edu.mum.validation.ServiceValidation;

@Service
@Transactional 
public class UserServiceImpl implements edu.mum.service.UserService {
	
 	@Autowired
	private UserDao userDao;
 	
	@Autowired
	MessageSourceAccessor messageAccessor;
	
 	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save( User user) {
 		if(this.validate(user, Default.class))
 			userDao.save(user);
 	}
  	
  	
 	public List<User> findAll() {
		return (List<User>)userDao.findAll();
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
 	@PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
  	public User update(User user) {
 		if(this.validate(user, Details.class))
 			return userDao.update(user);
 		return null;
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
 
	@Override
	public Boolean validate(User user, Class<?> group) {
		
       	System.out.println();
       	System.out.println("DOING Validation!");
            	
       	// Spring  : BeanPropertyBindingResult- Default implementation of the Errors and BindingResult interfaces
//        Errors errors = new BeanPropertyBindingResult(user, group.getName());
        Validator validator =  Validation.buildDefaultValidatorFactory().getValidator(); 
        Set<ConstraintViolation<Object>> errors = validator.validate(user, group);

        Boolean validationSuccess = errors.size() == 0 ? true : false;       
       	if (!validationSuccess) {
      	     for (ConstraintViolation<Object> error : errors) {
      	          System.out.println(error.getPropertyPath() + " " +error.getMessage());
      	     }
      	   	return false;
      	}
       	
    	System.out.println("Validation Success!");
    	return true;
	}
}
