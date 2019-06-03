package br.com.gtcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Alan Lopes
 * Critério de Avaliação
 *
 */
@Entity
public class CriterioAvaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "A quantidade de avaliações uma informação obrigadtória")
	private Integer quantidadeAvaliacao;
	
	@Column(nullable = false)
	@NotNull(message = "A quantidade de notas é uma informação obrigadtória")
	private String quantidadeNotas;
	
	@Column(nullable = false)
	@NotNull(message = "Peso uma informação obrigadtória")
	private String pesoPorNota;
	
	@Column(nullable = true)
	private Double mediaFinal;
	
	@Column(nullable = false)
	@NotNull(message = "Ano uma informação obrigadtória")
	private Integer ano;
	
	

	public CriterioAvaliacao(
			@NotNull(message = "A quantidade de avaliações uma informação obrigatória") Integer quantidadeAvaliacao,
			@NotNull(message = "A quantidade de notas é uma informação obrigatória") String quantidadeNotas,
			@NotNull(message = "Peso uma informação obrigatória") String pesoPorNota, Double mediaFinal,
			@NotNull(message = "Ano uma informação obrigatória") Integer ano) {
		super();
		this.quantidadeAvaliacao = quantidadeAvaliacao;
		this.quantidadeNotas = quantidadeNotas;
		this.pesoPorNota = pesoPorNota;
		this.mediaFinal = mediaFinal;
		this.ano = ano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidadeAvaliacao() {
		return quantidadeAvaliacao;
	}

	public void setQuantidadeAvaliacao(Integer quantidadeAvaliacao) {
		this.quantidadeAvaliacao = quantidadeAvaliacao;
	}

	public String getQuantidadeNotas() {
		return quantidadeNotas;
	}

	public void setQuantidadeNotas(String quantidadeNotas) {
		this.quantidadeNotas = quantidadeNotas;
	}

	public String getPesoPorNota() {
		return pesoPorNota;
	}

	public void setPesoPorNota(String pesoPorNota) {
		this.pesoPorNota = pesoPorNota;
	}

	public Double getMediaFinal() {
		return mediaFinal;
	}

	public void setMediaFinal(Double mediaFinal) {
		this.mediaFinal = mediaFinal;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CriterioAvaliacao other = (CriterioAvaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
