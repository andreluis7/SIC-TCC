package br.com.sic.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import br.com.sic.dao.ChamadoDAO;
import br.com.sic.dao.ManutencaoDAO;
import br.com.sic.dao.ProblemaDAO;
import br.com.sic.domain.Chamado;
import br.com.sic.domain.Manutencao;
import br.com.sic.domain.Problema;
import br.com.sic.domain.Usuario;
import br.com.sic.enumeradores.StatusChamado;
import br.com.sic.enumeradores.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ManutencaoBean implements Serializable {
	private Manutencao manutencao;
	private Object usuarioLogado;
	private Usuario usuario;
	private Chamado chamado;

	private List<Problema> problemas;
	private List<Manutencao> manutencoes;

	private ManutencaoDAO manutencaoDAO;
	private ChamadoDAO chamadoDAO;

	private Boolean habilitaCampo = false;
	private Boolean habilitaBotao = true;

	private String mensagemDataHora = "";

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Boolean getHabilitaCampo() {
		return habilitaCampo;
	}

	public void setHabilitaCampo(Boolean habilitaCampo) {
		this.habilitaCampo = habilitaCampo;
	}

	public Boolean getHabilitaBotao() {
		return habilitaBotao;
	}

	public void setHabilitaBotao(Boolean habilitaBotao) {
		this.habilitaBotao = habilitaBotao;
	}

	public String getMensagemDataHora() {
		return mensagemDataHora;
	}

	public void setMensagemDataHora(String mensagemDataHora) {
		this.mensagemDataHora = mensagemDataHora;
	}

	@PostConstruct
	public void listar() {
		try {
			usuarioLogado = (Object) getSession().getAttribute("usurioLogado");

			usuario = (Usuario) usuarioLogado;

			manutencaoDAO = new ManutencaoDAO();
			chamadoDAO = new ChamadoDAO();

			formataDataManutencao();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as manutenções");
			erro.printStackTrace();
		}
	}

	public void formataDataManutencao() {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		verificaListagem();

		for (Manutencao manutencao : manutencoes) {
			Date dataHoraManutencao = manutencao.getDataHoraAbertura();
			manutencao.setDataHoraFormatada(dateFormat.format(dataHoraManutencao));
		}

	}

	public void verificaListagem() {
		switch (usuario.getTipoUsuario()) {
		case ADMINISTRADOR:
			manutencoes = manutencaoDAO.listar("codigo");
			habilitaCampo = true;
			break;
		case SUPORTE:
			manutencoes = manutencaoDAO.listar("codigo");
			habilitaCampo = true;
			break;
		default:
			manutencoes = manutencaoDAO.listarPorUsuario(usuario.getCodigo());
			habilitaCampo = false;
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
			manutencao.setUsuario(usuario);
			manutencao.setDataHoraAbertura(new Date());
			manutencao.setStatusChamado(StatusChamado.ABERTO);
			manutencaoDAO.merge(manutencao);

			manutencao = new Manutencao();
			manutencoes = manutencaoDAO.listar("codigo");

			ProblemaDAO problemaDAO = new ProblemaDAO();
			problemas = problemaDAO.listar("codigo");

			formataDataManutencao();

			Messages.addGlobalInfo("Manutenção salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a manutenção");
			erro.printStackTrace();
		}
	}

	public void carregaInfoChamado(ActionEvent evento) {
		manutencao = (Manutencao) evento.getComponent().getAttributes().get("manutencaoSelecionada");

		chamado = new Chamado();
		chamado = chamadoDAO.buscarChamado(manutencao.getCodigo(), manutencao.getUsuario().getCodigo());

		if (manutencao.getStatusChamado().equals(StatusChamado.FECHADO)) {
			if (!usuario.getTipoUsuario().equals(TipoUsuario.FUNCIONARIO)) {
				habilitaCampo = true;
			}  else {
				habilitaCampo = false;
			}
		}

		if (manutencao.getStatusChamado().equals(StatusChamado.ABERTO)) {
			mensagemDataHora = "Chamado em andamento, em breve setá atendido!";
		}

		if (usuario.getTipoUsuario().equals(TipoUsuario.FUNCIONARIO)) {
			habilitaBotao = false;
		}

		if (chamado == null) {
			chamado = new Chamado();
			chamado.setManutencao(manutencao);
			chamado.setUsuario(usuario);
		} else {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			chamado.setDataHoraRecebidaFormatada(dateFormat.format(chamado.getDataHoraRecebida()));
			chamado.setDataHoraFinalizadaFormatada(dateFormat.format(chamado.getDataHoraFinalizada()));

			mensagemDataHora = chamado.getDataHoraRecebidaFormatada();

		}

		PrimeFaces.current().executeScript("PF('dlgChamado').show();");
	}

	public void salvarChamado() {

		chamado.setDataHoraRecebida(new Date());
		chamado.setDataHoraFinalizada(new Date());

		manutencao.setStatusChamado(StatusChamado.FECHADO);
		manutencaoDAO.merge(manutencao);

		chamadoDAO.merge(chamado);

		Messages.addGlobalInfo("Chamado salvo com sucesso!");
	}

	public HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	}

}
