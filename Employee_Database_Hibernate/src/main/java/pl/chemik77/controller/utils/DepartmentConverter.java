package pl.chemik77.controller.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pl.chemik77.database.dataManager.DepartmentDM;
import pl.chemik77.model.Department;

@FacesConverter(value = "departmentConverter")
public class DepartmentConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		DepartmentDM departmentDM = new DepartmentDM();
		Department department = departmentDM.getDepartmentByName(string);
		return department;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Department department = (Department) object;
		return department.toString();
	}

}
