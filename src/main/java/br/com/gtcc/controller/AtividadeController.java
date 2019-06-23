package br.com.gtcc.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
	
	/**
	 * Função para buscar as atividades dado o ano no cronograma
	 * @return
	 */
	@GetMapping("/cronograma")
	public ModelAndView findByAno(){
		 ModelAndView mv = new ModelAndView("atividade/cronograma");
	     mv.addObject("atividades", atividadeService.buscarPorAno(Calendar.getInstance().get(Calendar.YEAR)));
	     return mv;
	}
	
	@PostMapping("/pesquisar")
	public ModelAndView pesquisar(@RequestParam("ano") Integer ano) {
		 ModelAndView mv = new ModelAndView("atividade/cronograma");
		 mv.addObject("atividades", atividadeService.buscarPorAno(ano));
		 return mv;
		
	}
}
