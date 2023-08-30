package com.gerenciamento_de_senhas.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento_de_senhas.entity.GerenciamentoEntity;
import com.gerenciamento_de_senhas.entity.UsuarioEntity;
import com.gerenciamento_de_senhas.repository.GerenciamentoRepository;
import com.gerenciamento_de_senhas.repository.UsuarioRepository;

@Service
public class UsuarioBusiness {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private static final int MAX_PASSWORDS_PER_USER = 3;
    
    public UsuarioEntity save(UsuarioEntity usuarioEntity) throws Exception {
		usuarioEntity.setNome(usuarioEntity.getNome());
		return usuarioRepository.save(usuarioEntity);
	}

	public UsuarioEntity findById(Integer id) {
		return usuarioRepository.findById(id).get();
	}

	public List<UsuarioEntity> findAll() {
		return usuarioRepository.findAll();
	}
	 public List<GerenciamentoEntity> findAllSenhasById(Integer id) {
	        UsuarioEntity usuario = findById(id);
	        return usuario.getSenhas();
	   }
	 public boolean exceededMaxPasswords(Integer userId) {
		    UsuarioEntity usuario = findById(userId);
		    return usuario.getSenhas().size() >= MAX_PASSWORDS_PER_USER;
		}
}
