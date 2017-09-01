package pl.chemik77.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PageController {

	private List<String> letters;

	@PostConstruct
	private void init() {
		initLetters();
	}

	private void initLetters() {
		letters = new ArrayList<>();
		letters.clear();
		String letter = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		letters.addAll(Arrays.asList(letter.split("")));
	}


	public List<String> getLetters() {
		return letters;
	}

	public void setLetters(List<String> letters) {
		this.letters = letters;
	}

}
