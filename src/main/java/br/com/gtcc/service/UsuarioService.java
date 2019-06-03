package br.com.gtcc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.Usuario;
import br.com.gtcc.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Responsável por retornar todos os usuários do banco de dados
	 * @return
	 */
	public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
	
	/**
	 * Retorna todos os usuários ativos
	 * @return
	 */
	public List<Usuario> listarTodosAtivos(){
		return usuarioRepository.findByAtivo(1);
	}
     
	/**
	 * Busca o usuário de acorodo com o email digitado
	 * @param email
	 * @return
	 */
	public Usuario buscarPorEmail(String email) {
		
			List<Usuario> usuarios = 	usuarioRepository.findByEmail(email);
			if(!usuarios.isEmpty())
				return usuarios.get(0);
			else
				return null;
	}
	/**
	 * Busca o usuário de acordo com o usarName digitado
	 * @param userName
	 * @return
	 */
	public Usuario buscarPorUsername(String userName) {

		List<Usuario> usuarios = 	usuarioRepository.findByNomeUsuario(userName);
		if(!usuarios.isEmpty())
			return usuarios.get(0);
		else
			return null;
}

	/**
	 * Buscar o usuário de acordo com o id digitado
	 * @param id
	 * @return
	 */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
     
    /**
     * Reponsável por cadastrar um usuário
     * @param usuario
     * @return
     */
    public Usuario adicionar(@Valid Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }
    
    public Usuario atualizar(@Valid Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }     
	
}
