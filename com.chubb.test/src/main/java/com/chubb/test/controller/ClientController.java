package com.chubb.test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.test.model.dtos.ClientDto;
import com.chubb.test.model.dtos.ServiceResponse;
import com.chubb.test.service.IClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Client", description = "Client management operations")
@RestController
@CrossOrigin(origins = { "http://localhost:8080" })
@RequestMapping("/api")
public class ClientController {
    private final IClientService clientService;

	public ClientController(IClientService clientService) {
		this.clientService = clientService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@Operation(summary = "List clients", description = "Returns every client stored in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Clients retrieved successfully", content = @Content(
					array = @ArraySchema(schema = @Schema(implementation = ClientDto.class)))) })
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getClients() {
        return ResponseEntity.ok(clientService.findAll());

    }

	@CrossOrigin(origins = "http://localhost:4200")
	@Operation(summary = "Create client", description = "Persists a new client with the given data and reports the outcome.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Client saved successfully", content = @Content(
					schema = @Schema(implementation = ServiceResponse.class))),
			@ApiResponse(responseCode = "400", description = "Malformed payload: the client body is missing or unreadable", content = @Content(
					schema = @Schema(implementation = ServiceResponse.class))) })
    @PostMapping("/client")
    public ResponseEntity<ServiceResponse> saveClient(@RequestBody ClientDto client) {
        ServiceResponse serviceReponse = new ServiceResponse();
        int result = clientService.save(client);
        if (result == 1) {
            serviceReponse.setSuccess(true);
            serviceReponse.setMessage("Client saved with sucesss");
        }

        return new ResponseEntity<>(serviceReponse, HttpStatus.OK);

    }

	@CrossOrigin(origins = "http://localhost:4200")
	@Operation(summary = "Delete client", description = "Removes the client identified by the given id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Client deleted successfully", content = @Content(
					schema = @Schema(implementation = ServiceResponse.class))),
			@ApiResponse(responseCode = "404", description = "No client exists with the given id", content = @Content(
					schema = @Schema(implementation = ServiceResponse.class))) })
    @DeleteMapping("/client/{id}")
    public ResponseEntity<ServiceResponse> deleteClient(
            @Parameter(name = "id", description = "Identifier of the client to delete", example = "1", required = true) @PathVariable Long id) {
        ServiceResponse serviceReponse = new ServiceResponse();
        int result = clientService.deleteClient(id);
        if (result == 1) {
            serviceReponse.setSuccess(true);
            serviceReponse.setMessage("Client deleted with sucesss");
            return new ResponseEntity<>(serviceReponse, HttpStatus.NO_CONTENT);
        } else {
            serviceReponse.setSuccess(false);
            serviceReponse.setMessage("Client not found");
            return new ResponseEntity<>(serviceReponse, HttpStatus.NOT_FOUND);
        }

    }

}
