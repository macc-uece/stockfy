package br.com.uece.frameworks.stockfy.service.exceptions;

/**
 * ObjectNotFoundException
 */
public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 4691920743147607450L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
