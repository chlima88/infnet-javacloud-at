package br.edu.infnet.atapi.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEndereco")
public class Endereco {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	@OneToOne(mappedBy = "endereco")
	private Usuario usuario;
	@OneToOne(mappedBy = "endereco")
	private Cliente cliente;
	
	public Endereco(){}
	
	public Endereco(String cep, String logradouro, String localidade, String bairro, String uf) {
		this();
		this.cep = cep;
		this.logradouro = logradouro;
		this.localidade = localidade;
		this.bairro = bairro;
		this.uf = uf;
	}
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		List<String> cleanCep = Arrays.asList(cep.split("-"));
		this.cep = String.join("", cleanCep);
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "{" +
				"id: " + this.id + ", " +
				"cep: " + this.cep + ", " + 
				"logradouro: " + this.logradouro + ", " +
				"bairro: " + this.bairro + ", " +
				"localidade: " + this.localidade + ", " +
				"uf: " + this.uf  +
				"}";
	}
	
}
