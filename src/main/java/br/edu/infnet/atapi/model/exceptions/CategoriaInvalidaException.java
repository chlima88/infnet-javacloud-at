package br.edu.infnet.atapi.model.exceptions;

public class CategoriaInvalidaException extends Exception {
	private static final long serialVersionUID = 1L;

	public CategoriaInvalidaException(String mensagem) {
		super("[ERRO] " + mensagem);
	}
}
