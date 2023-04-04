package br.edu.infnet.atapi.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(hidden = true)
public class AgendamentoDto {

	@Schema(description = "Tempo previsto do atendimento.", example = "30")
	@NotNull(message = "Não pode ser nulo")
	private Integer duracaoEmMinutos;
	@Schema(description = "Data e hora no agendamento (Formato ISO8610)", type = "String", example = "2023-04-04T17:54:00")
	@NotNull(message = "Não pode ser nulo")
	@JsonFormat
	private LocalDateTime data;
	@Schema(description = "Agendamento confirmado", example = "true")
	private boolean confirmado;
	@Schema(description = "Id do usuario do agendamento", example = "1")
	@NotNull(message = "Não pode ser nulo")
	private Integer usuarioId;
	@Schema(description = "Id do cliente do agendamento", example = "1")
	@NotNull(message = "Não pode ser nulo")
	private Integer clienteId;
	@Schema(description = "Lista de Id's de servicos do agendamento", example = "[1,2]")
	@NotNull(message = "Não pode ser nulo")
	private List<Integer> servicos;
}
