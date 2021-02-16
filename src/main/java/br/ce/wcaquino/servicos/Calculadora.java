package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exception.NãoPodeDividirPorZeroExeption;

public class Calculadora {

	public int somar(int a, int b) 
	{
		return a + b;
	}

	public int subtrair(int a, int b) 
	{
		return  a - b;
	}

	public int dividi(int a, int b) throws NãoPodeDividirPorZeroExeption 
	{
		if(b == 0)
		{
			throw new NãoPodeDividirPorZeroExeption();
		}
		
		return a / b;
	}
	
	public int divide(String a, String b)
	{
		return Integer.valueOf(a) / Integer.valueOf(b);
	}
	
}
