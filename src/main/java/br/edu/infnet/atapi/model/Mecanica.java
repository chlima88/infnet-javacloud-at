package br.edu.infnet.atapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import br.edu.infnet.atapi.model.exceptions.CategoriaInvalidaException;
import br.edu.infnet.atapi.model.exceptions.PrecoBaseInvalidoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TMecanica")
public class Mecanica extends Servico {
	
	private String categoriaServico; // revisao, troca, manutencao
	private boolean problemaMotor;

	public Mecanica(String nome, String codigo, float precoBase, boolean terceirizado) throws PrecoBaseInvalidoException {
		super(nome, codigo, precoBase, terceirizado);
		this.problemaMotor = false;
	}
	
	Mecanica(){}

	@Override
	public float obterPrecoMaoDeObra() {		
		return super.getPrecoBase() * this.obterTaxaMaoDeObra() * (this.problemaMotor ? 1.15f : 1f ) ;
	}
	
	private float obterTaxaMaoDeObra() {
		float taxa = 0;
		
		switch(this.categoriaServico.toLowerCase()) {
		case "revisao":
			taxa = 1f;
			break;
		case "troca":
			taxa = 1.15f;
			break;
		case "manutencao":
			taxa = 1.3f;
			break;
		}
		
		return taxa;
	}

	
	public String getCategoriaServico() {
		return categoriaServico;
	}

	public void setCategoriaServico(String categoriaServico) throws CategoriaInvalidaException {
		List<String> validOptions = new ArrayList<String>(Arrays.asList("revisao","troca","manutencao"));
	
	if (!validOptions.contains(categoriaServico.toLowerCase())) {
		throw new CategoriaInvalidaException("Categoria invalida ["+categoriaServico+"]. "
				+ "Opcoes validas: " + validOptions);
	}
		this.categoriaServico = categoriaServico;
	}

	public boolean isProblemaMotor() {
		return problemaMotor;
	}

	public void setProblemaMotor(boolean problemaMotor) {
		this.problemaMotor = problemaMotor;
	}
	
	@Override
	public String toString() {		
		
		StringJoiner string = new StringJoiner(";");
		string.add(super.toString());
		string.add(this.categoriaServico);
		string.add(String.valueOf(this.problemaMotor));
		
		return string.toString();
	}

}
