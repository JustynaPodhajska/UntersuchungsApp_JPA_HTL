package fachlogik;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Ultraschall extends Untersuchung
{

	private static BigDecimal PreisProMinute = new BigDecimal("2.0");

	public Ultraschall()
	{
		super();
		this.setBezeichnung("US");
	}

	public static BigDecimal getPreisProMinute()
	{
		return PreisProMinute;
	}
}
