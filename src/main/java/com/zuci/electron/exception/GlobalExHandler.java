package com.zuci.electron.exception;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = FileNotFoundException.class)
	public ResponseEntity<Object> handleFieNotFoundEx(FileNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, "My File not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<Object> handleEx(Exception ex, WebRequest request) throws Exception {
		//System.out.println("GlobalExHandler.handle() : ");
		ex.printStackTrace();
		return handleExceptionInternal(ex, "API not found1", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
