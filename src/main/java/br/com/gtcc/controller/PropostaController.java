package br.com.gtcc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.gtcc.service.FichaIdentificacaoService;
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
	private FichaIdentificacaoService fichaIdentificacaoService;
	
	@Autowired
	private FichaIdentificacaoRepository fichaIdentificaoRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	@GetMapping
	public ModelAndView index() {
		List<Aluno> alunos = this.fichaIdentificacaoService.listarTodosAlunos();
		ModelAndView mv = new ModelAndView("proposta/proposta");
		mv.addObject("proposta", true);
		mv.addObject("alunos", alunos);
		return mv;
	}

	@PostMapping("/gerar")
	public ModelAndView gerar(@RequestParam(name = "avaliacao", required = false) String avaliacao,
			@RequestParam(name = "aluno", required = false) String aluno) {

		

		List<FichaIdentificacao> fichasAluno = fichaIdentificaoRepository.findByAlunoIdUsuario(Long.parseLong(aluno));
		
		FichaIdentificacao fichaAluno = fichasAluno.get(0);

		Professor coordenador = professorRepository.findByCurso(fichaAluno.getAreaConcentracao()).get(0);

		List<Atividade> atividade = atividadeRepository.findByAnoAndDescricao(fichaAluno.getAno(),
				new String("Entrega da Proposta de Projeto de TCC (" + avaliacao + "ºbim.)"));

		if (atividade.isEmpty()) {
			System.out.print("Atividade não cadastrada");
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		LocalDate data = atividade.get(0).getDataFinalEntrega();

		String dataS = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		HashMap parametros = new HashMap();
		parametros.put("NomeCurso", fichaAluno.getAreaConcentracao());
		parametros.put("Ano", Integer.toString(fichaAluno.getAno()));
		parametros.put("Professor", coordenador.getNome());
		parametros.put("DataDeEntrega", dataS);
		parametros.put("Avaliador", fichaAluno.getAvaliador1().getNome());
		parametros.put("Titulo", fichaAluno.getTituloTrabalho());
		parametros.put("NomeDoAluno", fichaAluno.getAluno().getNome());

		
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		
		try {
			gerarProposta("Proposta1.pdf", parametros);
			String caminhoaux = caminho+"/Proposta1.pdf";
			
		} catch (JRException e) {
			return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
		}

		System.out.println(avaliacao);
		if (Integer.parseInt(avaliacao) == 1) {
			System.out.println("in");
			parametros.put("Avaliador", fichaAluno.getAvaliador2().getNome());
			try {
				gerarProposta("Proposta2.pdf", parametros);
				String caminhoaux = caminho+"/Proposta2.pdf";
			} catch (JRException e) {
				return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
			}
			parametros.put("Avaliador", fichaAluno.getOrientador().getNome());
			try {
				String caminhoaux = caminho+"/Proposta3.pdf";
				gerarProposta("Proposta3.pdf", parametros);
			} catch (JRException e) {
				return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
			}
		}
		
		
		return new ModelAndView("redirect:/gtcc/home").addObject("sucesso", false);
	}

	public void gerarProposta(String nome, HashMap parametros) throws JRException {
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		JasperPrint print;
		print = JasperFillManager.fillReport(caminho + "Proposta.jasper", parametros, Conexao.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = new SimpleExporterInput(print);
		exporter.setExporterInput(exporterInput);
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
		exporter.setExporterOutput(exporterOutput);
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

	}
	
	
	@RequestMapping(value = "/show", produces =  "application/pdf")
	public ResponseEntity<byte[]> mostrar(String caminho) {
		Path path = Paths.get(caminho);
		byte[] pdfContents = null;
		try {
			pdfContents = Files.readAllBytes(path);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + caminho);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]> (
        	pdfContents, headers, HttpStatus.OK);
		return response;
	}

}
