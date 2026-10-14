package com.chubb.test.service;

import java.util.List;
import com.chubb.test.model.dtos.ClientDto;

public interface IClientService {
	List<ClientDto> findAll();
	 int  save(ClientDto client);
	 int deleteClient(Long id);
}
