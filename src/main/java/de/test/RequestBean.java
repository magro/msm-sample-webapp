package de.test;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//@ManagedBean
//@RequestScoped
@Named
@javax.enterprise.context.RequestScoped
public class RequestBean implements Serializable {

	String key = "testObject";
	
	//@ManagedProperty("#{sessionUser}")
	@Inject
	private SessionUser sessionUser;
	
	private String message;
	
	public RequestBean() {
		ExternalContext externalContext =
			FacesContext.getCurrentInstance().getExternalContext();

		if (externalContext.getSessionMap().containsKey(key)) {
			message =((TestObject) externalContext.getSessionMap().get(key)).getMessage();
		}
		else {
			message = "";
		}
	}

	public String save() {
		
		ExternalContext externalContext =
			FacesContext.getCurrentInstance().getExternalContext();
		
		TestObject to = null;
		if (externalContext.getSessionMap().containsKey(key)) {
			to = (TestObject) externalContext.getSessionMap().get(key);
		}
		else {
			to = new TestObject();
			externalContext.getSessionMap().put(key, to);
		}
		to.setMessage(message);
		
		//externalContext.getSessionMap().remove(key);
		//externalContext.getSessionMap().put(key, to);

		return null;
	}

	/**
	 * @return the sessionUser
	 */
	public SessionUser getSessionUser() {
		return sessionUser;
	}

	/**
	 * @param sessionUser the sessionUser to set
	 */
	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
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
