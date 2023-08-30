package com.gerenciamento_de_senhas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerenciamento_de_senhas.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{
	
}
