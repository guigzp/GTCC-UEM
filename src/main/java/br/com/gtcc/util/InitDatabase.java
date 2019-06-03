package br.com.gtcc.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.gtcc.model.Aluno;
import br.com.gtcc.model.Professor;
import br.com.gtcc.repository.AlunoRepository;
import br.com.gtcc.repository.ProfessorRepository;

@Component
public class InitDatabase implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	ProfessorRepository repositoryProfessor;
	@Autowired
	AlunoRepository repositoryAluno;
	
	/*
	 * Alunos inseridos de acordo com a planilha
	 */
	public void initTableAlunos()
	{
		
		String[] alunos = {
					"95379	ANA CLAUDIA LIMA DE ALMEIDA INACIO COSTA",
					"83085	ANDRESSA KARLA DA SILVA PILAR",
					"47735	ANDRÉ DIAS MENEGAZZO PEREIRA",
					"53216	BRUNO FERNANDES",
					"85203	BRUNO ROMERO ZEFERINO",
					"53346	CARLOS TADAO HIRAKURI",
					"83473	CHEN PO HSIANG",
					"54496	EDNER ZUCONELLI",
					"49037	EDUARDO HENRIQUE CICERI",
					"84757	EVANDRO FIORIM ESTIMA",
					"88408	FERNANDO EUGENIO AUGUSTO DE CARVALHO",
					"94116	FRANK RAYONI TAMBORELLI",
					"69695	GUILHERME MIYATA CORRÊA",
					"92859	GUILHERME SOARES VALDEVIESO",
					"67022	GUILHERME TADASHI KIDO",
					"95675	GUSTAVO LUIZ FURUHATA FERREIRA",
					"88410	GUSTAVO MENDONÇA DIAS",
					"57523	HEBER GONÇALVES JUNIOR",
					"61370	HUGO JOSÉ GONÇALVES",
					"83909	LEONARDO POPE GUERRA",
					"94518	LEONARDO PUGLIA ASSUNÇÃO",
					"80817	LIA MIDORI FUGIMOTO",
					"69992	LUIZ TOSHIMITSU NAKAHARA JUNIOR",
					"84756	MARIANA LEITE DE MELO",
					"92760	MATHEUS COLARES DA SILVA",
					"61744	RODRIGO BATILANA RAGIOTO",
					"89935	TADAO HARA",
					"60928	TAMIRES GANZERT BESPALHOK DEPRÁ",
					"89354	TATIANE FERNANDA PAZ GAIESKI",
					"82703	THIAGO ALBERTO",
					"78750	THIAGO KIRA DE SOUZA SAITO",
					"88080	TIAGO PEDROZO DO NASCIMENTO"
			};
		
		
		insertAlunos(alunos);
		
	}
	
	
	public void insertAlunos(String[] alunos)
	{		
		for(String linha : alunos)
		{
			String[] dados = linha.split("\t");
			Aluno aluno = new Aluno();
			aluno.setRa(Integer.parseInt(dados[0]));
			aluno.setNome(dados[1]);
			aluno.setEmail(dados[0]+"@uem.br");
			aluno.setAtivo(1);
			aluno.setSenha(dados[0]);
			aluno.setNomeUsuario("user"+dados[0]);
			aluno.setTelefone("4002-8922");
			
			repositoryAluno.save(aluno);
			
		}
	}
	
	
	public void initTableProfessores()
	{
		String professores = "Thelma Elita Colanzi Lopes\r\n" + 
				"Aline Maria Malachini Miotto Amaral\r\n" + 
				"Raqueline Ritter de Moura Penteado\r\n" + 
				"Edson Alves de Oliveira Junior\r\n" + 
				"André Felipe Ribeiro Cordeiro\r\n" + 
				"Lucas de Oliveira Teixeira\r\n" + 
				"Airton Marco Polidório\r\n" + 
				"Dante Alves Medeiros Filho\r\n" + 
				"Nardênio Almeida Martins	\r\n" + 
				"Rodrigo Calvo\r\n" + 
				"Carlos Fransley Scatambulo Costa\r\n" + 
				"Linnyer Beatrys Ruiz Aylon\r\n" + 
				"Donizete Carlos Bruzarosco\r\n" + 
				"André Noel\r\n" + 
				"Wagner Igarashi\r\n" + 
				"Flávio Arnaldo Braga da Silva\r\n" + 
				"Marcos Aurélio Domingues	\r\n" + 
				"Franklin César Flores\r\n" + 
				"Valéria Delisandra Feltrim\r\n" + 
				"Lucas Pupulin Nanni\r\n" + 
				"Maurílio Martins Campano Junior\r\n" + 
				"Daniel Kikuti\r\n" + 
				"Ademir Aparecido Constantino\r\n" + 
				"Yandre Maldonado e Gomes da Costa	\r\n" + 
				"Josiane Melchiori Pinheiro\r\n" + 
				"João Alencar Pamphile\r\n" + 
				"Ronaldo Augusto de Lara Gonçalves\r\n" + 
				"Paulo Roberto de Oliveira\r\n" + 
				"André Barbosa Verona\r\n" + 
				"Juliana Keiko Yamaguchi\r\n";
		
		insertProfessores(professores);
		
	}
	
	public void insertProfessores(String professores)
	{	
		String[] nomes = professores.split("\r\n");
		int matricula = 10000;
		for(String nome : nomes)
		{
			matricula++;
			Professor professor = new Professor();
			professor.setMatricula(matricula);
			professor.setCoordenador(false);
			professor.setNome(nome);
			professor.setEmail(matricula+"@uem.br");
			professor.setAtivo(1);
			professor.setSenha(String.valueOf(matricula));
			professor.setNomeUsuario("user"+matricula);
			professor.setTelefone("4002-8922");
			//seta a Thelma como coordenadora
			if(nome.contains("Thelma Elita Colanzi"))
			{
				professor.setCoordenador(true);
			}
			
			repositoryProfessor.save(professor);
			
		}
	}
	
	
	public void executar() {

		try {
			
			if(repositoryAluno.countRows() == 0)
			{
				initTableAlunos();
				System.out.println("Alunos cadastrados com sucesso!");
			}
			if(repositoryProfessor.countRows() == 0)
			{
				initTableProfessores();
				System.out.println("Professores cadastrados com sucesso!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*
		 * Após a primeira execução comente o método executar() para que não seja chamado novamente
		 */
		executar();
		
	}
	
	
}
