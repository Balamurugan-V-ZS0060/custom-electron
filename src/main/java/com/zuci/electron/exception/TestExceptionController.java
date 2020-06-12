package com.zuci.electron.exception;

import java.nio.file.FileAlreadyExistsException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestExceptionController {
	
	@GetMapping("/test")
	public String test() throws NullPointerException {
		int a  = 2;
		if(a%2==0) {
			throw new NullPointerException("Custom null found exception");
		}
		return "Success";
	}
}
