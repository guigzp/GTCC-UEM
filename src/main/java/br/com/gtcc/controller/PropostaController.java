package br.com.gtcc.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.model.Atividade;
import br.com.gtcc.model.Professor;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.service.AlunoService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import br.com.gtcc.repository.AlunoRepository;
import br.com.gtcc.repository.AtividadeRepository;
import br.com.gtcc.repository.FichaIdentificacaoRepository;
import br.com.gtcc.repository.ProfessorRepository;
import br.com.gtcc.security.Conexao;

@Controller
@RequestMapping("/gtcc/proposta")
public class PropostaController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private FichaIdentificacaoRepository fichaIdentificaoRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	@GetMapping
	public ModelAndView index() {
		List<Aluno> alunos = this.alunoService.buscarTodos();
		ModelAndView mv = new ModelAndView("proposta/proposta");
		mv.addObject("proposta", true);
		mv.addObject("alunos", alunos);
		return mv;
	}

	@PostMapping("/gerar")
	public ModelAndView gerar(@RequestParam(name = "avaliacao", required = false) String avaliacao,
			@RequestParam(name = "aluno", required = false) String aluno) {
		//Avaliacao não preenchida não tem nenhum if mas não passa por causa do if da atividade
		
		if (aluno == "") {
			System.out.print("Aluno não selecionado");
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		List<FichaIdentificacao> fichasAluno = fichaIdentificaoRepository.findByAlunoIdUsuario(Long.parseLong(aluno));
		Professor coordenador = professorRepository.findByIdProfessor(new Long(1)).get(0);

		if (fichasAluno.isEmpty()) {
			System.out.print("Ficha não cadastrada");
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		FichaIdentificacao fichaAluno = fichasAluno.get(0);

		List<Atividade> atividade = atividadeRepository.findByAnoAndDescricao(fichaAluno.getAno(),
				new String("Entrega da Proposta de Projeto de TCC (" + avaliacao + "ºbim.)"));

		if (atividade.isEmpty()) {
			System.out.print("Atividade não cadastrada");
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		LocalDate data = atividade.get(0).getDataFinalEntrega();

		String dataS = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		
		HashMap parametros = new HashMap();
		parametros.put("NomeCurso", fichaAluno.getAreaConcentracao());
		parametros.put("Ano", Integer.toString(fichaAluno.getAno()));
		parametros.put("Professor", coordenador.getNome());
		parametros.put("DataDeEntrega", dataS);
		parametros.put("Avaliador", fichaAluno.getAvaliador1().getNome());
		parametros.put("Titulo", fichaAluno.getTituloTrabalho());
		parametros.put("NomeDoAluno", fichaAluno.getAluno().getNome());

		JasperPrint print;
		try {
			print = JasperFillManager.fillReport(caminho + "Proposta.jasper", parametros, Conexao.getConnection());
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + "Proposta1.pdf");
			exporter.setExporterOutput(exporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (JRException e) {
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}
		
		parametros.put("Avaliador", fichaAluno.getAvaliador2().getNome());
		
		try {
			print = JasperFillManager.fillReport(caminho + "Proposta.jasper", parametros, Conexao.getConnection());
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + "Proposta2.pdf");
			exporter.setExporterOutput(exporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (JRException e) {
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}
		
		parametros.put("Avaliador", fichaAluno.getOrientador().getNome());
		
		try {
			print = JasperFillManager.fillReport(caminho + "Proposta.jasper", parametros, Conexao.getConnection());
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + "Proposta3.pdf");
			exporter.setExporterOutput(exporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (JRException e) {
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", true);
	}

}
