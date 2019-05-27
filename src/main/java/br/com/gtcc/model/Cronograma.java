package br.com.gtcc.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.gtcc.util.AdapterLocalDate;

@Entity
public class Cronograma {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(nullable = false)
    @NotNull(message = "Fase é uma informação obrigatória")
    private int fase;
    
    @Column(nullable = false)
    @NotNull(message = "Descrição é uma informação obrigatória")
    private String atividade;
    
    @Column(nullable = true)
    private String tamanho;
    
    @Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(nullable = true)
    private LocalDate dataInicioEntrega;
    
    @Convert(converter = AdapterLocalDate.class)
	@DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(nullable = false)
    @NotNull(message = "Data final de entrega é uma informação obrigatória")
    private LocalDate dataFinalEntrega;

	public Cronograma(Long id, @NotNull(message = "Fase é uma informação obrigatória") int fase,
			@NotNull(message = "Descrição é uma informação obrigatória") String atividade, String tamanho,
			LocalDate dataInicioEntrega,
			@NotNull(message = "Data final de entrega é uma informação obrigatória") LocalDate dataFinalEntrega) {
		super();
		this.id = id;
		this.fase = fase;
		this.atividade = atividade;
		this.tamanho = tamanho;
		this.dataInicioEntrega = dataInicioEntrega;
		this.dataFinalEntrega = dataFinalEntrega;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public LocalDate getDataInicioEntrega() {
		return dataInicioEntrega;
	}

	public void setDataInicioEntrega(LocalDate dataInicioEntrega) {
		this.dataInicioEntrega = dataInicioEntrega;
	}

	public LocalDate getDataFinalEntrega() {
		return dataFinalEntrega;
	}

	public void setDataFinalEntrega(LocalDate dataFinalEntrega) {
		this.dataFinalEntrega = dataFinalEntrega;
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
		Cronograma other = (Cronograma) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
