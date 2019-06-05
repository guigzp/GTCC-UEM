package br.com.gtcc.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProfessor;

	@Column(nullable = false)
	@NotBlank(message = "Nome é uma informação obrigatória")
	private String nome;

	@Column(nullable = false)
	@Email(message = "Entre com um email válido")
	@NotBlank(message = "Email é uma informação obrigatória")
	private String email;

	@Column
	private Boolean coordenador;

	@Column
	private Boolean orientador;

	@Column
	private Boolean avaliador;

	@Column
	private Boolean coordenador_do_curso;

	public Professor() {

	}

	public Professor(String nome, String email, Boolean coordenador, Boolean orientador,
					 Boolean avaliador, Boolean coordenador_do_curso) {
		super();
		this.nome = nome;
		this.email = email;
		this.coordenador = coordenador;
		this.orientador = orientador;
		this.avaliador = avaliador;
		this.coordenador_do_curso = coordenador_do_curso;
	}

	public Long getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(Long idProfessor) {
		this.idProfessor = idProfessor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;

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

}