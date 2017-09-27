package pl.chemik77.controller.utils;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ContextUtil {

	public static void redirectNewForm() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    StringBuffer requestURL = ((HttpServletRequest) ec.getRequest()).getRequestURL();
	    String queryString = ((HttpServletRequest) ec.getRequest()).getQueryString();
	    ec.redirect((queryString == null) ? requestURL.toString() : requestURL.append('?').append(queryString).toString());
		
	}
	
	public static void redirectTo(String page) throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/" + page);
	}
}
