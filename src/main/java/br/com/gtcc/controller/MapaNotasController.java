package br.com.gtcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/gtcc/mapanotas")
public class MapaNotasController {



    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("mapanotas/index");
        
        return mv;
    }


    

}
