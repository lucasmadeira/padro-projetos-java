package com.lucasmadeira.labpadroesprojetospring.service;

import com.lucasmadeira.labpadroesprojetospring.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar (Long id);
}
