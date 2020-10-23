package br.com.sic.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.sic.domain.Chamado;
import br.com.sic.util.HibernateUtil;

public class ChamadoDAO extends GenericDAO<Chamado> {

	public Chamado buscarChamado(Long codigoManutencao, Long codigoUsuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {

			Criteria consulta = sessao.createCriteria(Chamado.class);
			consulta.createAlias("manutencao", "m");
//			consulta.createAlias("usuario", "u");

			consulta.add(Restrictions.eq("m.codigo", codigoManutencao));
//			consulta.add(Restrictions.eq("u.codigo", codigoUsuario));

			Chamado resultado = (Chamado) consulta.uniqueResult();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
