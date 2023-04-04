package br.edu.infnet.atapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadDto {
	
	@Schema(description = "URL da imagem enviada", example = "https://cj-lab.s3.amazonaws.com/1680549667889_ThisPerson1.PNG")
	private String imagemUrl;
	
	public UploadDto(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}
}
