package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Razred Kvadrat je dijete razred razreda Pravoktnik koji je dijete
 * razred razreda GeometrijskiLik. Svaki kvadrat je definiran
 * s koordinatom točke gornjeg lijevog vrha te dimenzijom stranice.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Kvadrat extends Pravokutnik {
	
	/**
	 * Konstruktor razreda Kvadrat. Stvara geometrijski lik kvadrata
	 * za zadanu točku gornjeg lijevog vrha, te dimenziju stranice.
	 * @param vrhX Koordinata gornjeg lijevog vrha kvadrata na x-osi.
	 * @param vrhY Koordinata gornjeg lijevog vrha kvadrata na y-osi.
	 * @param dimenzija Dimenzija stranice kvadrata.
	 */
	public Kvadrat(int vrhX, int vrhY, int dimenzija) {
		super(vrhX, vrhY, dimenzija, dimenzija);
	}

	/** Konstanta stvaratelja lika */
	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj();
	
	/**
	 * Privatni razred koji implementira sučelje StvarateljLika.
	 * Dani razred stvara lik kvadrata s imenom "KVADRAT"
	 * i parametrima danim u metodi stvoriIzStringa();
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 * 
	 */
	private static class KvadratStvaratelj implements StvarateljLika {
		
		/**
		 * Vraća naziv geometrijskog lika kvadrata
		 * kao "KVADRAT".
		 * @return Niz znakova "KVADRAT".
		 */
		@Override
		public String nazivLika() {
			return "KVADRAT";
		}
		
		/**
		 * Stvara lik kvadrata s danim arugumentima. Metoda kao argument prima
		 * string koji sadrži koordinatnu točku gornjeg lijevog vrha, te dimenziju 
		 * stranice kvadrata, npr. u obliku "20 30 150". Ukoliko dimenzija i točka
		 * prelaze okvire slike iscrtat će se samo onaj dio koji bi bio vidljiv na dijelu slike od
		 * točke (0, 0) do točke (sirina-1, visina -1).
		 * @param parametri Točka gornjeg lijevog vrha, te dimenzija stranice.
		 * @return Stvoreni geometrijski lik razreda Kvadrat.
		 * @throws IllegalArgumentException Ukoliko su ulazni parametri neispravni.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			ParserArgumenata parser = new ParserArgumenata(parametri);
			if(parser.brojArgumenata() != 3) {
				throw new IllegalArgumentException("Neispravan broj argumenata za lik kvadrata!");
			}
			int[] arg = parser.getPoljeArgumenata();
			if(arg[2] <= 0) {
				throw new IllegalArgumentException("Dimenzija kvadrata mora biti veća od nule!");
			}
			return new Kvadrat(arg[0], arg[1], arg[2]);
		}
	}
		
}
