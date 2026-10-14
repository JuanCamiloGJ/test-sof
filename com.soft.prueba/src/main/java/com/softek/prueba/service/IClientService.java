package com.softek.prueba.service;

import java.util.List;
import com.softek.prueba.model.dtos.ClientDto;

public interface IClientService {
	List<ClientDto> findAll();
	 int  save(ClientDto client);
	 int deleteClient(Long id);
}
