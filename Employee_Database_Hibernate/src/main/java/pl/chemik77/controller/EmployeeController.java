package pl.chemik77.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.chemik77.database.EmployeeDataManager;
import pl.chemik77.model.Employee;

@ManagedBean
@SessionScoped
public class EmployeeController {

	// --------FIELDS----------------

	private List<Employee> employees;
	private List<String> letters;
	
	private String word;

	// --------FIELDS CONFIG---------

	private EmployeeDataManager employeeDataManager;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		employeeDataManager = new EmployeeDataManager();
		initEmployees();
		initLetters();
	}

	private void initEmployees() {
		employees = employeeDataManager.getAllEmployees();
	}

	private void initLetters() {
		letters = new ArrayList<>();
		letters.clear();
		String letter = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		letters.addAll(Arrays.asList(letter.split("")));
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

	public List<String> getLetters() {
		return letters;
	}

	public void setLetters(List<String> letters) {
		this.letters = letters;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
