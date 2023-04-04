package br.edu.infnet.atapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoDto {
	
	@Schema(description = "cep", example = "76813570")
	@NotBlank(message = "Não pode ser nulo")
	private String cep;
	@Schema(description = "Nome da rua", example = "Rua Ramiro Barcelos")
	private String logradouro;
	@Schema(description = "Bairro", example = "Mariana")
	private String bairro;
	@Schema(description = "Cidade", example = "Porto Velho")
	private String localidade;
	@Schema(description = "Sigla do estado", example = "RO")
	private String uf;

}
