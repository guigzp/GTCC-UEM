package br.com.gtcc.controller;
import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.DeclaracaoParticipante;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.model.Professor;
import br.com.gtcc.repository.AgendamentoRepository;
import br.com.gtcc.repository.DeclaracaoParticipanteRepository;
import br.com.gtcc.repository.FichaIdentificacaoRepository;
import br.com.gtcc.repository.ProfessorRepository;
import br.com.gtcc.security.Conexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptException;

@Controller
@RequestMapping("/gtcc/declaracoes")
public class DeclaracaoParticipanteController {
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private DeclaracaoParticipanteRepository declaracaoParticipanteRepository;

	@Autowired
	private FichaIdentificacaoRepository fichaIdentificacaoRepository;
	
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@GetMapping("/consultar")
	public ModelAndView view(FichaIdentificacao filtro) {
		
		ModelAndView mv = new ModelAndView("declaracoes/consulta");
		mv.addObject("declaracoes", this.professorRepository.findAll());
		mv.addObject("declaracoesPP", this.declaracaoParticipanteRepository.findAll());
		return mv;
	}
	
	@PostMapping("/consultar")
	public ModelAndView listarEspecifico(FichaIdentificacao filtro)
	{
		return view(filtro);
	}
	
	
	@GetMapping("/cadastro")
	public ModelAndView index() {
		LocalDate data = LocalDate.now();
		List<Agendamento> agendamentos = this.agendamentoRepository.findByDataDefesaLessThanEqual(data);
		List<FichaIdentificacao> fichaIdentificacaos = new ArrayList<>();
		for(Agendamento agen: agendamentos){
			fichaIdentificacaos.add(agen.getFichaIdentificacao());
		}
		ModelAndView mv = new ModelAndView("declaracoes/cadastro");
		mv.addObject("fichaIdentificacaos", fichaIdentificacaos);
		return mv;
	}

	@PostMapping("/salvar/")
	public ModelAndView gerar(@RequestParam(name = "alunoS", required = false) String alunoid,
			@RequestParam(name = "nomeReadOnly", required = false) String lixo,
			@RequestParam(name = "carga", required = false) Double carga,
			@RequestParam(name = "nome[]", required = false) String nome,
			@RequestParam(name = "ra[]", required = false) String ra,
			@RequestParam(name = "curso[]", required = false) String curso) throws InterruptedException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
		
		String cursoV = fichaIdentificacaoRepository.findByAlunoIdUsuario(Long.parseLong(alunoid)).get(0).getAreaConcentracao();
		//mudar para ano do agendamento
		Integer ano = fichaIdentificacaoRepository.findByAlunoIdUsuario(Long.parseLong(alunoid)).get(0).getAno();
		String[] nomes = nome.split(",");
		String[] ras = ra.split(",");
		String[] cursos = curso.split(",");
		
		for(int i=0; i < nomes.length; i++) {
			declaracaoParticipanteRepository.saveAndFlush(new DeclaracaoParticipante(nomes[i],ras[i],cursos[i],carga,cursoV, ano));
		}
		
		return new ModelAndView("redirect:/gtcc/declaracoes/cadastro").addObject("sucesso", true);
	}
	
	@PostMapping("/gerar/PP/{nome}/{anoS}")
	public ModelAndView gerarPdfParticipante(@PathVariable String nome, @PathVariable String anoS) throws NoSuchMethodException, MalformedURLException, JRException, ScriptException, IOException {
		String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		int dia = LocalDate.now().getDayOfMonth();
		String mes = meses[LocalDate.now().getMonthValue()];
		int anoGeracao = LocalDate.now().getYear();
		String dataAtual = String.valueOf(dia) + " de " + mes + " de " + String.valueOf(anoGeracao);
		
		System.out.println(anoS);
		Integer ano = Integer.parseInt(anoS);
		
		System.out.println(nome);
		List<DeclaracaoParticipante> decs = declaracaoParticipanteRepository.findByNomeParticipante(nome);
		DeclaracaoParticipante dec = decs.get(0);
		
		List<DeclaracaoParticipante> participantes = declaracaoParticipanteRepository.findByNomeParticipanteAndAno(dec.getNomeParticipante(), ano);
		double cargaHorariaCC = 0;
		double cargaHorariaInfo = 0;
		for(DeclaracaoParticipante d : participantes) {
			if (d.getCursoVisto().equals("Informática")) {
				cargaHorariaInfo += d.getCargaHoraria();
			}else {
				cargaHorariaCC += d.getCargaHoraria();
			}
		}
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("NomeDoAluno", dec.getNomeParticipante());
		parametros.put("Data", dataAtual);
		parametros.put("RA", dec.getRaParticipante());
		parametros.put("CursoAluno", dec.getCursoParticipante());
		parametros.put("Ano", String.valueOf(ano));
		System.out.println(parametros);
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		
		if(cargaHorariaInfo > 0 && !professorRepository.findByCurso("Informática").isEmpty()) {
			parametros.put("CoordenadorCurso", professorRepository.findByCurso("Informática").get(0).getNome());
			parametros.put("NomeCurso", "Informática");
			parametros.put("CargaHoraria", String.valueOf(cargaHorariaInfo));
			gerarProposta("DeclaracaoInfo.pdf", parametros);
		}
		
		if(cargaHorariaCC > 0 && !professorRepository.findByCurso("Ciência da Computação").isEmpty()) {
			parametros.put("CoordenadorCurso", professorRepository.findByCurso("Ciência da Computação").get(0).getNome());
			parametros.put("NomeCurso", "Ciência da Computação");
			parametros.put("CargaHoraria", String.valueOf(cargaHorariaCC));
			gerarProposta("DeclaracaoCC.pdf", parametros);
		}
		ModelAndView mv = new ModelAndView("redirect:/gtcc/declaracoes/consultar").addObject("sucesso", true);
		return mv;
	}
	
	
		
	@PostMapping("/gerar/P/{nome}/{anoS}")
	public ModelAndView gerarPdfProfessor(@PathVariable String nome, @PathVariable String anoS) throws NoSuchMethodException, MalformedURLException, JRException, ScriptException, IOException {
		String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		int dia = LocalDate.now().getDayOfMonth();
		String mes = meses[LocalDate.now().getMonthValue()];
		int anoGeracao = LocalDate.now().getYear();
		String dataAtual = String.valueOf(dia) + " de " + mes + " de " + String.valueOf(anoGeracao);
		
		System.out.println(anoS);
		Integer ano = Integer.parseInt(anoS);
		
		
		List<Professor> decs = professorRepository.findByNome(nome);
		System.out.println(nome);
		Professor dec = decs.get(0);
		
		//List<FichaIdentificacao> fichas = fichaIdentificacaoRepository.findByOrientador(dec.getIdProfessor());
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("Professor", dec.getNome());
		parametros.put("Ano", String.valueOf(ano));
		parametros.put("Data", dataAtual);
		
		
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		
		
		if(dec.getOrientador()) {
			parametros.put("IdProfessor", dec.getIdProfessor().toString());
			if(!professorRepository.findByCurso("Informática").isEmpty()) {
				parametros.put("CoordenadorCurso", professorRepository.findByCurso("Informática").get(0).getNome());
				parametros.put("NomeCurso", "Informática");
				gerarPropostaOri("DeclaracaoInfoOrientador.pdf", parametros);
			}
			if(!professorRepository.findByCurso("Ciência da Computação").isEmpty()) {
				parametros.put("CoordenadorCurso", professorRepository.findByCurso("Ciência da Computação").get(0).getNome());
				parametros.put("NomeCurso", "Ciência da Computação");
				gerarPropostaOri("DeclaracaoCCOrientador.pdf", parametros);
			}
		}
		if(dec.getAvaliador()) {
			parametros.put("IdProfessor", dec.getIdProfessor().toString());
			if(!professorRepository.findByCurso("Informática").isEmpty()) {
				parametros.put("CoordenadorCurso", professorRepository.findByCurso("Informática").get(0).getNome());
				parametros.put("NomeCurso", "Informática");
				gerarPropostaAvali("DeclaracaoInfoAvaliador.pdf", parametros);
			}
			if(!professorRepository.findByCurso("Ciência da Computação").isEmpty()) {
				parametros.put("CoordenadorCurso", professorRepository.findByCurso("Ciência da Computação").get(0).getNome());
				parametros.put("NomeCurso", "Ciência da Computação");
				gerarPropostaAvali("DeclaracaoCCAvaliador.pdf", parametros);
			}
			
			
		}
		
		ModelAndView mv = new ModelAndView("redirect:/gtcc/declaracoes/consultar").addObject("sucesso", true);
		return mv;
	}
	
	public void gerarProposta(String nome, HashMap<String, Object> parametros) throws JRException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		JasperPrint print;
		print = JasperFillManager.fillReport(caminho + "DeclaracaoParticipante.jasper", parametros, Conexao.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = new SimpleExporterInput(print);
		exporter.setExporterInput(exporterInput);
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
		exporter.setExporterOutput(exporterOutput);
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}
	
	public void gerarPropostaOri(String nome, HashMap<String, Object> parametros) throws JRException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		JasperPrint print;
		print = JasperFillManager.fillReport(caminho + "DeclaracaoOrientador.jasper", parametros, Conexao.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = new SimpleExporterInput(print);
		exporter.setExporterInput(exporterInput);
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
		exporter.setExporterOutput(exporterOutput);
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}
	
	public void gerarPropostaAvali(String nome, HashMap<String, Object> parametros) throws JRException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
		String caminho = new File("./").getAbsolutePath();
		caminho = caminho.substring(0, caminho.length() - 1);
		caminho = caminho + "src/main/resources/static/report/";
		JasperPrint print;
		print = JasperFillManager.fillReport(caminho + "DeclaracaoAvaliador.jasper", parametros, Conexao.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = new SimpleExporterInput(print);
		exporter.setExporterInput(exporterInput);
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
		exporter.setExporterOutput(exporterOutput);
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}

}
