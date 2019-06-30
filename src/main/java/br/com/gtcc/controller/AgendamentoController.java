package br.com.gtcc.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.Calendar;

import br.com.gtcc.model.Agendamento;
import br.com.gtcc.model.Atividade;
import br.com.gtcc.model.FichaIdentificacao;
import br.com.gtcc.security.Conexao;
import br.com.gtcc.service.AgendamentoService;
import br.com.gtcc.service.AtividadeService;
import br.com.gtcc.service.FichaIdentificacaoService;
import br.com.gtcc.service.ProfessorService;
import groovy.util.ScriptException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
/**
 * 
 * @author Grupo 03 - Ana Cláudia, Ana Paula, Rafael de Souza, Viviane Shiraishi
 *
 */
@Controller
@RequestMapping("/gtcc/agendamentodefesa")
public class AgendamentoController {
	
    @Autowired
    private AgendamentoService agendamentoService;
    
    @Autowired
    private FichaIdentificacaoService fichaIdentificacaoService;
    
    @Autowired
	private ProfessorService professorService;

    @Autowired
    private AtividadeService atividadeService;
    
    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("agendamentodefesa/index");

        List<Agendamento> agendamentos = agendamentoService.listarTodosAtivos();
        List<Agendamento> proximosAgendamentos = new ArrayList<>();
        List<Agendamento> subListaProximosAgendamentos = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        for(Agendamento agendamento : agendamentos) {
        	if (!agendamento.getDataDefesa().isAfter(LocalDate.now()) && agendamento.getApresentado() != 0) {
        		agendamento.setApresentado(1);
        		agendamentoService.atualizar(agendamento);
        	}
        	
        	if(agendamento.getDataDefesa().isAfter(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
        		proximosAgendamentos.add(agendamento);
        	}
        }
        
        if(proximosAgendamentos.size() > 8) {
        	subListaProximosAgendamentos = proximosAgendamentos.subList(0, 7);
        }else {
        	subListaProximosAgendamentos = proximosAgendamentos;
        }

        mv.addObject("agendamentos", subListaProximosAgendamentos);
        return mv;
    }


    @GetMapping("/cadastrar")
    public ModelAndView add(Agendamento agendamento) {
    	
    	List<FichaIdentificacao> fichas = fichaIdentificacaoService.listarTodos();
    	
        ModelAndView mv = new ModelAndView("agendamentodefesa/defesaCreate");
        mv.addObject("fichas", fichas);
        mv.addObject("agendamento", agendamento);
        return mv;
    }
    
    @GetMapping("/cadastrar/{id}")
    public ModelAndView add(@PathVariable("id") Long id) {
    	
    	FichaIdentificacao fichas = fichaIdentificacaoService.buscar(id);
    	
        ModelAndView mv = new ModelAndView("agendamentodefesa/defesaCreate2");
        mv.addObject("fichas", fichas);
        return mv;
    }
    
    @PostMapping("/cadastrar{id}")
    public ModelAndView save(@Valid Agendamento agendamento, BindingResult result) {
        
    	if(agendamento.getFichaIdentificacao() == null){
    		result.addError(new FieldError("agendamento", "fichaIdentificacao", "Selecione um aluno"));
    	}
    	String horario = agendamento.getHorario();
    	if(!(horario.regionMatches(2, "00h00m", 2, 1)) || !(horario.regionMatches(5, "00h00m", 5, 1)) || horario.length() != 6) {
    		result.addError(new FieldError("agendamento", "horario", "Formato incorreto. Por favor, digite no padrão 12h00m"));
    	}
    	
        if (result.hasErrors()) {
            return add(agendamento);
        }
        
        if (agendamento.getDataDefesa().isAfter(LocalDate.now())) {
        	agendamento.setApresentado(0);
        }
        else {
        	agendamento.setApresentado(1);
        }
        
        agendamento.setAtivo(1);
        agendamento.setAno(agendamento.getFichaIdentificacao().getAno());
        agendamentoService.adicionar(agendamento);

        return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", true);
    }
    
    @GetMapping("/pesquisar/{data}")
    public ModelAndView pesquisar(@PathVariable("data") String data) {
    	LocalDate data2Date = LocalDate.parse(data);
    	List<Agendamento> agendamentos = agendamentoService.buscarPorData(data2Date);
    	ModelAndView mv = new ModelAndView("agendamentodefesa/index");
    	mv.addObject("agendamentos", agendamentos);
    	int day = data2Date.getDayOfMonth();
    	int month = data2Date.getMonthValue();
    	String dia = String.valueOf(day);
    	String mes = String.valueOf(month);
    	if (day < 10) {
    		dia = "0" + day;
    	}
    	if (month < 10) {
    		mes = "0" + month;
    	}
    	String data2 = dia + "/" + mes + "/" + data2Date.getYear();
    	mv.addObject("dataPesquisa", data2);
    	return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView("agendamentodefesa/defesaUpdate");
        mv.addObject("professores", professorService.buscarTodos());
        mv.addObject("agendamento", agendamentoService.buscarPorId(id));
        mv.addObject("agendamentoLista", agendamentoService.listarTodosAtivos());

        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView details(@PathVariable("id") Long id) {
    	List<Agendamento> agendamentos = agendamentoService.listarTodosAtivos();
        ModelAndView mv = new ModelAndView("agendamentodefesa/visualizarDefesa");
        mv.addObject("agendamento", agendamentoService.buscarPorId(id));
        mv.addObject("agendamentos", agendamentos);
        return mv;
    }

    @PostMapping("/editar/{id}")
    public ModelAndView update(@Valid Agendamento agendamento, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("agendamentodefesa/defesaUpdate");
            mv.addObject("agendamento", agendamento);
            return mv;
        }
        agendamento.getFichaIdentificacao().setId(agendamentoService.buscarPorId(agendamento.getId()).getFichaIdentificacao().getId());
        agendamento.getFichaIdentificacao().setAluno(agendamentoService.buscarPorId(agendamento.getId()).getFichaIdentificacao().getAluno());
        agendamento.getFichaIdentificacao().setAno(agendamentoService.buscarPorId(agendamento.getId()).getFichaIdentificacao().getAno());
        agendamento.getFichaIdentificacao().setAreaConcentracao(agendamentoService.buscarPorId(agendamento.getId()).getFichaIdentificacao().getAreaConcentracao());
        agendamento.getFichaIdentificacao().setAvaliador1(agendamento.getFichaIdentificacao().getAvaliador1());
        agendamento.getFichaIdentificacao().setAvaliador2(agendamento.getFichaIdentificacao().getAvaliador2());
        agendamento.getFichaIdentificacao().setOrientador(agendamento.getFichaIdentificacao().getOrientador());
        agendamento.getFichaIdentificacao().setTituloTrabalho(agendamento.getFichaIdentificacao().getTituloTrabalho());
        fichaIdentificacaoService.adicionar(agendamento.getFichaIdentificacao());
        agendamento.setAtivo(1);
        
        if (agendamento.getDataDefesa().isAfter(LocalDate.now())) {
        	agendamento.setApresentado(0);
        }
        else {
        	agendamento.setApresentado(1);
        }
        
        agendamento.setAno(agendamento.getFichaIdentificacao().getAno());
        agendamentoService.atualizar(agendamento);
        return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("atualizado", true);
    }

    @GetMapping("/editar/{id}/remover/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
    	Agendamento agendamento = agendamentoService.buscarPorId(id);
    	if(agendamento != null) {
    		agendamento.setAtivo(0);
    		agendamentoService.atualizar(agendamento);
    	}
    	return new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("removido", true);
    }
    
    @PostMapping("/gerar")
   	public ModelAndView gerar(@RequestParam(name = "cursos", required = false) String cursos,
   							  @RequestParam(name = "options", required = false) String options,
   							  @RequestParam(name = "select", required = false) String agendamentoid) throws 
   							InterruptedException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
       	if (cursos == null || options == null) {
       		ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
       		mv.addObject("tipo", "1");
   			return mv;
   		}
       	
       	Integer op = 1;
       	
       	if (Integer.parseInt(options) == 1) {
       		op = 1;
       		if (Integer.parseInt(cursos) == 1) {
       			try {
       				gerarEditalDefesas("edital_defesa.pdf", "info");
       			} catch (JRException e) {
       				ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
       				mv.addObject("tipo", "2");
       				return mv;
       			}
			}
       		else if (Integer.parseInt(cursos) == 2) {
       			try {
       				gerarEditalDefesas("edital_defesa.pdf", "cc");
       			} catch (JRException e) {
       				ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
       				mv.addObject("tipo", "2");
       				return mv;
       			}
       		}
   		}
       	else if (Integer.parseInt(options) == 2) {
       		if (Long.parseLong(agendamentoid) == 0) {
       			op = 3; 
   				List<Agendamento> agendamentos = agendamentoService.listarTodosAtivos();
   				List<Agendamento> agendamentosNaoApresentados = new ArrayList<>();
   				
   				for (Agendamento agendamento : agendamentos) {
   					if (agendamento.getApresentado() == 0 && Integer.parseInt(cursos) == 1 && agendamento.getFichaIdentificacao().getAreaConcentracao().equals("Informática")) {
   						agendamentosNaoApresentados.add(agendamento);
					}
   					else if (agendamento.getApresentado() == 0 && Integer.parseInt(cursos) == 2 && agendamento.getFichaIdentificacao().getAreaConcentracao().equals("Ciência da Computação")) {
   						agendamentosNaoApresentados.add(agendamento);
					}
				}
   				
   				List<JasperPrint> prints = new ArrayList<JasperPrint>();
   				String caminho = new File("./").getAbsolutePath();
   				caminho = caminho.substring(0, caminho.length() - 1);
   				caminho = caminho + "src/main/resources/static/report/";
   				for (Agendamento agendamento : agendamentosNaoApresentados) {
   	    			HashMap<String, Object> parametros = getParametros(agendamento);
   	    			try {
   	    				prints.add(JasperFillManager.fillReport(caminho + "ata_defesa.jasper", parametros, Conexao.getConnection()));
   	    		    	
   	    			} catch (JRException e) {
   	    				ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
   	    				mv.addObject("tipo", "2");
   	    				return mv;
   	    			}
   				}
   				JRPdfExporter exporter = new JRPdfExporter();
   				exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
   				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(caminho + "atas_defesas.pdf"));
   				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
   				exporter.setConfiguration(configuration);
   				try {
   					exporter.exportReport();
   				} catch (JRException e) {
   					ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
   					mv.addObject("tipo", "2");
       				return mv;
   				}
   			} 
       		else {
       			op = 2; 
       			Agendamento agendamento = agendamentoService.buscarPorId(Long.parseLong(agendamentoid));
       			HashMap<String, Object> parametros = getParametros(agendamento);
       			
       			String caminho = new File("./").getAbsolutePath();
       			caminho = caminho.substring(0, caminho.length() - 1);
       			caminho = caminho + "src/main/resources/static/report/";
       			
       			try {
       				gerarAtaDefesa("ata_de_defesa.pdf", parametros);
       			} catch (JRException e) {
       				ModelAndView mv = new ModelAndView("redirect:/gtcc/agendamentodefesa").addObject("sucesso", false);
       				mv.addObject("tipo", "2");
       				return mv;
       			}
   			}
   		}
       	
   		
   		return new ModelAndView("redirect:/gtcc/agendamentodefesa/gerados/" + op);
   	}
       
       @GetMapping("/gerados/{opcao}")
   	public ModelAndView pdfs(@PathVariable Integer opcao) {
   		ModelAndView mv = new ModelAndView("agendamentodefesa/agendamentoPDFs");
   		mv.addObject("opcao", opcao);
   		return mv;
   	}

       public void gerarAtaDefesa(String nome, HashMap<String, Object> parametros) throws JRException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
       	String caminho = new File("./").getAbsolutePath();
       	caminho = caminho.substring(0, caminho.length() - 1);
       	caminho = caminho + "src/main/resources/static/report/";
       	JasperPrint print;
       	print = JasperFillManager.fillReport(caminho + "ata_defesa.jasper", parametros, Conexao.getConnection());
       	JRPdfExporter exporter = new JRPdfExporter();
       	ExporterInput exporterInput = new SimpleExporterInput(print);
       	exporter.setExporterInput(exporterInput);
       	OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
       	exporter.setExporterOutput(exporterOutput);
       	SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
       	exporter.setConfiguration(configuration);
       	exporter.exportReport();
       }
       
       public void gerarEditalDefesas(String nome, String curso) throws JRException, ScriptException, NoSuchMethodException, MalformedURLException, IOException {
       	String caminho = new File("./").getAbsolutePath();
       	HashMap<String, Object> parametros = new HashMap<String, Object>();
       	
       	caminho = caminho.substring(0, caminho.length() - 1);
       	caminho = caminho + "src/main/resources/static/report/";
       	JasperPrint print;
       	print = JasperFillManager.fillReport(caminho + "edital_defesas_" + curso + ".jasper", parametros, Conexao.getConnection());
       	JRPdfExporter exporter = new JRPdfExporter();
       	ExporterInput exporterInput = new SimpleExporterInput(print);
       	exporter.setExporterInput(exporterInput);
       	OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(caminho + nome);
       	exporter.setExporterOutput(exporterOutput);
       	SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
       	exporter.setConfiguration(configuration);
       	exporter.exportReport();
       }
       
       @RequestMapping(value = "/show/{num}", produces =  "application/pdf")
   	public ResponseEntity<byte[]> mostrar(@PathVariable("num") String num) {
   		String caminho = new File("./").getAbsolutePath();
   		caminho = caminho.substring(0, caminho.length() - 1);
   		if (num.equals("1")) {
   			caminho = caminho + "src/main/resources/static/report/edital_defesa.pdf";
   		}
   		else if (num.equals("2")) {
   			caminho = caminho + "src/main/resources/static/report/ata_de_defesa.pdf";
   		}
   		else {
   			caminho = caminho + "src/main/resources/static/report/atas_defesas.pdf";
   		}
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
   	
   	private HashMap<String, Object> getParametros(Agendamento agendamento) {
   		FichaIdentificacao fichaAluno = agendamento.getFichaIdentificacao();
   		List<Atividade> atividades = atividadeService.buscarPorAno(agendamento.getAno());
   		
   		String dataDefesa = agendamento.getDataDefesa().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("pt", "BR")));
   		String dataEntregaFinal = atividades.get(atividades.size()-1).getDataFinalEntrega().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("pt", "BR")));
   		
   		String[] data1 = dataDefesa.split("\\s");
   		String[] data2 = dataEntregaFinal.split("\\s");
   		
   		dataDefesa = data1[0] + " de " + data1[1] + " de " + data1[2];
   		dataEntregaFinal = data2[0] + " de " + data2[1] + " de " + data2[2];
   		String local = "";
   		
   		String[] local1 = agendamento.getLocal().split("\\s");
   		if (local1[local1.length-1].toUpperCase().contentEquals("DIN") || local1.length <= 2) {
   			local = local1[0] + " " + local1[1] + " " + "do Departamento de Informática";
   		}
   		else {
   			local = local1[0] + " " + local1[1] + " " + local1[2];
   		}
   		
   		HashMap<String, Object> parametros = new HashMap<String, Object>();
   		parametros.put("ano", fichaAluno.getAno());
   		parametros.put("data_defesa", dataDefesa);
   		parametros.put("local", local);
   		parametros.put("horario", agendamento.getHorario());
   		parametros.put("titulo_trabalho", fichaAluno.getTituloTrabalho());
   		parametros.put("area_concentracao", fichaAluno.getAreaConcentracao().toUpperCase());
   		parametros.put("nome_avaliador1", fichaAluno.getAvaliador1().getNome());
   		parametros.put("nome_avaliador2", fichaAluno.getAvaliador2().getNome());
   		parametros.put("nome_orientador", fichaAluno.getOrientador().getNome());
   		parametros.put("nome_aluno", fichaAluno.getAluno().getNome());
   		parametros.put("data_final_entrega", dataEntregaFinal);
   		
   		return parametros;
   	}
}
