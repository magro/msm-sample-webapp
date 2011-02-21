package de.test;

import java.io.Serializable;

public class TestObject implements Serializable {

	private String message;

	public TestObject() {
	}

	
	public TestObject(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
