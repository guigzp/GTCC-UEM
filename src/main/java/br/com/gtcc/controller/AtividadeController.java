package br.com.gtcc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Atividade;
import br.com.gtcc.repository.AtividadeRepository;
import br.com.gtcc.service.AtividadeService;

/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */
@Controller
@RequestMapping("/gtcc/atividade")
public class AtividadeController {
	
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private AtividadeRepository atividadeRepository;
	
	/**
	 * Função para buscar as atividades dado o ano no cronograma
	 * @return
	 */
	
	public List<Integer> listaAnoAtividade(){
		List<Atividade> atividades = atividadeRepository.findAll();
		List<Integer> anosRepeated = new ArrayList<Integer>();
		for(int i = 0; i < atividades.size();i++) {
			anosRepeated.add(atividades.get(i).getAno());
		}
		List<Integer> anos = new ArrayList<Integer>();
		 
		for(int element : anosRepeated) {
			if(!anos.contains(element)) {
				anos.add(element);
			}
		}
		return anos;
	}
	
	@GetMapping("/cronograma")
	public ModelAndView findByAno(){
		 ModelAndView mv = new ModelAndView("atividade/cronograma");
		 
		 List<Integer> anos = this.listaAnoAtividade();
		 
	     mv.addObject("atividades", atividadeService.buscarPorAno(Calendar.getInstance().get(Calendar.YEAR)));
	     mv.addObject("anos", anos);
	     return mv;
	}
	
	@PostMapping("/cronograma/pesquisar")
	public ModelAndView pesquisar(@RequestParam("ano") Integer ano) {
		 ModelAndView mv = new ModelAndView("atividade/cronograma");
		 List<Integer> anos = this.listaAnoAtividade();
		 mv.addObject("atividades", atividadeService.buscarPorAno(ano));
		 mv.addObject("anos", anos);
		 return mv;
		
	}
}
