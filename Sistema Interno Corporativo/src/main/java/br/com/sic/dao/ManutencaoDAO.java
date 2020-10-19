package br.com.sic.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.sic.domain.Manutencao;
import br.com.sic.util.HibernateUtil;

public class ManutencaoDAO extends GenericDAO<Manutencao>{

	@SuppressWarnings("unchecked")
	public List<Manutencao> listarPorUsuario(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try{
			Criteria consulta = sessao.createCriteria(Manutencao.class);
			consulta.createAlias("usuario", "u");
			
			consulta.add(Restrictions.eq("u.codigo", codigo));
			
			List<Manutencao> resultado = consulta.list();
			
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
}
