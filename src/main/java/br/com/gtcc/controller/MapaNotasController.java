package br.com.gtcc.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("mapanotas/index");
        mv.addObject("mapaNotas", mapaNotasService.findAll());
        
        return mv;
    }
    
    @GetMapping("/lancarNotas")
	public ModelAndView add(MapaNotas mapaNotas) {
		
		List<FichaIdentificacao> fichas = this.fichaIndentificacaoService.listarTodos();
		CriterioAvaliacao criterio = this.criterioAvaliacaoService.listarTodos().get(0);
		
		ModelAndView mv = new ModelAndView("mapanotas/lancar_notas");
		mv.addObject("mapaNotas", mapaNotas);
		mv.addObject("criterioAvaliacao", criterio);
		//mv.addObject("fichas", fichas);
		return mv;
	}

    @GetMapping("/lancarNotas/{ano}")
    public List<FichaIdentificacao> listarPorAno(@PathVariable("ano") Integer ano) {
        return this.fichaIndentificacaoService.listarPorAno(ano);
    }

    @RequestMapping(value="/lancarNotas/ano/{ano}", method=RequestMethod.GET,
    		produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FichaIdentificacao> getEventoAno( @PathVariable("ano") Integer ano) {
    	
		List<FichaIdentificacao> fichas = this.fichaIndentificacaoService.listarPorAno(ano);
		
		return fichas;
    }


    

}
