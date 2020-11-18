package br.com.sic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.sic.enumeradores.TipoPrioridade;

@SuppressWarnings("serial")
@Entity
public class Problema extends GenericDomain {
	@Column(length = 50, nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoPrioridade tipoPrioridade;
	
	@Column(length = 50, nullable = false)
	private String tempoAproximadoManutencao;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoPrioridade getTipoPrioridade() {
		return tipoPrioridade;
	}

	public void setTipoPrioridade(TipoPrioridade tipoPrioridade) {
		this.tipoPrioridade = tipoPrioridade;
	}

	public String getTempoAproximadoManutencao() {
		return tempoAproximadoManutencao;
	}

	public void setTempoAproximadoManutencao(String tempoAproximadoManutencao) {
		this.tempoAproximadoManutencao = tempoAproximadoManutencao;
	}
	
}
