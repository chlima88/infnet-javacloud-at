package br.edu.infnet.atapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import br.edu.infnet.atapi.model.exceptions.PrecoBaseInvalidoException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TServico")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY , property = "tipo")
@JsonSubTypes({ 
    @JsonSubTypes.Type(value = Eletrica.class, name = "eletrica"),
    @JsonSubTypes.Type(value = Lanternagem.class, name = "lanternagem"),
    @JsonSubTypes.Type(value = Mecanica.class, name = "mecanica")
})
public abstract class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String codigo;
	private float precoBase;
	private boolean terceirizado;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	@JsonIgnore
	@ManyToMany(mappedBy = "servicos")
	private List<Agendamento> agendamentos;

	public Servico() {};

	public Servico(String nome, String codigo, float precoBase, boolean terceirizado) throws PrecoBaseInvalidoException {

		this();

		if (precoBase <= 0) {
			throw new PrecoBaseInvalidoException("O preco deve ser superior a zero.");
		}

		this.nome = nome;
		this.codigo = codigo;
		this.precoBase = precoBase;
		this.terceirizado = terceirizado;
	}

	public abstract float obterPrecoMaoDeObra();

	public float obterPrecoFinal() {
		return this.terceirizado ? this.obterPrecoMaoDeObra() * 1.2f : this.obterPrecoMaoDeObra();
	};

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public float getPrecoBase() {
		return this.precoBase;
	}

	public void setPrecoBase(float precoBase) {
		this.precoBase = precoBase;
	}

	public Boolean getTerceirizado() {
		return this.terceirizado;
	}

	public void setTerceirizado(boolean terceirizado) {
		this.terceirizado = terceirizado;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.codigo);
		sb.append(";");
		sb.append(this.nome);
		sb.append(";");
		sb.append(this.precoBase);
		sb.append(";");
		sb.append(this.terceirizado);

		return sb.toString();
	}

}
