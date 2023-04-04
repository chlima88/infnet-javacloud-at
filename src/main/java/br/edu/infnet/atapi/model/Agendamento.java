package br.edu.infnet.atapi.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.infnet.atapi.model.exceptions.ClienteIndefinidoException;
import br.edu.infnet.atapi.model.exceptions.DuracaoAtendimentoException;
import br.edu.infnet.atapi.model.exceptions.ServicoIndefinidoException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TAgendamento")
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime data;
	private boolean confirmado;
	private int duracaoEmMinutos;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinColumn(name = "idAgendamento")
	private List<Servico> servicos;
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	public Agendamento() {};
	
	public Agendamento(Cliente cliente, List<Servico> servicos) throws ClienteIndefinidoException, ServicoIndefinidoException {
		
		if (cliente == null) {
			throw new ClienteIndefinidoException("E necessário atribuir um cliente ao agendamento");	
		}
		
		if (servicos == null) {
			throw new ServicoIndefinidoException("E necessario atribuir um servico ao agendamento");
		}
		
		this.data = LocalDateTime.now();
		this.duracaoEmMinutos = 0;
		this.cliente = cliente;
		this.servicos = servicos;
	}


	public int getDuracaoEmMinutos() {
		return this.duracaoEmMinutos;
	}

	public void setDuracaoEmMinutos(int duracaoEmMinutos) throws DuracaoAtendimentoException {
		if (duracaoEmMinutos <= 0 ) {
			throw new DuracaoAtendimentoException("A duracao do atendimento deve ser maior que zero.");
		}
		this.duracaoEmMinutos = duracaoEmMinutos;
	}




	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public LocalDateTime getData() {
		return this.data;
	}	


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return this.cliente;
	}


	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
	public List<Servico> getServicos() {
		return this.servicos;
	}
	
	public boolean getConfirmado() {
		return this.confirmado;
	}
	
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}


	@Override
	public String toString() { 
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YY HH:mm");
		
		return String.format("%s;%d;%s", 
				this.data.format(dateFormat), 
				this.duracaoEmMinutos,
				this.confirmado ? "Sim" : "Não");
		
	}
	
	public String obterPedido() {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YY HH:mm");
		
		StringBuilder sb = new StringBuilder();
		
		
		float orcamento = 0;
		for (Servico servico: this.servicos) {
			orcamento += servico.obterPrecoFinal();
		}
		
		sb.append(this.data.format(dateFormat));
		sb.append(";");
		sb.append(this.duracaoEmMinutos);
		sb.append(";");
		sb.append(this.confirmado ? "Sim" : "Não");
		sb.append(";");
		sb.append(this.cliente);
		sb.append(";");
		sb.append(this.servicos.size());
		sb.append(";");
		sb.append(orcamento);
		sb.append("\r\n");
		
		return sb.toString(); 
	}
	
	public void imprimir() {

		System.out.println("");
		System.out.println("Cliente: " + this.cliente);
		System.out.println("Agendamento: " + this.toString());
		System.out.println("Qtd. Servicos: " + this.servicos.size());
		System.out.println("Servicos: ");
		
		for (Servico servico: this.servicos ) {
			System.out.println("   - " + servico.getNome() );
		}
	}


}
