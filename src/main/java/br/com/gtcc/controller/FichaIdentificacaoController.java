package br.com.gtcc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.model.Professor;
import br.com.gtcc.service.FichaIdentificacaoService;
import br.com.gtcc.service.ProfessorService;

@Controller
@RequestMapping("/gtcc/fichaidentificacao")
public class FichaIdentificacaoController {

	@Autowired
	private FichaIdentificacaoService fichaService;
	@Autowired
	private ProfessorService professorService;

	@GetMapping("/cadastrar")
	public ModelAndView add(FichaIdentificacao ficha) {
		
		List<Professor> professores = this.professorService.buscarTodos();
		
		ModelAndView mv = new ModelAndView("fichaIdentificacao/fichaCreate");
		mv.addObject("ficha", ficha);
		mv.addObject("professores", professores);
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView save(@Valid @ModelAttribute("ficha") FichaIdentificacao ficha, BindingResult result) {

		if (result.hasErrors()) {			
			return add(ficha);
		}

		fichaService.adicionar(ficha);

		return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", true);
	}

}
