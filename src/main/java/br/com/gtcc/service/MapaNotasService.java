package br.com.gtcc.service;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.model.MapaNotas;
import br.com.gtcc.repository.MapaNotasRepository;

/**
 * 
 * @author Alan Lopes
 *
 */
@Service
public class MapaNotasService {
	
	@Autowired
	private MapaNotasRepository mapaNotasRepository;
	
	/**
	 * Retorna todos os registros da entidade mapa de notas
	 * @return
	 */
	public List<MapaNotas> findAll(){
		return mapaNotasRepository.findAll();
	}
	
	public MapaNotas adicionar(@Valid MapaNotas mapaNotas)
	{
		return this.mapaNotasRepository.save(mapaNotas);
	}
	
	
}
