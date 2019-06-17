package br.com.gtcc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Professor;
import br.com.gtcc.service.ProfessorService;

@Controller
@RequestMapping("/gtcc/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/cadastrar")
    public ModelAndView add(Professor professor) {
        ModelAndView mv = new ModelAndView("professor/professorCreate");
        mv.addObject("professor", professor);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView save(@Valid Professor professor, BindingResult result) {
    	System.out.println(professor.getCurso());
        if (this.professorService.buscarPorEmail(professor.getEmail()) != null) {
            result.addError(new FieldError("professor", "email", "Email já cadastrado"));
        }

        if(!professor.getOrientador()) {
            if(!professor.getAvaliador()) {
                if(!professor.getCoordenador()) {
                    if(!professor.getCoordenador_do_curso()) {
                        result.addError(new FieldError("coordenador_do_curso", "coordenador_do_curso", "Tipo é uma informação obrigatória"));
                    }
                }
            }
        }

        if (result.hasErrors()) {
            return add(professor);
        }

        professorService.adicionar(professor);

        return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", true);
    }

}