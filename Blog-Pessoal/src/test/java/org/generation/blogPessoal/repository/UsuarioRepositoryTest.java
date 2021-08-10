package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// definindo anotação BeforeAll para que método start inicie antes de todos os
	// testes
	@BeforeAll
	void start() throws ParseException {

		// definindo atributos data e nome iniciais para aplicar testes
		LocalDate data = LocalDate.parse("1997-09-05", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Usuario usuario = new Usuario(0, "Matheus Tabarelli", "matheus@email.com", "12345678", data);

		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);

		usuario = new Usuario(0, "Manuel da Silva", "manuel@email.com.br", "13465278", data);
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);

		usuario = new Usuario(0, "Frederico da Silva", "frederico@email.com.br", "13465278", data);
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);

		usuario = new Usuario(0, "Paulo Antunes", "paulo@email.com.br", "13465278", data);
		if (!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			usuarioRepository.save(usuario);

	}

	// criando teste para retornar um nome específico
	@Test
	@DisplayName("🔍 Retorna o nome pesquisado")
	public void findByNomeRetornaNome() throws Exception {

		Usuario usuario = usuarioRepository.findByNome("Matheus Tabarelli");
		assertTrue(usuario.getNome().equals("Matheus Tabarelli"));

	}
	// criando teste para retornar quantidades de um nome específico
	@Test
	@DisplayName("💾 Retorna 2 usuarios")
	public void findAllByNomeContainingIgnoreCaseRetornaTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(2, listaDeUsuarios.size());
	}

	@AfterAll
	public void end() {

		usuarioRepository.deleteAll();

	}
}