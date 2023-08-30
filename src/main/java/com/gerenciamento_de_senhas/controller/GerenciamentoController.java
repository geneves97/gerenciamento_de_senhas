package com.gerenciamento_de_senhas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciamento_de_senhas.business.GerenciamentoBusiness;
import com.gerenciamento_de_senhas.entity.GerenciamentoEntity;

@RestController
public class GerenciamentoController {
	
	@Autowired 
	GerenciamentoBusiness gerenciamentoBusiness;
	
	@GetMapping("/{id}")
	public GerenciamentoEntity get(@PathVariable (value = "id") Integer id) {
		return gerenciamentoBusiness.findById(id);
	}
	
	@GetMapping
	public List<GerenciamentoEntity> get() {
		return gerenciamentoBusiness.findAll();
	}
	
	@PostMapping
	public GerenciamentoEntity post(@RequestBody GerenciamentoEntity gerenciamentoEntity) throws Exception {
		return  gerenciamentoBusiness.save(gerenciamentoEntity);
	}

	@GetMapping("/senhas-decrypted/{userId}")
	public ResponseEntity<List<GerenciamentoBusiness.DecryptedPassword>> findAllDecryptedSenhasById(@PathVariable Integer userId) {
		List<GerenciamentoBusiness.DecryptedPassword> decryptedPasswords = gerenciamentoBusiness.findAllDecryptedSenhasById(userId);
		return ResponseEntity.ok(decryptedPasswords);
	}
	
}
