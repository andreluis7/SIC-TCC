package br.com.sic.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Chamado extends GenericDomain {

	@Column(length = 255)
	private String observacao;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraRecebida;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraFinalizada;

	@OneToOne
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@OneToOne
	@JoinColumn(nullable = false)
	private Manutencao manutencao;
	
	@Transient
	private String dataHoraRecebidaFormatada;
	
	@Transient
	private String dataHoraFinalizadaFormatada;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataHoraRecebida() {
		return dataHoraRecebida;
	}

	public void setDataHoraRecebida(Date dataHoraRecebida) {
		this.dataHoraRecebida = dataHoraRecebida;
	}

	public Date getDataHoraFinalizada() {
		return dataHoraFinalizada;
	}

	public void setDataHoraFinalizada(Date dataHoraFinalizada) {
		this.dataHoraFinalizada = dataHoraFinalizada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	public String getDataHoraRecebidaFormatada() {
		return dataHoraRecebidaFormatada;
	}

	public void setDataHoraRecebidaFormatada(String dataHoraRecebidaFormatada) {
		this.dataHoraRecebidaFormatada = dataHoraRecebidaFormatada;
	}

	public String getDataHoraFinalizadaFormatada() {
		return dataHoraFinalizadaFormatada;
	}

	public void setDataHoraFinalizadaFormatada(String dataHoraFinalizadaFormatada) {
		this.dataHoraFinalizadaFormatada = dataHoraFinalizadaFormatada;
	}
	
}
