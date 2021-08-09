package br.org.generation.lojagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.lojagames.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{ 
		
	public Optional<UsuarioModel> findByUsuario(String usuario); // aqui eu coloquei o metodo finBy só com o nome usuario sem o model, 
																 //pois eu entendo que estamos criano o nome.  
	
}
