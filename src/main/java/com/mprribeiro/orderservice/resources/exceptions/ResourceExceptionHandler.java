package com.mprribeiro.orderservice.resources.exceptions;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mprribeiro.orderservice.services.exception.DataIntegrityException;
import com.mprribeiro.orderservice.services.exception.ObjectAlreadyExistsException;
import com.mprribeiro.orderservice.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(sdf.format(Date.from(Instant.now())), HttpStatus.NOT_FOUND.value(), "Object not found", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class) 
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
	    
		StandardError err = new StandardError(sdf.format(Date.from(Instant.now())), HttpStatus.BAD_REQUEST.value(), "Data Integrity!", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); 
	}
	
	@ExceptionHandler(ObjectAlreadyExistsException.class) 
	public ResponseEntity<StandardError> dataIntegrity(ObjectAlreadyExistsException e, HttpServletRequest request) {
	    
		StandardError err = new StandardError(sdf.format(Date.from(Instant.now())), HttpStatus.BAD_REQUEST.value(), "It already exists!", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); 
	}
	 
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError err = new ValidationError(sdf.format(Date.from(Instant.now())), HttpStatus.BAD_REQUEST.value(), "Argument is not valid!", e.getMessage(), request.getRequestURI()); 
		for(FieldError x : e.getBindingResult().getFieldErrors()) { 
			err.addError(x.getField(), x.getDefaultMessage()); 
		}
			
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); 
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class) 
	public ResponseEntity<StandardError> httpMessage(HttpMessageNotReadableException e, HttpServletRequest request) {
	    
		StandardError err = new StandardError(sdf.format(Date.from(Instant.now())), HttpStatus.BAD_REQUEST.value(), "Argument is not valid!", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); 
	}
}
