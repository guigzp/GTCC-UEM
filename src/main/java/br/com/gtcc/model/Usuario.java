package br.com.gtcc.model;



import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * @author Alan Lopes
 * Usuario 
 * 
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@Column(nullable = false)
	@NotBlank(message = "Nome é uma informação obrigatória")
	private String nome;

	@Column(nullable = false)
	@NotBlank(message = "Nome do usuário é uma informação obrigatória")
	private String nomeUsuario;

	@Column(nullable = false)
	@Email(message = "Entre com um email válido")
	@NotBlank(message = "Email é uma informação obrigatória")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "Senha é uma informação obrigatória")
	private String senha;

	@Column(nullable = false)
	@NotNull(message = "Tipo de usuário é uma informação obrigatória")
	private Boolean tipo;

	@Column
	private String telefone;

	@Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;

	@Transient
	private String confirmarSenha;
	
	public Usuario() {
		
	}

	public Usuario(String nome, String nomeUsuario, String email, String senha, String telefone, Boolean tipo) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nomeUsuario = nomeUsuario;
		this.telefone = telefone;
		this.tipo = tipo;
	}

	public Boolean getTipo() {
		return tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}

	public int getAtivo() {
		return ativo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public int isAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
	
	
}
