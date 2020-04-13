package fachlogik;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Access(AccessType.FIELD)
public class MagnetResonanz extends Untersuchung
{
	private static BigDecimal PreisProMinute = new BigDecimal("7.0");
	private static BigDecimal PreisProSerie = new BigDecimal("2.0");
	private static BigDecimal PreisNachbearbeitung = new BigDecimal("2.0");
	private int anzahlSerien;
	private int dauerNachbearbeitung;

	public MagnetResonanz()
	{
		super();
		this.setBezeichnung("MR");
	}
	
	public int getAnzahlSerien()
	{
		return anzahlSerien;
	}

	public void setAnzahlSerien(int anzahlSerien)
	{
		this.anzahlSerien = anzahlSerien;
	}

	public int getDauerNachbearbeitung()
	{
		return dauerNachbearbeitung;
	}

	public void setDauerNachbearbeitung(int dauerNachbearbeitung)
	{
		this.dauerNachbearbeitung = dauerNachbearbeitung;
	}

	public static BigDecimal getPreisProMinute()
	{
		return PreisProMinute;
	}

	public static BigDecimal getPreisProSerie()
	{
		return PreisProSerie;
	}

	public static BigDecimal getPreisNachbearbeitung()
	{
		return PreisNachbearbeitung;
	}
}
