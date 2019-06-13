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

import br.com.gtcc.model.FichaIdentificacao;


@Repository
public class FichaIdentificacaoRepositorySearch {

	@PersistenceContext
	private EntityManager manager;
	
	
	public List<FichaIdentificacao> filtrar(FichaIdentificacao ficha) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<FichaIdentificacao> criteria = builder.createQuery(FichaIdentificacao.class);
		
		Root<FichaIdentificacao> root = criteria.from(FichaIdentificacao.class);
		
		Predicate[] predicates = restricoes(ficha, builder, root);
		criteria.where(predicates);
		
		TypedQuery<FichaIdentificacao> query = manager.createQuery(criteria);
		
		return query.getResultList(); 
	}
	
	private Predicate[] restricoes(FichaIdentificacao ficha, CriteriaBuilder builder, Root<FichaIdentificacao> root) {
		List<Predicate> predicates	= new ArrayList<>();
		
		
		if(!StringUtils.isEmpty(ficha.getTituloTrabalho()))
			predicates.add(builder.like(builder.lower(root.get("tituloTrabalho")), 
					"%"+ ficha.getTituloTrabalho().toLowerCase() + "%" ));
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}