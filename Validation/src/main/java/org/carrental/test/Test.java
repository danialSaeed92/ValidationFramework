package org.carrental.test;

import cs525.annotations.Validations.Email;
import cs525.annotations.Validator.FieldValidator;
import cs525.annotations.Validator.Validator;

public class Test {

	@Email
	String email;
	
	public Test(String email){
		this.email = email;
	}

	@Override
	public String toString() {
		return "Test [email=" + email + "]";
	}
	
	public static void main(String[] args){
		
		Test t = new Test("asdhaskjdh@abc.com");
		Validator v = new FieldValidator();
	    v.validate(t);
		
	}
}
