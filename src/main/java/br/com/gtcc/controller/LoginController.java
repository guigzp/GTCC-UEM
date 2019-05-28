package br.com.gtcc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Usuario;
/**
 * Classe controladora do Login - responsável por manipular as ações da visão
 * @author Alan Lopes
 *
 */
@Controller
@RequestMapping("/gtcc/login")
public class LoginController {

	@GetMapping
	public ModelAndView index(Usuario usuario) {
		ModelAndView mv = new ModelAndView("login/login");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping
	public ModelAndView validar(Usuario usuario) {
		return new ModelAndView("redirect:/gtcc/usuarios");
	}
	
	@PostMapping("/logout")
	public ModelAndView logout() {
		return new ModelAndView("redirect:/gtcc/login");
	}
	
	/**
	 * Função responsável pela recuperação da senha do usuário
	 * @param usuario - usuário que deseja recuperar sua senha
	 * @return mv - retorna um ModelAndView, isto é, uma interface (tela)
	 */

	@GetMapping("/recuperarSenha")
	public ModelAndView recuperarSenha(Usuario usuario) {
		ModelAndView mv = new ModelAndView("login/recuperarSenha");
		mv.addObject("usuario", usuario);
		return mv;
	}
	
}
