package pl.chemik77.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pl.chemik77.database.dataManager.EmployeeDM;
import pl.chemik77.model.Employee;

@ManagedBean
@ViewScoped
public class EmployeeController {

	// --------FIELDS----------------

	private List<Employee> employees;

	private String word;

	// --------FIELDS CONFIG---------

	private EmployeeDM employeeDataManager;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		employeeDataManager = new EmployeeDM();
		initEmployees();
	}

	private void initEmployees() {
		employees = employeeDataManager.getAllEmployees();
	}

	// --------METHODS----------------

	public void initEmployeesLetter(String letter) {
		if (employees != null) {
			employees.clear();
		}
		if (letter.equals("All")) {
			initEmployees();
		} else {
			employees = employeeDataManager.getEmployeesLetter(letter);
		}

	}

	public void searchEmployee() {
		if (employees != null) {
			employees.clear();
		}
		if (word.equals("")) {
			initEmployees();
		} else {
			employees = employeeDataManager.getEmployeesWord(word);
		}
	}

	// --------GETTERS AND SETTERS----------------

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
