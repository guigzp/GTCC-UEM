package br.com.gtcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Alan Lopes
 * Ficha de Indentificação  
 * 
 * 
 */
@Entity
public class FichaIdentificacao {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Titulo do trabalho é uma informação obrigatória")
	private String tituloTrabalho;
	
	@Column(nullable = false)
	@NotBlank(message = "Area de concentração é uma informação obrigatória")
	private String areaConcentracao;
	
	@Column(nullable = false)
	@NotNull(message = "Ano é uma informação obrigatória")
	private Integer ano;
	
	@OneToOne
	@NotNull(message = "Aluno é uma informação obrigatória")
	private Aluno aluno;

	@OneToOne
	@NotNull(message = "Orientador é uma informação obrigatória")
	private Professor orientador;
	
	@OneToOne
	@NotNull(message = "1° Avaliador é uma informação obrigatória")
	private Professor avaliador1;
	
	@OneToOne
	@NotNull(message = "2° Avaliador é uma informação obrigatória")
	private Professor avaliador2;
	
	public FichaIdentificacao() {}

	public FichaIdentificacao(
			@NotBlank(message = "Titulo do trabalho é uma informação obrigatória") String tituloTrabalho,
			@NotBlank(message = "Area de concentração é uma informação obrigatória") String areaConcentracao,
			@NotNull(message = "Ano é uma informação obrigatória") Integer ano) {
		super();
		this.tituloTrabalho = tituloTrabalho;
		this.areaConcentracao = areaConcentracao;
		this.ano = ano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloTrabalho() {
		return tituloTrabalho;
	}

	public void setTituloTrabalho(String tituloTrabalho) {
		this.tituloTrabalho = tituloTrabalho;
	}

	public String getAreaConcentracao() {
		return areaConcentracao;
	}

	public void setAreaConcentracao(String areaConcentracao) {
		this.areaConcentracao = areaConcentracao;
	}
	

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getOrientador() {
		return orientador;
	}

	public void setOrientador(Professor orientador) {
		this.orientador = orientador;
	}

	public Professor getAvaliador1() {
		return avaliador1;
	}

	public void setAvaliador1(Professor avaliador1) {
		this.avaliador1 = avaliador1;
	}

	public Professor getAvaliador2() {
		return avaliador2;
	}

	public void setAvaliador2(Professor avaliador2) {
		this.avaliador2 = avaliador2;
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
		FichaIdentificacao other = (FichaIdentificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
