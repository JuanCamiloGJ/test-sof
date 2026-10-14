package com.chubb.test.model.dtos;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Client", description = "Client data exchanged by the API")
@Getter
@Setter
public class ClientDto {
	@Schema(description = "Client identifier", example = "1")
	int id;
	@Schema(description = "Client first name", example = "John")
	String firstName;
	@Schema(description = "Client last name", example = "Doe")
	String lastName;
	@Schema(description = "Client surname (second last name)", example = "Smith")
	String surname;
	@Schema(description = "Client phone number", example = "555-0100")
	String phone;
	

	 public ClientDto(int id, String firstName, String lastName, String surname, String phone){
		super();
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.surname = surname; 
		this.phone = phone;
	 }
}
