package br.org.generation.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping ("/atividade2")
public class HelloController2 {


	
	@GetMapping 
	public String ObjetivosAprendizagem () {
		return "Meus Objetivos para essa semana é surtar um pouco menos </br>"
				+ "e continuar com minha persistencia para conseguir alcançar </br>"
				+ "meus objetivos. E claro, pedir ajuda aos meus amigos.";
}
}
