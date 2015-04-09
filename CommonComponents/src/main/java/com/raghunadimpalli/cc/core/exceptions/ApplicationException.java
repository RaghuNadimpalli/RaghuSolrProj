package com.raghunadimpalli.cc.core.exceptions;

public class ApplicationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ApplicationException(String message){
		this.message = message;
	}
	
	@Override
	public String toString(){
		return message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
