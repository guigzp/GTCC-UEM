package br.com.gtcc.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.CriterioAvaliacao;
import br.com.gtcc.service.CriterioAvaliacaoService;

@Controller
@RequestMapping("/gtcc/criterioavaliacao")
public class CriterioAvaliacaoController {

	@Autowired
	private CriterioAvaliacaoService criteiroService;

	@GetMapping("/cadastrar")
	public ModelAndView add(CriterioAvaliacao criterioAvaliacao) {
		
		
		ModelAndView mv = new ModelAndView("criterioAvaliacao/criterioCreate");
		mv.addObject("criterioAvaliacao", criterioAvaliacao);
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView save(@Valid CriterioAvaliacao criterioAvaliacao, BindingResult result) {

		if (result.hasErrors()) {			
			return add(criterioAvaliacao);
		}

		criteiroService.adicionar(criterioAvaliacao);

		return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", true);
	}

}
