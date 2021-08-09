package br.org.generation.lojagames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.lojagames.model.CategoriaModel;
import br.org.generation.lojagames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	private ResponseEntity<List<CategoriaModel>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/{id}")
	private ResponseEntity<CategoriaModel> getById(@PathVariable long id) {
		return categoriaRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<CategoriaModel>> GetByTipo(@PathVariable String tipo){
		
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));	
	}
	
	// faz postagem
	@PostMapping
	public ResponseEntity<CategoriaModel> post(@RequestBody CategoriaModel categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}

	// faz alteração
	@PutMapping
	public ResponseEntity<CategoriaModel> put(@RequestBody CategoriaModel categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}

	// deleta.
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		categoriaRepository.deleteById(id);
	}
}