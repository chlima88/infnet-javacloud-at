package br.edu.infnet.atapi.model.exceptions;

public class ServicoIndefinidoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServicoIndefinidoException(String mensagem) {
		super("[ERRO] " + mensagem);
	}
}
