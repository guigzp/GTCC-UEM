package br.com.gtcc.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gtcc.util.AdapterLocalDate;
/**
 * 
 * @author Alan Lopes
 * Classe entidade 
 * 
 * 
 */
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	@Length(min = 14,max = 15, message = "Informe um telefone válido")
	private String telefone;

	@Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;

	//decidiremos no futuro

	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_permissao",
            joinColumns =  @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns =  @JoinColumn(name = "permissao_id", referencedColumnName = "id"))
    private Set<Permissao> permissoes;

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }


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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
