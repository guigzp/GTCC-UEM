package br.com.gtcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Alan Lopes
 * Mapa de Notas
 *
 */
@Entity
public class MapaNotas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private FichaIdentificacao fichaIdentificacao;
	
	@Column(nullable = false)
	@NotNull(message = "Ano uma informação obrigadtória")
	private Integer ano;
	
	public MapaNotas() {}

	public MapaNotas(FichaIdentificacao fichaIdentificacao,
			@NotNull(message = "Ano uma informação obrigadtória") Integer ano) {
		super();
		this.fichaIdentificacao = fichaIdentificacao;
		this.ano = ano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public FichaIdentificacao getFichaIdentificacao() {
		return fichaIdentificacao;
	}

	public void setFichaIdentificacao(FichaIdentificacao fichaIdentificacao) {
		this.fichaIdentificacao = fichaIdentificacao;
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
		MapaNotas other = (MapaNotas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
