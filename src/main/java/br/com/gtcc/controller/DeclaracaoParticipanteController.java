package br.com.gtcc.controller;
import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/gtcc/declaracoes")
public class DeclaracaoParticipanteController {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;


	@GetMapping
	public ModelAndView index() {
		int hora = Calendar.getInstance().get(Calendar.HOUR);
		LocalDate data = LocalDate.now();
		//List<Agendamento> agendamentos = this.agendamentoRepository.findByHorarioGreaterThanAndDataDefesaGreaterThanEqual(hora, data);
		List<FichaIdentificacao> fichaIdentificacaos = new ArrayList<>();
		//for(Agendamento agen: agendamentos){
		//	fichaIdentificacaos.add(agen.getFichaIdentificacao());
		//}
		ModelAndView mv = new ModelAndView("declaracoes/cadastro");
		mv.addObject("fichaIdentificacaos", fichaIdentificacaos);
		return mv;
	}

}
