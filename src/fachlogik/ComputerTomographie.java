package fachlogik;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class ComputerTomographie extends Untersuchung
{
	private static BigDecimal PreisProMinute = new BigDecimal("4.0");
	private static BigDecimal PreisProSerie = new BigDecimal("2.0");
	private int anzahlSerien;

	public ComputerTomographie()
	{
		super();
		this.setBezeichnung("CT");
	}
	
	public int getAnzahlSerien()
	{
		return anzahlSerien;
	}

	public void setAnzahlSerien(int anzahlSerien)
	{
		this.anzahlSerien = anzahlSerien;
	}

	public static BigDecimal getPreisProMinute()
	{
		return PreisProMinute;
	}

	public static BigDecimal getPreisProSerie()
	{
		return PreisProSerie;
	}
}
