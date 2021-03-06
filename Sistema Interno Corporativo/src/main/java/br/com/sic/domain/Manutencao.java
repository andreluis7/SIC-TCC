package br.com.sic.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.sic.enumeradores.StatusChamado;

@SuppressWarnings("serial")
@Entity
public class Manutencao extends GenericDomain {
	@Column(length = 255, nullable = false)
	private String observacao;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAbertura;

	@OneToOne
	@JoinColumn(nullable = false)
	private Problema problema;

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusChamado statusChamado;

	@Transient
	private String dataHoraFormatada;
	
	private Boolean atendimento;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}

	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}

	public Problema getProblema() {
		return problema;
	}

	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDataHoraFormatada() {
		return dataHoraFormatada;
	}

	public void setDataHoraFormatada(String dataHoraFormatada) {
		this.dataHoraFormatada = dataHoraFormatada;
	}

	public StatusChamado getStatusChamado() {
		return statusChamado;
	}

	public void setStatusChamado(StatusChamado statusChamado) {
		this.statusChamado = statusChamado;
	}

	public Boolean getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Boolean atendimento) {
		this.atendimento = atendimento;
	}
	
}
