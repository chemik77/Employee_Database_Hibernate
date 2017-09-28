package pl.chemik77.controller.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtil {
	
	public static void addInfoMessage(String message) {
		FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
		FacesContext.getCurrentInstance().addMessage(null, message2);
	}
}
