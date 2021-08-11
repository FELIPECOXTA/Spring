package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		
		
		/**
		 * Lanço uma Exception do tipo Response Status Bad Request
		 */
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
		
		/**
		 * Calcular a idade (em anos) através do método between, da Classe Period
		 */
		
		 int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();
		
		/**
		 * Verifico se a iade é menor de 18. Caso positivo,
		 * Lanço uma Exception do tipo Response Status Bad Request 
		 */
		
		 if(idade < 18)
			throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Usuário menor de 18 anos", null);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return Optional.of(usuarioRepository.save(usuario));
	
	}

	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {

			/**
			 * Mesma verificação do método cadastrarUsuario
			 */

			int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();
			
			if(idade < 18)
				throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Usuário menor de 18 anos", null);
					
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			String senhaEncoder = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncoder);
			
			return Optional.of(usuarioRepository.save(usuario));
		
		}else {
			
			/**
			 * Lanço uma Exception do tipo Response Status Not Found
			 */

			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
			
		}
		
	}
	
	public Optional<UsuarioLogin> logarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				usuarioLogin.get().setToken(authHeader);				
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				
				return usuarioLogin;

			}
		}
		
		/**
		 * Lanço uma Exception do tipo Response Status Unauthorized
		*/
		
		throw new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);
	}

}