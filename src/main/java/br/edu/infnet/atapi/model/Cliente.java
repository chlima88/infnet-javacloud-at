package br.edu.infnet.atapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.infnet.atapi.model.exceptions.ClienteInvalidoException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TCliente")
public class Cliente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	private String nome;
	private String documento;
	private String contato;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Agendamento> agendamento;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "idEndereco")
	private Endereco endereco;
	
	public Cliente() {};
	
	public Cliente(String nome, String documento, String contato) throws ClienteInvalidoException {
		
		this();
		
		if ( nome == "" || nome == null ) {
			throw new ClienteInvalidoException("O nome do cliente deve ser informado.");
		}
		if ( contato == "" || contato == null ) {
			throw new ClienteInvalidoException("O contato do cliente deve ser informado.");
		}
		if ( documento == "" || documento == null) {
			throw new ClienteInvalidoException("O documento do cliente deve ser informado.");
		}
		
		this.nome = nome;
		this.documento = documento;
		this.contato = contato;
	}
	

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getContato() {
		return this.contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Agendamento> getAgendamento() {
	return agendamento;
	}
	
	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.nome);
		sb.append(";");
		sb.append(this.documento);
		sb.append(";");
		sb.append(this.contato);
		
		return sb.toString();
	}
	
	
	
}
