package br.com.gtcc.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.gtcc.util.AdapterLocalDate;

/**
 * 
 * @author Grupo 03 - Ana Claudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */

@Entity
public class Agendamento {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @OneToOne
	private FichaIdentificacao fichaIdentificacao;

    @Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "Data de agendamento é uma informação obrigatória")
	private LocalDate dataDefesa;

    @Column(nullable = false)
	@NotBlank(message = "Local é uma informação obrigatória")
	private String local;

    @Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;
    
    @Column(nullable = true)
    @NotNull(message = "Ano é uma informação obrigatória")
    private int ano;
    
    public Agendamento() {}

	public Agendamento(Long id, FichaIdentificacao fichaIdentificacao,
			@NotNull(message = "Data de agendamento é uma informação obrigatória") LocalDate dataDefesa,
			@NotBlank(message = "Local é uma informação obrigatória") String local, int ativo,
			@NotNull(message = "Ano é uma informação obrigatória") int ano) {
		super();
		this.id = id;
		this.fichaIdentificacao = fichaIdentificacao;
		this.dataDefesa = dataDefesa;
		this.local = local;
		this.ativo = ativo;
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

	public LocalDate getDataDefesa() {
		return dataDefesa;
	}

	public void setDataDefesa(LocalDate dataDefesa) {
		this.dataDefesa = dataDefesa;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
    
    
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
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
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
