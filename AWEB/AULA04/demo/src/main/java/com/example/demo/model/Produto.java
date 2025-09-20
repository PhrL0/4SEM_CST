package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Garante que não seja nulo e que nem tenha espaço em branco
    @NotBlank(message = "O nome não pode ser nulo")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "A descrição não pode ser nulo")
    @Column(nullable = false, length = 250)
    private String description;

    //Garante que seja maior que zero e nulo
    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal price;

    //Garante que seja maior que zero e nulo
    @NotNull(message = "A quantidade não pode ser nula.")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    @Column(nullable = false)
    private Integer stockQuantity;
}
