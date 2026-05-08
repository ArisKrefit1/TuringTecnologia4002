package com.api.controller;

import com.api.model.Participante;
import com.api.service.ParticipanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/participantes")
@CrossOrigin(origins = "*")
public class ParticipanteController {

    private final ParticipanteService service;

    public ParticipanteController(ParticipanteService service) {
        this.service = service;
    }

    // Formulário envia os dados
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Participante participante) {
        try {
            Participante salvo = service.registrar(participante);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    // Telão busca a lista completa
    @GetMapping
    public ResponseEntity<List<Participante>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Participante> resultado = service.buscarPorId(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", "Participante não encontrado."));
    }

    // Apaga tudo após o evento
    @DeleteMapping("/limpar")
    public ResponseEntity<?> limpar() {
        service.limparBanco();
        return ResponseEntity.ok(Map.of("mensagem", "Banco apagado com sucesso."));
    }

    // Status geral
    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok(Map.of(
                "totalRegistros", service.totalRegistros(),
                "dataHora",       LocalDateTime.now().toString(),
                "banco",          "PostgreSQL — Supabase"
        ));
    }
}
