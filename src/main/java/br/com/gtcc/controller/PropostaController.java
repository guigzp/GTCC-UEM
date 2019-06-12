package br.com.gtcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.service.AlunoService;

@Controller
@RequestMapping("/gtcc/proposta")
public class PropostaController {
	
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping
	public ModelAndView index(){
		List<Aluno> alunos = this.alunoService.buscarTodos();
		ModelAndView mv = new ModelAndView("proposta/proposta");
		mv.addObject("proposta", true);
		mv.addObject("alunos", alunos);
	    return mv;
	}
	
	@PostMapping("/gerar/")
	public ModelAndView gerar() {
		return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", true);
    }
	
}

