package br.com.gtcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gtcc.model.MapaNotas;

public interface MapaNotasRepository extends JpaRepository<MapaNotas, Long>{
	

	public List<MapaNotas> findByAno(Integer ano);
}
