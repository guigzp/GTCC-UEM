package br.com.gtcc.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * 
 * @author Alan Lopes
 * Professor
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="idUsuario")
public class Professor extends Usuario{
	
	@Column(nullable = false)
	private Integer matricula;

	@Column
	private Boolean coordenador;

	@Column
	private Boolean orientador;

	@Column
	private Boolean avaliador;

	@Column
	private Boolean coordenador_do_curso;
	
	public Professor() {}

	
	
	public Professor(Integer matricula, Boolean coordenador, Boolean orientador, Boolean avaliador,
			Boolean coordenador_do_curso) {
		super();
		this.matricula = matricula;
		this.coordenador = coordenador;
		this.orientador = orientador;
		this.avaliador = avaliador;
		this.coordenador_do_curso = coordenador_do_curso;
	}



	public Integer getMatricula() {
		return matricula;
	}



	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}



	public Boolean getCoordenador() {
		return coordenador;
	}



	public void setCoordenador(Boolean coordenador) {
		this.coordenador = coordenador;
	}



	public Boolean getOrientador() {
		return orientador;
	}



	public void setOrientador(Boolean orientador) {
		this.orientador = orientador;
	}



	public Boolean getAvaliador() {
		return avaliador;
	}



	public void setAvaliador(Boolean avaliador) {
		this.avaliador = avaliador;
	}



	public Boolean getCoordenador_do_curso() {
		return coordenador_do_curso;
	}



	public void setCoordenador_do_curso(Boolean coordenador_do_curso) {
		this.coordenador_do_curso = coordenador_do_curso;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Professor other = (Professor) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
}
