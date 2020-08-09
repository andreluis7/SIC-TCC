package br.com.sic.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.sic.dao.ProblemaDAO;
import br.com.sic.domain.Problema;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProblemaBean implements Serializable {
	private Problema problema;
	private List<Problema> problemas;

	
	
	public Problema getProblema() {
		return problema;
	}

	public void setProblema(Problema problema) {
		this.problema = problema;
	}

	public List<Problema> getProblemas() {
		return problemas;
	}

	public void setProblemas(List<Problema> problemas) {
		this.problemas = problemas;
	}

	@PostConstruct
	public void listar() {
		try {
			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemas = problemaDAO.listar("codigo");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os problemas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			problema = new Problema();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo problema");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			problema = (Problema) evento.getComponent().getAttributes().get("problemaSelecionado");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar um problema");
		}
	}

	public void salvar() {
		try {
			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemaDAO.merge(problema);

			problemas = problemaDAO.listar("codigo");

			problema = new Problema();

			Messages.addGlobalInfo("Problema salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o problema");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			problema = (Problema) evento.getComponent().getAttributes().get("problemaSelecionado");

			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemaDAO.excluir(problema);

			problemas = problemaDAO.listar();

			Messages.addGlobalInfo("Problema removido com sucesso");
		} catch (Exception erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o problema");
			erro.printStackTrace();
		}
	}

}
