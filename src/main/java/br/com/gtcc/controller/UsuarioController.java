package br.com.gtcc.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Usuario;
import br.com.gtcc.repository.filter.UsuarioFilter;
import br.com.gtcc.service.UsuarioService;

@Controller
@RequestMapping("/gtcc/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ModelAndView findAll(@RequestParam("usuarioFilter") Optional<UsuarioFilter> usuarioFilter) {
        ModelAndView mv = new ModelAndView("usuario/index");

        mv.addObject("usuarioFilter", usuarioFilter);


        mv.addObject("usuarios", usuarioService.listarTodosAtivos());
        return mv;
    }


    @GetMapping("/cadastrar")
    public ModelAndView add(Usuario usuario) {
        ModelAndView mv = new ModelAndView("usuario/usuarioCreate");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Usuario usuario, BindingResult result) {
        if (!(usuario.getSenha() == null && usuario.getConfirmarSenha() == null ||
                usuario.getSenha() != null && usuario.getSenha().equals(usuario.getConfirmarSenha()))) {
            result.addError(new FieldError("usuario", "confirmarSenha", "As senhas digitadas não correspondem"));
        }
        if (this.usuarioService.buscarPorEmail(usuario.getEmail()) != null) {
        	result.addError(new FieldError("usuario", "email", "Email já cadastrado"));
        }

        if(this.usuarioService.buscarPorUsername(usuario.getNomeUsuario()) != null) {
        	result.addError(new FieldError("usuario", "nomeUsuario", "Nome de Usuário já cadastrado"));
        }

        if (result.hasErrors()) {
            return add(usuario);
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setAtivo(1);
        usuarioService.adicionar(usuario);

        return new ModelAndView("redirect:/gtcc/usuarios/cadastrar").addObject("sucesso", true);
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("usuario/usuarioUpdate");
        mv.addObject("usuario", usuarioService.buscarPorId(id));

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView details(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("usuario/usuarioDetails");
        mv.addObject("usuario", usuarioService.buscarPorId(id));
        return mv;
    }

    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Usuario usuario, BindingResult result) {

        if (!(usuario.getSenha() == null && usuario.getConfirmarSenha() == null ||
                usuario.getSenha() != null && usuario.getSenha().equals(usuario.getConfirmarSenha()))) {
            result.addError(new FieldError("usuario", "confirmarSenha", "As senhas digitadas não correspondem"));
        }


        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("usuario/usuarioUpdate");
            mv.addObject("usuario", usuario);
            return mv;
        }
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setAtivo(1);
        usuarioService.atualizar(usuario);
        return new ModelAndView("redirect:/gtcc/usuarios").addObject("atualizado", true);
    }

}
