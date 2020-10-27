package br.com.sic.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe para execução de relatórios
 * 
 */
public class RelatorioUtil {

	/**
	 * Método requerido para geração de relatórios
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse resp = (HttpServletResponse) externalContext.getResponse();
		return resp;
	}

	/**
	 * Método para gerar de relatórios
	 * 
	 * @return
	 */
	public static void responseComplete() {
		FacesContext.getCurrentInstance().renderResponse();
		FacesContext.getCurrentInstance().responseComplete();

	}
}
