package br.com.gtcc.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.gtcc.service.AtividadeService;

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
	
}
