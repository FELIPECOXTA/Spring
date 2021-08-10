package org.generation.blogPessoal.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioTest {

	// criando atributos a serem testados
	public Usuario usuario;
	public Usuario usuarioNulo;

	// construindo validador
	@Autowired
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	Validator validator = factory.getValidator();

	// definindo anotação BeforeEach para que método start inicie antes de cada um
	// dos testes
	@BeforeEach
	public void start() {

		// definindo atributos data e nome iniciais para testar com função assert
		LocalDate data = LocalDate.parse("1997-09-05", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		usuario = new Usuario(0L, "Matheus Tabarelli", "matheus@email.com", "12345678", data);
	}

	// estruturando o primeiro teste (para atributos não nulos)
	@Test
	@DisplayName("✔ Valida Atributos Não Nulos")
	void testValidaAtributos() {

		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuario);

		System.out.println(violacao.toString());

		assertTrue(violacao.isEmpty());
	}

	// estruturando o segundo teste (para tributos nulos)
	@Test
	@DisplayName("❌ Não Valida Atributos Nulos")
	void testNaoValidaAtributos() {

		Set<ConstraintViolation<Usuario>> violacao = validator.validate(usuarioNulo);

		System.out.println(violacao.toString());

		assertTrue(violacao.isEmpty());
	}

}
