package pl.chemik77.controller.utils.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pl.chemik77.database.dataManager.EmployeeDM;
import pl.chemik77.model.Employee;

@FacesConverter(value="employeeConverter")
public class EmployeeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		EmployeeDM employeeDM = new EmployeeDM();
		Employee employeeByName = employeeDM.getEmployeeByName(string);
		return employeeByName;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Employee employee = (Employee) object;
		return employee.toString();
	}

}
