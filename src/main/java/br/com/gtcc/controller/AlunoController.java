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

import br.com.gtcc.model.Aluno;
import br.com.gtcc.repository.filter.UsuarioFilter;
import br.com.gtcc.service.AlunoService;

@Controller
@RequestMapping("/gtcc/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;


    @GetMapping
    public ModelAndView findAll(@RequestParam("alunoFilter") Optional<UsuarioFilter> alunoFilter) {
        ModelAndView mv = new ModelAndView("aluno/index");

        mv.addObject("alunoFilter", alunoFilter);


        mv.addObject("alunos", alunoService.listarTodosAtivos());
        return mv;
    }


    @GetMapping("/cadastrar")
    public ModelAndView add(Aluno aluno) {
        ModelAndView mv = new ModelAndView("aluno/alunoCreate");
        mv.addObject("aluno", aluno);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Aluno aluno, BindingResult result) {
        if (!(aluno.getSenha() == null && aluno.getConfirmarSenha() == null ||
                aluno.getSenha() != null && aluno.getSenha().equals(aluno.getConfirmarSenha()))) {
            result.addError(new FieldError("aluno", "confirmarSenha", "As senhas digitadas não correspondem"));
        }
        if (this.alunoService.buscarPorEmail(aluno.getEmail()) != null) {
        	result.addError(new FieldError("aluno", "email", "Email já cadastrado"));
        }

        if(this.alunoService.buscarPorUsername(aluno.getNomeUsuario()) != null) {
        	result.addError(new FieldError("aluno", "nomeUsuario", "Nome de Usuário já cadastrado"));
        }

        if (result.hasErrors()) {
            return add(aluno);
        }

        aluno.setSenha(new BCryptPasswordEncoder().encode(aluno.getSenha()));
        aluno.setAtivo(1);
        alunoService.adicionar(aluno);

        return new ModelAndView("redirect:/gtcc/login").addObject("sucesso", true);
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("aluno/alunoUpdate");
        mv.addObject("aluno", alunoService.buscarPorId(id));

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView details(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("aluno/ualunoDetails");
        mv.addObject("aluno", alunoService.buscarPorId(id));
        return mv;
    }

    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Aluno aluno, BindingResult result) {

        if (!(aluno.getSenha() == null && aluno.getConfirmarSenha() == null ||
                aluno.getSenha() != null && aluno.getSenha().equals(aluno.getConfirmarSenha()))) {
            result.addError(new FieldError("aluno", "confirmarSenha", "As senhas digitadas não correspondem"));
        }


        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("aluno/alunoUpdate");
            mv.addObject("aluno", aluno);
            return mv;
        }
        aluno.setSenha(new BCryptPasswordEncoder().encode(aluno.getSenha()));
        aluno.setAtivo(1);
        alunoService.atualizar(aluno);
        return new ModelAndView("redirect:/gtcc/alunos").addObject("atualizado", true);
    }

}
