package br.com.sic.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.sic.dao.UsuarioDAO;
import br.com.sic.domain.Pessoa;
import br.com.sic.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	private Usuario usuario;
	private Usuario usuarioLogado;
	private boolean mostraUsuario = false, mostraPessoa = false, mostraFuncionario = false, mostraProblema = false,
			mostraManutencao = false;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public boolean isMostraFuncionario() {
		return mostraFuncionario;
	}

	public boolean isMostraManutencao() {
		return mostraManutencao;
	}

	public boolean isMostraPessoa() {
		return mostraPessoa;
	}

	public boolean isMostraProblema() {
		return mostraProblema;
	}

	public boolean isMostraUsuario() {
		return mostraUsuario;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}

			// colocar atributo na sessao
			getSession().setAttribute("usurioLogado", usuarioLogado);

			Faces.redirect("./pages/principal.xhtml");

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}

	public HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	}

	public void verificaTipoUsuario() {

		switch (usuarioLogado.getTipoUsuario()) {
		case ADMINISTRADOR:
			mostraFuncionario = true;
			mostraManutencao = true;
			mostraPessoa = true;
			mostraProblema = true;
			mostraUsuario = true;
			break;
		case FUNCIONARIO:
			mostraManutencao = true;

			mostraFuncionario = false;
			mostraPessoa = false;
			mostraProblema = false;
			mostraUsuario = false;
			break;
		case SUPORTE:
			mostraProblema = true;
			mostraManutencao = true;
			
			mostraFuncionario = false;
			mostraPessoa = false;
			mostraUsuario = false;
			break;
		default:
			break;
		}

	}
	
	/**
	 * Método para fazer logout.
	 * 
	 */
	public void logout() {
		usuarioLogado = new Usuario();
		getSession().setAttribute("usurioLogado", usuarioLogado);
		
		try {
			Faces.redirect("/SIC");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
