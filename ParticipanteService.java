package com.api.service;

import com.api.model.Participante;
import com.api.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteService {

    private final ParticipanteRepository repository;

    public ParticipanteService(ParticipanteRepository repository) {
        this.repository = repository;
    }

    public Participante registrar(Participante p) {
        validar(p);
        return repository.save(p);
    }

    public List<Participante> listarTodos() {
        return repository.findAll();
    }

    public Optional<Participante> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void limparBanco() {
        repository.deleteAll();
    }

    public long totalRegistros() {
        return repository.count();
    }

    private void validar(Participante p) {
        if (p.getNome() == null || p.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório.");

        if (p.getNumero() == null || p.getNumero().isBlank())
            throw new IllegalArgumentException("Número é obrigatório.");

        if (p.getEmail() == null || !p.getEmail().contains("@"))
            throw new IllegalArgumentException("E-mail inválido.");
    }
}
