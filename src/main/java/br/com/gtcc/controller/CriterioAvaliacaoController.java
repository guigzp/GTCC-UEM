package br.com.gtcc.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gtcc.model.CriterioAvaliacao;
import br.com.gtcc.repository.CriterioAvaliacaoRepositorySearch;
import br.com.gtcc.service.CriterioAvaliacaoService;

@Controller
@RequestMapping("/gtcc/criterioavaliacao")
public class CriterioAvaliacaoController {

	@Autowired
	private CriterioAvaliacaoService criteiroService;
	@Autowired
	private CriterioAvaliacaoRepositorySearch search;
	
	@GetMapping("/consultar")
	public ModelAndView view(CriterioAvaliacao filtro) {
		
		ModelAndView mv = new ModelAndView("criterioAvaliacao/criterioSearch");
		mv.addObject("criterios", this.search.filtrar(filtro));
		mv.addObject("filtro", filtro);
		return mv;
	}
	
	@PostMapping("/ativar/{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes)
	{
		this.criteiroService.ativar(this.criteiroService.buscarPorId(id));
		
		attributes.addFlashAttribute("mensagem", "Critério de avaliação ativado com sucesso!");
		
		return "redirect:/gtcc/criterioavaliacao/consultar";
	}
	
	@PostMapping("/consultar")
	public ModelAndView listarEspecifico(CriterioAvaliacao filtro)
	{
		return view(filtro);
	}

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

		criterioAvaliacao.setAtivo(false);
		criteiroService.adicionar(criterioAvaliacao);

		return new ModelAndView("redirect:/gtcc/criterioavaliacao/cadastrar").addObject("sucesso", true);
	}

}
