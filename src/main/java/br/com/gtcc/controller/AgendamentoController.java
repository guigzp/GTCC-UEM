package br.com.gtcc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.service.AgendamentoService;
import br.com.gtcc.service.FichaIdentificacaoService;
/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */
@Controller
@RequestMapping("/gtcc/agendamentodefesa")
public class AgendamentoController {
	
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private FichaIdentificacaoService fichaIdentificacaoService;


    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("agendamentodefesa/index");

        //mv.addObject("agendamentoFilter", agendamentoFilter);


        mv.addObject("agendamentos", agendamentoService.listarTodosAtivos());
        return mv;
    }


    @GetMapping("/cadastrar")
    public ModelAndView add(Agendamento agendamento) {
    	
    	List<FichaIdentificacao> fichas = fichaIdentificacaoService.listarTodos();
    	
        ModelAndView mv = new ModelAndView("agendamentodefesa/defesaCreate");
        mv.addObject("fichas", fichas);
        mv.addObject("agendamento", agendamento);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Agendamento agendamento, BindingResult result) {
        if (this.agendamentoService.buscarPorFichaId(agendamento.getFichaIdentificacao().getId()) != null) {
        	result.addError(new FieldError("agendamento", "nome", "Agendamento desse aluno já cadastrado."));
        }

        if (result.hasErrors()) {
            return add(agendamento);
        }
        agendamentoService.adicionar(agendamento);

        return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", true);
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("agendamentodefesa/defesaUpdate");
        mv.addObject("agendamento", agendamentoService.buscarPorId(id));

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView details(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("agendamentodefesa/visualizarDefesa");
        mv.addObject("agendamento", agendamentoService.buscarPorId(id));
        return mv;
    }

    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Agendamento agendamento, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("agendamentodefesa/defesaUpdate");
            mv.addObject("agendamento", agendamento);
            return mv;
        }
        
        agendamentoService.atualizar(agendamento);
        return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("atualizado", true);
    }

    @GetMapping("/remover/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	Agendamento agendamento = agendamentoService.buscarPorId(id);
    	if(agendamento != null) {
    		agendamento.setAtivo(0);
    		agendamentoService.atualizar(agendamento);
    	}
    	return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("removido", true);
    }
    
    /*@PostMapping
    public ModelAndView search(AgendamentoFilter agendamentoFilter){
    	ModelAndView mv = new ModelAndView("");
    	mv.addObject("agendamentoFilter", agendamentoFilter);
    	mv.addObject("agendamentos", agendamentoService.filtrar(agendamentoFilter));
    	
    	return mv;
    }*/
}
