package br.org.generation.lojagames.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.lojagames.model.UsuarioLogin;
import br.org.generation.lojagames.model.UsuarioModel;
import br.org.generation.lojagames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired // faz a injeção do repository e permite o acesso ao banco de dados para as
				// validações.
	private UsuarioRepository usuarioRepository;

	public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {

		// verifica se o usuario existe, caso exista retorna a mensagme que já existe.
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário já existe!", null);

		// verifica se o usuario tem mais de 18.
		int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

		if (idade < 18)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário é menor de idade!", null);
		// crio o objeto encoder que é a senha criptografada
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// declaro a variavel senhaEncoder que vai receber a senha digitada atravez do
		// metodo get e deixala criptografada.
		String senhaEncoder = encoder.encode(usuario.getSenha());

		// faço com a essa nova senha seja salva
		usuario.setSenha(senhaEncoder);

		return Optional.of(usuarioRepository.save(usuario)); // obrigatorio ser Optional pois o motodo criado é Optinal
																// tbm.

	}

	public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {

		// verificar se o usuario existe, como é atualização o ususario precisa existir.
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {

			// verifica se o usuario tem mais de 18.
			int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

			if (idade < 18)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário é menor de idade!", null);
			// crio o objeto encoder que é a senha criptografada
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			// declaro a variavel senhaEncoder que vai receber a senha digitada atravez do
			// metodo get e deixala criptografada.
			String senhaEncoder = encoder.encode(usuario.getSenha());

			// faço com a essa nova senha seja salva
			usuario.setSenha(senhaEncoder);

			return Optional.of(usuarioRepository.save(usuario)); // obrigatorio ser Optional pois o motodo criado é
																	// Optinal
			// tbm.
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
		}

	}

	public Optional<UsuarioLogin> loginUsuario(Optional<UsuarioLogin> usuarioLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<UsuarioModel> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setToken(authHeader);

				return usuarioLogin;
			}
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou Senha Inválidos!", null);
	}
}
