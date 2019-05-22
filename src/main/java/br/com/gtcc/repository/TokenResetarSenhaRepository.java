package br.com.gtcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.TokenResetarSenha;

@Repository
public interface TokenResetarSenhaRepository extends JpaRepository<TokenResetarSenha, Long> {
	
	TokenResetarSenha findByToken(String token);
	
}
