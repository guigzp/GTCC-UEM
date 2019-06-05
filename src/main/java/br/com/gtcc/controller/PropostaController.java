package br.com.gtcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gtcc/proposta")
public class PropostaController {
	
	@GetMapping("/1")
	public ModelAndView findAll(){
		 ModelAndView mv = new ModelAndView("proposta/1");
		 mv.addObject("proposta", true);
	     return mv;
	}
	
	@PostMapping("/gerar")
	public ModelAndView gerar() {
		return null;
    }
	
}

