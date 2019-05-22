package br.com.gtcc.repository.perfilUsuario;

import java.util.List;

import br.com.gtcc.model.PerfilUsuario;
import br.com.gtcc.repository.filter.PerfilUsuarioFilter;

public interface PerfilUsuarioRepositoryQuery {

	public List<PerfilUsuario> filtrar(PerfilUsuarioFilter perfilUsuarioFilter);	
	
	
}
