package com.lucasmadeira.labpadroesprojetospring.service.impl;

import com.lucasmadeira.labpadroesprojetospring.model.Cliente;
import com.lucasmadeira.labpadroesprojetospring.model.ClienteRepository;
import com.lucasmadeira.labpadroesprojetospring.model.Endereco;
import com.lucasmadeira.labpadroesprojetospring.model.EnderecoRepository;
import com.lucasmadeira.labpadroesprojetospring.service.ClienteService;
import com.lucasmadeira.labpadroesprojetospring.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton : Injetar os componetes do spring com @Autowired
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {

        //Buscar cliente por id
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if(clienteOpt.isPresent()){
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        //verificar se o endereco do cliente ja existe
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            //Caso não exista integrar com o via cep e persistir o retorno
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        //Inserir cliente , vinculando o endereco novo ou existente
        clienteRepository.save(cliente);
    }
}
