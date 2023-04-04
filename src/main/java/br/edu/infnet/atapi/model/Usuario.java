package br.edu.infnet.atapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TUsuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private List<String> caracteristicas;
	private String tipo;
	private String empresa;
	private String imagemUrl;
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "idUsuario")
	private List<Cliente> clientes;
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "idUsuario")
	private List<Servico> servicos;
	@JsonIgnore
	@OneToMany(mappedBy="usuario")
	private List<Agendamento> agendamentos;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "idEndereco")
	private Endereco endereco;
	private boolean masterAdmin = false;

	public Usuario() {};
	
	public Usuario(String email, String senha) {
		this();
		this.email = email;
		this.senha = senha;	
	};
		
	public Usuario(String nome, String email, String senha) {
		this(email, senha);
		this.nome = nome;			
	}

	public Usuario(
			String nome,
			String email,
			String senha,
			boolean masterAdmin,
			List<String> caracteristicas,
			String tipo,
			String empresa
		) {
		this(nome, email, senha);
		this.caracteristicas = caracteristicas;
		this.tipo = tipo;
		this.empresa = empresa;
		this.masterAdmin = masterAdmin;
	}
	
	
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<String> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<String> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}	

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
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
	

	public boolean isMasterAdmin() {
		return masterAdmin;
	}

	public void setMasterAdmin(boolean masterAdmin) {
		this.masterAdmin = masterAdmin;
	}

	public String toString() {
		
		return 
				"id: " + this.id + "; " +
				"nome: " + this.nome + "; " +
				"email: " + this.email + "; " +
				"senha: " + this.senha + "; " +
				"caracteristicas: " + this.caracteristicas + "; " + 
				"tipo: " + this.tipo + "; " +
				"empresa: " + this.empresa + "; " +
				"endereco: " + this.getEndereco();
			
				
	}
}
