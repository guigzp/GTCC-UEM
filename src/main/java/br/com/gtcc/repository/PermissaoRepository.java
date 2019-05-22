package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gtcc.model.Permissao;
import br.com.gtcc.model.Usuario;
import br.com.gtcc.repository.permissao.PermissaoRepositoryQuery;

@Repository
public interface PermissaoRepository extends JpaRepository<Usuario, Long>, PermissaoRepositoryQuery{
	
	public List<Permissao> findByNome(String nome);

}
