package com.gerenciamento_de_senhas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento_de_senhas.entity.GerenciamentoEntity;

public interface GerenciamentoRepository extends JpaRepository<GerenciamentoEntity, Integer>{
	
}
