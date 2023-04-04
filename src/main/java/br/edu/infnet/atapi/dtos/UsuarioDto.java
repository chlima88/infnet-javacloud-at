package br.edu.infnet.atapi.dtos;

import java.util.List;

import br.edu.infnet.atapi.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDto {

	@Schema(description = "Nome do usuario", example = "Rubens Lopes de Oliveira")
	@NotBlank(message = "Não pode ser nulo")
	private String nome;
	@Schema(description = "Email do usuario", example = "rlo@gmail.com")
	@NotBlank(message = "Não pode ser nulo")
	private String email;
	@Schema(description = "Senha do usuario", example = "UB6egdXKSt")
	@NotBlank(message = "Não pode ser nulo")
	private String senha;
	@Schema(description = "Lista de caracteristicas do usuario", type = "List"  , example = "[\"Ele\",\"Mec\"]")
	@NotNull(message = "Não pode ser nulo")
	private List<String> caracteristicas;
	@Schema(description = "Tipo de usuario", example = "A")
	@NotBlank(message = "Não pode ser nulo")
	private String tipo;
	@Schema(description = "Empresa do usuario", example = "Infnet")
	@NotBlank(message = "Não pode ser nulo")
	private String empresa;
	@Schema(description = "URL da imagem do usuario", example = "https://cj-lab.s3.amazonaws.com/1680549667889_ThisPerson1.PNG")
	private String imagemUrl;
	@NotNull(message = "Não pode ser nulo")
	private Endereco endereco;
}
