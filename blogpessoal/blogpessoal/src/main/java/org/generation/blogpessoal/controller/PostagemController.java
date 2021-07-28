package org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

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

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;

/**
 * 
 * /@RestController: Annotation (Anotação), que indica que a Classe é uma RestController,
 * ou seja, é responsável por responder às requisições http enviadas para um endpoint
 * (endereço) definido na anotação @RequestMapping
 * 
 * @RequestMapping("/postagens"): Annotation (Anotação), que indica o endpoint (endereço) 
 * que a controladora responderá as requisições 
 * 
 * @CrossOrigin("*"): Annotation (Anotação), que indica que a classe controladora permitirá o 
 * recebimento de requisições realizadas de fora do domínio (localhost, em nosso caso) ao qual 
 * ela pertence. Essa anotação é essencial para que o front-end (Angular em nosso caso), tenha
 * acesso à nossa API (O termo técnico é consumir a API)
 * 
 * Para as versões mais recentes do Angular, recomenda-se alterar esta anotação para: 
 * /@CrossOrigin(origins = "", allowedHeaders = "") 
 * Esta anotação, além de liberar as origens, libera também os cabeçalhos das requisições
 * 
 */

@RestController
@RequestMapping("/postagens") 
@CrossOrigin(origins = "", allowedHeaders = "")  //CrossOrigin essencial para o FrontEnd
public class PostagemController {
	
	/* CrossOrigin essencial para o FrontEnd
	 * Se eles estão em lugares diferentes, estão em endereços diferente (Front - Back)
	 * Se não colocar o CrossOrigin, quando o front tentar acesar o back, não vai autorizar o acesso
	 * "A minha API acessa qualquer front end de qualquer endereço"
	 * Se trocar o * pelo endereço, vai acessar somente o endereço colocado
	 * 
	 * 
	 * Injeção de Dependência (@Autowired): Consiste  na  maneira,  ou  seja,  na  implementação 
	 * utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for 
	 * necessário.
	 * 
	 * A Injeção de Dependência define quais classes serão instanciadas e em quais lugares serão 
	 * injetadas quando houver necessidade. 
	 * 
	 * Em nosso exemplo a classe controladora cria um ponto de injeção da interface PostagemRepository, 
	 * e quando houver a necessidade o Spring Framework irá criar uma instância (objeto) desta interface
	 * permitindo o uso de todos os métodos (padrão ou personalizados em PostagemRepository)
	 *  
	 * */
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	/**
	 * Autowired - Injeção de dependencias
	 * Zoom: Rafa tem conta no zoom que as pessoas acessam. O gabriel entra como co anfitrião e tem funcionalidades
	 * maiores que os outros e menores que o Rafa. Rafa passa o host para o Gabriel. Plenos poderes
	 * Essa transferencia de comandos é o que o Autowired faz. Pega minha postagemRepository e passa o comando dela
	 * Spring ta aqui a postagem, você tem plenos poderes sobre ela, faça ela funcionar na classe.
	 * 
	 * Porque? Não vai precisa criar Objeto, o spring cria, faz tudo.
	 * 
	 * Listar todas as Postagens
	 *  
	 * /@GetMapping: Annotation (Anotação), que indica que o método abaixo responderá todaas as 
	 * requisições do tipo GET que forem enviadas no endpoint /postagens
	 * 
	 * O Método getAll() será do tipo ResponseEntity porque ele responderá a requisição (Request),
	 * com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo List (Collection) preenchido com objetos do tipo Postagem,
	 * que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAll());: Executa o método findAll(), que é um
	 * método padrão da interface JpaRepository e retorna o status OK = 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 */

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll (){ //metodo getall lista todas as postagens do banco de dados
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	// OK = 200
		/*Uma List com as postagens
		 * select * from tb_postagens;
		 */
	
	@GetMapping ("idifelse/{id}")
	public ResponseEntity<Postagem> getByIdIfElse(@PathVariable long id) {
		
		Optional<Postagem> postagem = postagemRepository.findById(id);
		if (postagem.isPresent()) {
			return ResponseEntity.ok(postagem.get());
		}
		return ResponseEntity.notFound().build();	
	}
	//se deu tudo certo, mostra o not found
	@GetMapping ("idtrycatch/{id}")
	public ResponseEntity<Postagem> getByIdTryCatch(@PathVariable long id) {
		
		Optional<Postagem> postagem = postagemRepository.findById(id);
		try {
			return ResponseEntity.ok(postagem.get());
		} catch (Exception e) {
		return ResponseEntity.notFound().build();
		}
}
	@GetMapping ("/{id}") //Função lâmbida
	public ResponseEntity <Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping 
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	@PutMapping 
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
}
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemRepository.deleteById(id);
	}
}
