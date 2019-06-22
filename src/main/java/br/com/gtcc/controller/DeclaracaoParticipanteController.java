package br.com.gtcc.controller;
import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.repository.AgendamentoRepository;
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
import java.util.Calendar;
import java.util.List;

import javax.script.ScriptException;

@Controller
@RequestMapping("/gtcc/declaracoes")
public class DeclaracaoParticipanteController {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;


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
	public ModelAndView gerar(@RequestParam(name = "alunoS", required = false) String aluno,
			@RequestParam(name = "nomeReadOnly", required = false) String lixo,
			@RequestParam(name = "carga", required = false) String carga,
			@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "ra", required = false) String ra,
			@RequestParam(name = "curso", required = false) String curso) throws InterruptedException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
	return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
	}

}
