package com.softek.prueba.model.dtos;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ServiceResponse", description = "Uniform outcome of a client operation")
@Data
public class ServiceResponse {
	@Schema(description = "Whether the operation succeeded", example = "true")
	Boolean success;
	@Schema(description = "Human readable outcome message", example = "Client saved with sucesss")
	String message;
}
