package br.com.gtcc.controller;
import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.DeclaracaoParticipante;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.repository.AgendamentoRepository;
import br.com.gtcc.repository.DeclaracaoParticipanteRepository;
import br.com.gtcc.repository.FichaIdentificacaoRepository;
import br.com.gtcc.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

@Controller
@RequestMapping("/gtcc/declaracoes")
public class DeclaracaoParticipanteController {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private DeclaracaoParticipanteRepository declaracaoParticipanteRepository;

	@Autowired
	private FichaIdentificacaoRepository fichaIdentificacaoRepository;
	
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@GetMapping("/consultar")
	public ModelAndView view(FichaIdentificacao filtro) {
		
		ModelAndView mv = new ModelAndView("declaracoes/consulta");
		mv.addObject("declaracoes", this.professorRepository.findAll());
		//mv.addObject("filtro", filtro);
		return mv;
	}
	
	@PostMapping("/consultar")
	public ModelAndView listarEspecifico(FichaIdentificacao filtro)
	{
		return view(filtro);
	}
	
	
	@GetMapping("/cadastro")
	public ModelAndView index() {
		LocalDate data = LocalDate.now();
		List<Agendamento> agendamentos = this.agendamentoRepository.findByDataDefesaLessThanEqual(data);
		List<FichaIdentificacao> fichaIdentificacaos = new ArrayList<>();
		for(Agendamento agen: agendamentos){
			fichaIdentificacaos.add(agen.getFichaIdentificacao());
		}
		ModelAndView mv = new ModelAndView("declaracoes/cadastro");
		mv.addObject("fichaIdentificacaos", fichaIdentificacaos);
		return mv;
	}

	@PostMapping("/salvar/")
	public ModelAndView gerar(@RequestParam(name = "alunoS", required = false) String alunoid,
			@RequestParam(name = "nomeReadOnly", required = false) String lixo,
			@RequestParam(name = "carga", required = false) Double carga,
			@RequestParam(name = "nome[]", required = false) String nome,
			@RequestParam(name = "ra[]", required = false) String ra,
			@RequestParam(name = "curso[]", required = false) String curso) throws InterruptedException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
		
		String cursoV = fichaIdentificacaoRepository.findByAlunoIdUsuario(Long.parseLong(alunoid)).get(0).getAreaConcentracao();
		String[] nomes = nome.split(",");
		String[] ras = nome.split(",");
		String[] cursos = nome.split(",");
		
		for(int i=0; i < nomes.length; i++) {
			declaracaoParticipanteRepository.saveAndFlush(new DeclaracaoParticipante(nomes[i],ras[i],cursos[i],carga,cursoV));
		}
		
		return new ModelAndView("redirect:/gtcc/declaracoes/cadastro").addObject("sucesso", true);
	}

}
