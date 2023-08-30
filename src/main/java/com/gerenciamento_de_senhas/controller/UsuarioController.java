package com.gerenciamento_de_senhas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.gerenciamento_de_senhas.business.UsuarioBusiness;
import com.gerenciamento_de_senhas.entity.GerenciamentoEntity;
import com.gerenciamento_de_senhas.entity.UsuarioEntity;

@RestController
public class UsuarioController {
    
    @Autowired
    private UsuarioBusiness usuarioBusiness;
    
    @PostMapping("/usuario")
    public UsuarioEntity criarUsuario(@RequestBody UsuarioEntity usuarioEntity) throws Exception {
        return usuarioBusiness.save(usuarioEntity);
    }
    
    @GetMapping("/usuario/{id}")
	public UsuarioEntity get(@PathVariable (value = "id") Integer id) {
		return usuarioBusiness.findById(id);
	}
	
	@GetMapping("/usuario")
	public List<UsuarioEntity> get() {
		return usuarioBusiness.findAll();
	}
	@GetMapping("/{id}/senhas")
	    public List<GerenciamentoEntity> buscarTodasSenhasPorId(@PathVariable Integer id) {
	    UsuarioEntity usuario = usuarioBusiness.findById(id);
	    return usuario.getSenhas();
	    }

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<String> deleteUsuarioById(@PathVariable Integer id) {
		try {
			usuarioBusiness.deleteUsuarioById(id);
			return ResponseEntity.ok("Usuário e suas senhas foram excluídos com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuário e suas senhas.");
		}
	}
}
