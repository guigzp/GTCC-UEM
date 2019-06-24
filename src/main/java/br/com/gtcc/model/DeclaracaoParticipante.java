package br.com.gtcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class DeclaracaoParticipante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "O nome do participante é uma informação obrigatória")
	private String nomeParticipante;
	
	@Column(nullable = false)
	@NotNull(message = "O RA do participante é uma informação obrigatória")
	private String raParticipante;
	
	@Column(nullable = false)
	@NotNull(message = "O curso do participante é uma informação obrigatória")
	private String cursoParticipante;
	
	@Column(nullable = false)
	@NotNull(message = "O carga horaria é uma informação obrigatória")
	private Double cargaHoraria;
	
	@Column
	private String cursoVisto;
	
	@Column
	private Integer ano;

	public DeclaracaoParticipante() {
		super();
	}
	
	public DeclaracaoParticipante(
			@NotNull(message = "O nome do participante é uma informação obrigatória") String nomeParticipante,
			@NotNull(message = "O RA do participante é uma informação obrigatória") String raParticipante,
			@NotNull(message = "O curso do participante é uma informação obrigatória") String cursoParticipante,
			@NotNull(message = "O carga horaria é uma informação obrigatória") Double cargaHoraria, String cursoVisto, Integer ano) {
		super();
		this.nomeParticipante = nomeParticipante;
		this.raParticipante = raParticipante;
		this.cursoParticipante = cursoParticipante;
		this.cargaHoraria = cargaHoraria;
		this.cursoVisto = cursoVisto;
		this.ano = ano;
	}

	public String getNomeParticipante() {
		return nomeParticipante;
	}

	public void setNomeParticipante(String nomeParticipante) {
		this.nomeParticipante = nomeParticipante;
	}

	public String getRaParticipante() {
		return raParticipante;
	}

	public void setRaParticipante(String raParticipante) {
		this.raParticipante = raParticipante;
	}

	public String getCursoParticipante() {
		return cursoParticipante;
	}
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCursoVisto() {
		return cursoVisto;
	}

	public void setCursoVisto(String cursoVisto) {
		this.cursoVisto = cursoVisto;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public void setCursoParticipante(String cursoParticipante) {
		this.cursoParticipante = cursoParticipante;
	}

	public Double getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Double cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
}