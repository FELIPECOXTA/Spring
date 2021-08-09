package br.org.generation.lojagames.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produto")
public class ProdutoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "O Atrtibuto título é obrigatório")
	@Size (min = 5, max = 100)
	private String nomeJogo;
	
	@NotNull(message = "O Atrtibuto valor é obrigatório")
	@Positive(message = "O numero deve ser positivo") 
	private double valor;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date date = new java.sql.Date(System.currentTimeMillis()); 
	
	@ManyToOne
	@JsonIgnoreProperties("produto") // perguntar porque a não ta maiusculo. 
	private CategoriaModel categoria;
	
	@ManyToOne
	@JsonIgnoreProperties("produto") // perguntar porque a não ta maiusculo. 
	private UsuarioModel usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeJogo() {
		return nomeJogo;
	}

	public void setNomeJogo(String nomeJogo) {
		this.nomeJogo = nomeJogo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	} 
}
