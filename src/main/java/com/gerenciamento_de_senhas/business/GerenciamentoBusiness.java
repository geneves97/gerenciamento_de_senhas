package com.gerenciamento_de_senhas.business;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciamento_de_senhas.entity.GerenciamentoEntity;
import com.gerenciamento_de_senhas.entity.UsuarioEntity;
import com.gerenciamento_de_senhas.repository.GerenciamentoRepository;

@Service
public class GerenciamentoBusiness {
	
	@Autowired
	GerenciamentoRepository gerenciamentoRepository; 
	
	@Autowired
    UsuarioBusiness usuarioBusiness;
	
	private static final String PASSWORD = "senha123";
	private static final String SALT = "salt123";
	private static final int KEY_LENGTH = 128;
			
	public GerenciamentoEntity findById(Integer id) {
		return gerenciamentoRepository.findById(id).get();
	}

	public List<GerenciamentoEntity> findAll() {
		return gerenciamentoRepository.findAll();
	}
	
	 public GerenciamentoEntity save(GerenciamentoEntity gerenciamentoEntity) throws Exception {
	        UsuarioEntity usuario = gerenciamentoEntity.getUsuario();
	        
	        if (usuarioBusiness.exceededMaxPasswords(usuario.getId())) {
	            throw new Exception("Limite de senhas excedido para o usuário.");
	        }
	        
	        gerenciamentoEntity.setSenha(encryptSenha(gerenciamentoEntity.getSenha()));
	        return gerenciamentoRepository.save(gerenciamentoEntity);
	    }
	
	public static String encryptSenha(String senha) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(),SALT.getBytes(),10000, KEY_LENGTH);
			SecretKeySpec secretKeySpec = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
					
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipher.ENCRYPT_MODE, secretKeySpec);
			
			byte[] encryptedBytes = cipher.doFinal(senha.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptSenha(String encryptedSenha) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), SALT.getBytes(), 10000, KEY_LENGTH);
			SecretKeySpec secretKeySpec = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedSenha));
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<DecryptedPassword> findAllDecryptedSenhasById(Integer userId) {
		List<GerenciamentoEntity> senhas = usuarioBusiness.findAllSenhasById(userId);
		List<DecryptedPassword> decryptedPasswords = new ArrayList<>();

		for (GerenciamentoEntity senha : senhas) {
			String decryptedSenha = decryptSenha(senha.getSenha());
			DecryptedPassword decryptedPassword = new DecryptedPassword(senha, decryptedSenha);
			decryptedPasswords.add(decryptedPassword);
		}

		return decryptedPasswords;
	}
	public class DecryptedPassword {
		private GerenciamentoEntity senha;
		private String decryptedSenha;

		public DecryptedPassword(GerenciamentoEntity senha, String decryptedSenha) {
			this.senha = senha;
			this.decryptedSenha = decryptedSenha;
		}

		public GerenciamentoEntity getSenha() {
			return senha;
		}

		public String getDecryptedSenha() {
			return decryptedSenha;
		}
	}
	@Transactional
	public void deleteSenhaById(Integer senhaId) {
		GerenciamentoEntity senha = gerenciamentoRepository.findById(senhaId).orElse(null);

		if (senha != null) {
			gerenciamentoRepository.delete(senha);
		}
	}
}
