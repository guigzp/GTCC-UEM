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
    @NotBlank(message = "Horário da defesa é uma informação obrigatória")
    private String horario;


	@Column(nullable = false)
	@NotBlank(message = "Local é uma informação obrigatória")
	private String local;

    @Column(nullable = false, columnDefinition = "int(1) default 1")
	private int ativo;
    
    @Column(nullable = false, columnDefinition = "int(1) default 0")
	private int apresentado;
    
	@Column(nullable = true)
    @NotNull(message = "Ano é uma informação obrigatória")
    private int ano;
    
    public Agendamento() {}

    public Agendamento(
			@NotNull(message = "Data de agendamento é uma informação obrigatória") LocalDate dataDefesa,
			@NotBlank(message = "Horário da defesa é uma informação obrigatória") String horario,
			@NotBlank(message = "Local é uma informação obrigatória") String local) {
		this.dataDefesa = dataDefesa;
		this.horario = horario;
		this.local = local;
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
	
	 public int getApresentado() {
			return apresentado;
	}

	public void setApresentado(int apresentado) {
		this.apresentado = apresentado;
	}
    
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String getHorario() {
		return horario;
	}
	
	public void setHorario(String horario) {
		this.horario = horario;
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
