package br.com.gtcc.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.gtcc.model.CriterioAvaliacao;


@Repository
public class CriterioAvaliacaoRepositorySearch {

	@PersistenceContext
	private EntityManager manager;
	
	
	public List<CriterioAvaliacao> filtrar(CriterioAvaliacao criterio) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<CriterioAvaliacao> criteria = builder.createQuery(CriterioAvaliacao.class);
		
		Root<CriterioAvaliacao> root = criteria.from(CriterioAvaliacao.class);
		
		Predicate[] predicates = restricoes(criterio, builder, root);
		criteria.where(predicates);
		
		TypedQuery<CriterioAvaliacao> query = manager.createQuery(criteria);
		
		return query.getResultList(); 
	}
	
	private Predicate[] restricoes(CriterioAvaliacao criterio, CriteriaBuilder builder, Root<CriterioAvaliacao> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(criterio.getAno()))
			predicates.add(builder.equal(root.get("ano"), 
					 criterio.getAno() ));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}