package br.org.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.lojagames.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long>{ 
	
	public List<ProdutoModel> findAllBynomeJogoContainingIgnoreCase (String Produto); // sempre inserir o atributo e não a classe. 
}
