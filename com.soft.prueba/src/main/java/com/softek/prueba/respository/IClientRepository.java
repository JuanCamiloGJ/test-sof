package com.softek.prueba.respository;

import java.util.List;

import com.softek.prueba.model.Client;

public interface IClientRepository {

    public List<Client> findAll();

    public int save(Client client);
    int deleteClient(Long id);
}
