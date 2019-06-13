package br.com.gtcc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.model.Professor;
import br.com.gtcc.repository.FichaIdentificacaoRepositorySearch;
import br.com.gtcc.service.AlunoService;
import br.com.gtcc.service.FichaIdentificacaoService;
import br.com.gtcc.service.ProfessorService;

@Controller
@RequestMapping("/gtcc/fichaidentificacao")
public class FichaIdentificacaoController {

	@Autowired
	private FichaIdentificacaoService fichaService;
	@Autowired
	private FichaIdentificacaoRepositorySearch fichaSearch;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/consultar")
	public ModelAndView view(FichaIdentificacao filtro) {
		
		ModelAndView mv = new ModelAndView("fichaIdentificacao/fichaSearch");
		mv.addObject("fichas", this.fichaSearch.filtrar(filtro));
		mv.addObject("filtro", filtro);
		return mv;
	}
	
	@PostMapping("/consultar")
	public ModelAndView listarEspecifico(FichaIdentificacao filtro)
	{
		return view(filtro);
	}

	@GetMapping("/cadastrar")
	public ModelAndView add(FichaIdentificacao ficha) {
		
		List<Professor> professores = this.professorService.buscarTodos();
		List<Aluno> alunos = this.alunoService.buscarTodos();
		
		ModelAndView mv = new ModelAndView("fichaIdentificacao/fichaCreate");
		mv.addObject("ficha", ficha);
		mv.addObject("professores", professores);
		mv.addObject("alunos", alunos);
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
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id)
	{
		return add(fichaService.buscar(id));
	}
	
	@DeleteMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id, RedirectAttributes attributes)
	{
		this.fichaService.deletar(this.fichaService.buscar(id));
		
		attributes.addFlashAttribute("mensagem", "Ficha de identificação removida com sucesso!");
		
		return "redirect:/gtcc/fichaidentificacao/consultar";
	}

}
