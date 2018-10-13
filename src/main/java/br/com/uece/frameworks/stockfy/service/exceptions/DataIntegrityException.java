package br.com.uece.frameworks.stockfy.service.exceptions;

/**
 * DataIntegrityException
 */

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = -7581759867659664029L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}