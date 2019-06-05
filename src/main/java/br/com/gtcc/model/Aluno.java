package br.com.gtcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Alan Lopes
 * Aluno
 */
@Entity
@PrimaryKeyJoinColumn(name="idUsuario")
public class Aluno extends Usuario{
	
	@Column(nullable = false)
	@NotNull(message = "RA é uma informação obrigatória")
	private Integer ra;
	
	private String curso;
	
	public Aluno() {}

	public Aluno(@NotNull(message = "RA é uma informação obrigatória") Integer ra, String curso) {
		super();
		this.ra = ra;
		this.curso = curso;
	}

	public Integer getRa() {
		return ra;
	}

	public void setRa(Integer ra) {
		this.ra = ra;
	}
	
	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ra == null) ? 0 : ra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (ra == null) {
			if (other.ra != null)
				return false;
		} else if (!ra.equals(other.ra))
			return false;
		return true;
	}
	
	
	
}
