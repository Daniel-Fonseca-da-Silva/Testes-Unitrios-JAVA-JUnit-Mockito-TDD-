package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProprios {
	
	public static DiasSemanaMatcher caiEm(Integer diaSemana)
	{
		return new DiasSemanaMatcher(diaSemana);
	}
	
	public static DiasSemanaMatcher caiNumaSegunda()
	{
		return new DiasSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher  eHojeComDiferencaDias(Integer qtdDias)
	{
		
		return new DataDiferencaDiasMatcher(qtdDias);
	}
	
	public static DataDiferencaDiasMatcher  eHoje()
	{
		
		return new DataDiferencaDiasMatcher(0);
	}
	

}
