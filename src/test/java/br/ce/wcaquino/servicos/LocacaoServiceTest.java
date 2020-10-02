package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup()
	{
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Cenario
		Usuario usuario = new Usuario("Usuario1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);
			
		// Verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true) );
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception
	{
		
		// Cenario
		Usuario usuario = new Usuario("Usuario1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 0, 4.0));
			
		// Ação
		service.alugarFilme(usuario, filme);
		
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException
	{
		// Cenário
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		// Ação
			try 
			{
				service.alugarFilme(null, filme);
				Assert.fail();
			} 
			catch (LocadoraException e) 
			{
				assertThat(e.getMessage(), is("Usuario vazio!"));
			}
			
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException
	{
		// Cenario
		Usuario usuario = new Usuario("Usuario1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio!");
		
		// Açao
		service.alugarFilme(usuario, null);
		
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException
	{
		// Cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0));
		
		// Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// Verificao
		assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException
	{
		// Cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0),
		new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));
		
		// Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// Verificao
		
		assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException
	{
		// Cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(
		new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), 
		new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), 
		new Filme("Filme 5", 2, 4.0));
		
		// Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// Verificao
		
		assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException
	{
		// Cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(
		new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), 
		new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), 
		new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));
		
		// Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// Verificao
		
		assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException
	{
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		// Acao
		service.alugarFilme(usuario, filmes);
		
		// Verificacao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataLocacao() , Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
	
	
}