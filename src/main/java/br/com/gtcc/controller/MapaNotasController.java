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

import br.com.gtcc.model.Aluno;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.model.MapaNotas;
import br.com.gtcc.model.Professor;
import br.com.gtcc.service.FichaIdentificacaoService;
import br.com.gtcc.service.MapaNotasService;

/**
 * 
 * @author Alan Lopes
 *
 */
@Controller
@RequestMapping("/gtcc/mapanotas")
public class MapaNotasController {

	@Autowired
	private MapaNotasService mapaNotasService;
	@Autowired
	private FichaIdentificacaoService fichaIndentificacaoService;

	/**
	 * Tela inicial do Mapa de Notas
	 * @return
	 */
    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("mapanotas/index");
        
        
        mv.addObject("mapaNotas", mapaNotasService.findAll());
        
        return mv;
    }
    
    @GetMapping("/lancarNotas")
	public ModelAndView add(MapaNotas mapaNotas) {
		
		List<FichaIdentificacao> fichas = this.fichaIndentificacaoService.listarTodos();
		
		ModelAndView mv = new ModelAndView("mapanotas/lancar_notas");
		mv.addObject("mapaNotas", mapaNotas);
		mv.addObject("fichas", fichas);
		return mv;
	}



    

}
