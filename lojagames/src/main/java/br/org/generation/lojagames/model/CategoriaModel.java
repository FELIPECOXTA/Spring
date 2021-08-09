package br.org.generation.lojagames.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "tb_categoria")
public class CategoriaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Ó seu Oreudo, o Atributo tipo não pode ser nulo.!")
	private String tipo;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL) // esse cascade é responsavel por fazer com que todas alterações em categoria sejam feitas nos produtos tbm.
	@JsonIgnoreProperties ("categoria") // faz com que o não entre em loop.
	private List<ProdutoModel> produto; 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<ProdutoModel> getProduto() {
		return produto;
	}

	public void setProduto(List<ProdutoModel> produto) {
		this.produto = produto;
	}


}
