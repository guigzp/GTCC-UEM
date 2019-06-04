package br.com.gtcc.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gtcc.repository.MapaNotasRepository;


@Service
public class MapaNotasService {
	
	@Autowired
	private MapaNotasRepository mapaNotasRepository;
	
	
}
