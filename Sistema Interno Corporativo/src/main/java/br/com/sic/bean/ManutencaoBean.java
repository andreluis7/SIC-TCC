package br.com.sic.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.sic.dao.ManutencaoDAO;
import br.com.sic.dao.ProblemaDAO;
import br.com.sic.domain.Manutencao;
import br.com.sic.domain.Problema;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ManutencaoBean implements Serializable {
	private Manutencao manutencao;
	
	private List<Problema> problemas;
	private List<Manutencao> manutencoes;
	
	public Manutencao getManutencao() {
		return manutencao;
	}

	public void setManutencao(Manutencao manutencao) {
		this.manutencao = manutencao;
	}

	public List<Problema> getProblemas() {
		return problemas;
	}

	public void setProblemas(List<Problema> problemas) {
		this.problemas = problemas;
	}

	public List<Manutencao> getManutencoes() {
		return manutencoes;
	}

	public void setManutencoes(List<Manutencao> manutencoes) {
		this.manutencoes = manutencoes;
	}

	@PostConstruct
	public void listar(){
		try{
			ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
			manutencoes = manutencaoDAO.listar("codigo");
		}catch(RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as manutenções");
			erro.printStackTrace();
		}
	}
	
	public void novo() {
		try {
			manutencao = new Manutencao();

			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemas = problemaDAO.listar("codigo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar uma nova manutenção");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
			manutencao.setDataHoraAbertura(new Date());
			manutencaoDAO.merge(manutencao);
			
			manutencao = new Manutencao();
			manutencoes = manutencaoDAO.listar("codigo");
			
			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemas = problemaDAO.listar("codigo");
			
			Messages.addGlobalInfo("Manutenção salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a manutenção");
			erro.printStackTrace();
		}
	}
}
