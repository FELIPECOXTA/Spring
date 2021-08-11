package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
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

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	@Autowired
	private PostagemRepository PostagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(PostagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return PostagemRepository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Postagem>> getByTitle(@PathVariable String title) {
		return ResponseEntity.ok(PostagemRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(PostagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(PostagemRepository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		PostagemRepository.deleteById(id);
	}
}