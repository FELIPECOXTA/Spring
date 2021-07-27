package br.org.generation.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/atividade1")
public class HelloController {
	
	@GetMapping
	public String HabilidadeMentalidade () {
		return"A Habilidade é a de Comunicação </br>"
				+ "e a Mentalidade é de Persistência";
		
	}
	@RequestMapping ("/atividade2")
	public String ObjetivosAprendizagem () {
		return "Meus Objetivos para essa semana é surtar um pouco menos </br>"
				+ "e continuar com minha persistencia para conseguir alcançar </br>"
				+ "meus objetivos. E claro, pedir ajuda aos meus amigos.";
	
}
}
