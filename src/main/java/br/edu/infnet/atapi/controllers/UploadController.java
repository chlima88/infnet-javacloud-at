package br.edu.infnet.atapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.edu.infnet.atapi.dtos.UploadDto;
import br.edu.infnet.atapi.services.StorageService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "upload")
public class UploadController {

	@Autowired
	private StorageService storageService;
	
	@PostMapping(path = "/api/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UploadDto incluirImagem(
			@RequestParam("imagem") MultipartFile imagem
		) throws Exception {
		try {
			
			String url = storageService.putObject(imagem);
			return new UploadDto(url);
			
		} catch(Exception error) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					error.getMessage(),
					error);
		}
	}
	
}
