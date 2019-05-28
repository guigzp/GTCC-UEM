package br.com.gtcc.controller;

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
	
	@GetMapping("/cronograma")
	public ModelAndView findAll(){
		 ModelAndView mv = new ModelAndView("atividade/cronograma");
	     mv.addObject("atividades", atividadeService.listarTodos());
	     return mv;
	}
	
}
