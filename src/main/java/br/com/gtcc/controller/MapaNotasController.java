package br.com.gtcc.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import br.com.gtcc.model.CriterioAvaliacao;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.model.MapaNotas;
import br.com.gtcc.service.CriterioAvaliacaoService;
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
	@Autowired
	private CriterioAvaliacaoService criterioAvaliacaoService;

	/**
	 * Tela inicial do Mapa de Notas
	 * @return
	 */
    @GetMapping("/consultar")
    public ModelAndView findAll(MapaNotas mapaNotasFiltro) {
        ModelAndView mv = new ModelAndView("mapanotas/index");
        mv.addObject("mapaNotas", mapaNotasService.findByAno(mapaNotasFiltro.getAno()));
        mv.addObject("mapaNotasFiltro", mapaNotasFiltro);
        return mv;
    }
    
	@PostMapping("/consultar")
	public ModelAndView mapasPorAno(MapaNotas mapaNotasFiltro)
	{
		return findAll(mapaNotasFiltro);
	}
    
    @GetMapping("/lancarNotas")
	public ModelAndView add(MapaNotas mapaNotas) {
		
		CriterioAvaliacao criterio = this.criterioAvaliacaoService.buscarAtivo();
		
		ModelAndView mv = new ModelAndView("mapanotas/lancar_notas");
		mv.addObject("mapaNotas", mapaNotas);
		mv.addObject("criterioAvaliacao", criterio);
		return mv;
	}

    @PostMapping("/lancarNotas")
    public ModelAndView save(@Valid MapaNotas mapaNotas, BindingResult result) {
        
        if (result.hasErrors()) {
            return add(mapaNotas);
        }

        
        mapaNotasService.adicionar(mapaNotas);

        return new ModelAndView("redirect:/gtcc/mapanotas/lancarNotas").addObject("sucesso", true);
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        
		ModelAndView mv = new ModelAndView("mapanotas/editar_notas");
		MapaNotas mapaNotas = this.mapaNotasService.findById(id);
		mv.addObject("mapaNotas", mapaNotas);
		mv.addObject("criterioAvaliacao", mapaNotas.getCriterioAvaliacao());
		return mv;
    }
    
    @PostMapping("/editar")
    public ModelAndView edit(@Valid MapaNotas mapaNotas, BindingResult result) {
        
        if (result.hasErrors()) {
            return editar(mapaNotas.getId());
        }

        mapaNotasService.adicionar(mapaNotas);

        return new ModelAndView("redirect:/gtcc/mapanotas/editar/"+mapaNotas.getId()).addObject("sucesso", true);
    }
    

    @RequestMapping(value="/lancarNotas/ano/{ano}", method=RequestMethod.GET,
    		produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FichaIdentificacao> getEventoAno( @PathVariable("ano") Integer ano) {
    	
		List<FichaIdentificacao> fichas = this.fichaIndentificacaoService.listarPorAno(ano);
		
		return fichas;
    }


    

}
