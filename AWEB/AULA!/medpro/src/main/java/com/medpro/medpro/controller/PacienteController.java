package com.medpro.medpro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medpro.medpro.model.dto.DadosCadastroPaciente;
import com.medpro.medpro.model.dto.DadosListagemPaciente;
import com.medpro.medpro.model.entity.Paciente;
import com.medpro.medpro.repository.PacienteRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastro(@RequestBody DadosCadastroPaciente dados) {
        pacienteRepository.save(new Paciente(dados));    
    }

    @GetMapping
    public Page<DadosListagemPaciente> Listar(Pageable pagina){
        return pacienteRepository.findAll(pagina).map(DadosListagemPaciente:: new);
    }
}
