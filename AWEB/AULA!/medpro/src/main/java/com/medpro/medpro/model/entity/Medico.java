package com.medpro.medpro.model.entity;

import com.medpro.medpro.enums.Especialidade;
import com.medpro.medpro.model.dto.DadosAtualizacaoMedico;
import com.medpro.medpro.model.dto.DadosCadastroMedico;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{4,6}")
    private String crm;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Especialidade especialidade;
    @Embedded
    @NotNull
    @Valid
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.telefone = dados.telefone();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados){
        if(dados.nome() != null){
            if(dados.nome().isBlank()){
                throw new IllegalArgumentException("Nome não pode estar em branco");
            }
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            if(dados.telefone().isBlank()){
                throw new IllegalArgumentException("Telefone não pode estar em branco");
            }
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir(){
        this.ativo = false;
    }

}
