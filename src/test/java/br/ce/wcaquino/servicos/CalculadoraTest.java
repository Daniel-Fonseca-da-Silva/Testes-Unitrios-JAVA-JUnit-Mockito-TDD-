package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exception.NãoPodeDividirPorZeroExeption;


public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup()
	{
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores()
	{
		// Cenário
		int a = 5;
		int b = 3;
		
		// Ação
		int resultado = calc.somar(a, b);
		
		
		// Verificação
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() 
	{
		
		// Cenário
		int a = 8;
		int b = 5;
		
		// Ação
		
		int resultado = calc.subtrair(a, b);
		
		// Verificação
		
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NãoPodeDividirPorZeroExeption
	{
		// Ação
		
		int a = 6;
		int b = 3;
		
		// Cenário
		
		int resultado = calc.dividi(a, b);
		
		// Verificação
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NãoPodeDividirPorZeroExeption.class)
	public void deveLancarExecaoAoDividirPorZero() throws NãoPodeDividirPorZeroExeption 
	{
		int a = 10;
		int b = 0;
		
		calc.dividi(a, b);
	}
	
	@Test
	public void deveDividir()
	{
		String a = "6";
		String b = "3";
		
		int resultado = calc.divide(a, b);
		Assert.assertEquals(2, resultado);
	}
	
}
