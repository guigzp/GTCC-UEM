package br.com.gtcc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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


    

}
