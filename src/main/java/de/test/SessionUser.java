package de.test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

//@ManagedBean
//@SessionScoped
@Named
@javax.enterprise.context.SessionScoped
public class SessionUser implements Serializable {

	private String message;
	
	public SessionUser() {
		System.err.println("????? create SessionUser");
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
