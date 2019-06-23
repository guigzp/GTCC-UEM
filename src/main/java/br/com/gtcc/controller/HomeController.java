package br.com.gtcc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Usuario;

@Controller
@RequestMapping("/gtcc/home")
public class HomeController {

	
	@GetMapping
	public ModelAndView index(Usuario usuario) {
		ModelAndView mv = new ModelAndView("home");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
		mv.addObject("nomeUsuario", name);
		return mv;
	}
	
}
