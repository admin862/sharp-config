package com.dafy.config.client.exception;

public class SharpConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7736834494234836193L;

	public SharpConfigException(Exception e) {
		super(e);
	}

	public SharpConfigException(String exc, Exception e) {
		super(exc, e);
	}
	
	public SharpConfigException(String exc){
		super(exc);
	}
}
