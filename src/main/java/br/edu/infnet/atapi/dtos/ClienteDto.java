package br.edu.infnet.atapi.dtos;

import br.edu.infnet.atapi.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteDto {

	@Schema(description = "Nome do cliente", example = "Rubens Lopes de Oliveira")
	@NotNull(message = "Não pode ser nulo")
	@NotBlank(message = "Não pode ser nulo")
	private String nome;
	@Schema(description = "Documento do cliente", example = "87601606244")
	@NotBlank(message = "Não pode ser nulo")
	private String documento;
	@Schema(description = "Contato do cliente", example = "69989599192")
	@NotBlank(message = "Não pode ser nulo")
	private String contato;
	@NotNull(message = "Não pode ser nulo")
	private Endereco endereco;
}
