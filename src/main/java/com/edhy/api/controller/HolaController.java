/**
 * 
 */
package com.edhy.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
public class HolaController {

	@GetMapping("/hola")
	public String saludar() {
		return "firts API Hello word Pipeline  with automatic trigger  ";
	}
	
}
