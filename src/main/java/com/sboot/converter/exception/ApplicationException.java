package com.sboot.converter.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 6396665722352835134L;

	public ApplicationException(String msg) {
		super(msg);
	}

	public ApplicationException(String msg, Throwable t) {
		super(msg, t);
	}

}
