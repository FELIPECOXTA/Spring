package br.org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.generation.blogPessoal.model.UserLogin;
import br.org.generation.blogPessoal.model.Usuario;
import br.org.generation.blogPessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private  UsuarioRepository repository;
	
	public Usuario CadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return repository.save(usuario);
		
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> usuarioLogin){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioLogin.get().getSenha())) {
				
				String auth = usuario.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte [] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setToken(authHeader);
				
				return usuarioLogin;
			}
			 
		}
		
		return null;
	}

}
