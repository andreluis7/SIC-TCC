package br.com.sic.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
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
import br.com.sic.util.HibernateUtil;
import br.com.sic.util.RelatorioUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	private String nomeBotao = "";

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

		if (chamado == null) {
			chamado = new Chamado();
			chamado.setManutencao(manutencao);
			chamado.setUsuario(usuario);
		} else {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			verificaDataHoraChamado(dateFormat);

		}

		verificarRegras();

	}

	public void verificaDataHoraChamado(DateFormat dateFormat) {
		if (chamado.getManutencao().getStatusChamado().equals(StatusChamado.ANDAMENTO)) {
			chamado.setDataHoraRecebidaFormatada(dateFormat.format(chamado.getDataHoraRecebida()));
		} else {
			chamado.setDataHoraRecebidaFormatada(dateFormat.format(chamado.getDataHoraRecebida()));
			chamado.setDataHoraFinalizadaFormatada(dateFormat.format(chamado.getDataHoraFinalizada()));
		}
	}

	public void verificarRegras() {
		if (usuario.getTipoUsuario().equals(TipoUsuario.FUNCIONARIO)) {
			regrasUsuarioFuncionario();
			PrimeFaces.current().executeScript("PF('dlgChamado').show();");
		} else {
			regrasUsuarioOutros();
			PrimeFaces.current().executeScript("PF('dlgChamadoNaoFuncionario').show();");
		}
	}

	public void regrasUsuarioFuncionario() {

		switch (manutencao.getStatusChamado()) {
		case FECHADO:
			habilitaCampo = true;
			break;
		case ABERTO:
			habilitaCampo = false;
			break;
		case ANDAMENTO:
			habilitaCampo = true;
			break;
		default:
			habilitaBotao = false;
			break;
		}

	}

	public void regrasUsuarioOutros() {
		switch (manutencao.getStatusChamado()) {
		case ABERTO:
			habilitaCampo = false;
			nomeBotao = "Atender";
			break;
		case ANDAMENTO:
			nomeBotao = "Finalizar";
			break;
		case FECHADO:
			habilitaBotao = true;
			nomeBotao = "Alterar";
			break;
		default:
			break;
		}
	}

	public void verificaAcao() {
		if (nomeBotao.equalsIgnoreCase("Atender")) {
			atenderChamado();
		} else {
			salvarChamado();
		}
	}

	public void atenderChamado() {
		manutencao.setAtendimento(true);
		manutencao.setStatusChamado(StatusChamado.ANDAMENTO);

		chamado.setDataHoraRecebida(new Date());

		manutencaoDAO.merge(manutencao);
		chamadoDAO.merge(chamado);
		PrimeFaces.current().executeScript("PF('dlgChamadoNaoFuncionario').hide();");
		Messages.addGlobalInfo("Solicitação em atendimento!");
	}

	public void salvarChamado() {
		chamado.setDataHoraFinalizada(new Date());
		manutencao.setStatusChamado(StatusChamado.FECHADO);
		manutencaoDAO.merge(manutencao);
		chamadoDAO.merge(chamado);
		PrimeFaces.current().executeScript("PF('dlgChamadoNaoFuncionario').hide();");
		Messages.addGlobalInfo("Chamado salvo com sucesso!");
	}

	public void imprimir() {
		try {
			String caminhoRelatorio = Faces.getRealPath("/reports/manutencoes.jasper");
			String caminhoLogo = Faces.getRealPath("/resources/poseidon-layout/images/logo-white.png");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("STATUS_PESQUISA", "%%");
			parametros.put("IMAGEM_LOGO", caminhoLogo);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminhoRelatorio, parametros, conexao);

			byte[] bytes = JasperExportManager.exportReportToPdf(relatorio);

			if (bytes != null) {
				HttpServletResponse response = RelatorioUtil.getResponse();
				response.setContentType("application/pdf");
	            response.setHeader("Content-disposition", "attachment" + "; filename=\"" + "Manutencoes" + ".pdf\"");
				response.setContentLength(bytes.length);
				response.getOutputStream().write(bytes, 0, bytes.length);
				response.getOutputStream().flush();
				response.flushBuffer();
				RelatorioUtil.responseComplete();
			}
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}

	public HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

//	******************************************************************************
//	
//							GETTERS AND SETTERS
//	
//	******************************************************************************

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

	public String getNomeBotao() {
		return nomeBotao;
	}

	public void setNomeBotao(String nomeBotao) {
		this.nomeBotao = nomeBotao;
	}

}
