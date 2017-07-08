package edu.mum.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.Errors;
import java.lang.RuntimeException;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Set<ConstraintViolation<Object>> errors;
	
    //Parameterless Constructor
    public ValidationException() {}

    //Constructor that accepts a message
    public ValidationException(Set<ConstraintViolation<Object>> errors2)
    {
       this.errors = errors2;      
    }

	public Set<ConstraintViolation<Object>> getErrors() {
		return errors;
	}

}
