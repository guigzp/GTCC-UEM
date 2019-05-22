package br.com.gtcc.repository.usuario;

import java.util.List;

import br.com.gtcc.model.Usuario;
import br.com.gtcc.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public List<Usuario> filtrar(UsuarioFilter usuarioFilter);	
	
	
}
