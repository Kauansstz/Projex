package com.kauan.projex.utils;

import jakarta.persistence.*;

@Entity
public class Tecnologia{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String nome;
}