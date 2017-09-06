package pl.chemik77.controller.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "localDateConverter")
public class LocalDateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		return LocalDate.parse(string);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		LocalDate dateValue = (LocalDate) object;
		return dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}
