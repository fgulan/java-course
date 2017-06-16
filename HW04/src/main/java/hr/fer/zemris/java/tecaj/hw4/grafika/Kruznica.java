package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Razred Kruznica je dijete razred razreda Elipsa koji je dijete
 * razred razreda GeometrijskiLik. Svaka kružnica je definirana
 * s koordinatom točke središta te polumjerom kružnice.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Kruznica extends Elipsa {
	
	/**
	 * Konstruktor razreda Kruznica. Stvara lik kružnice s danom
	 * točkom središta i polumjerom.
	 * @param sredisteX Koordinata x središta kružnice.
	 * @param sredisteY Koordinata y središta kružnice.
	 * @param polumjer Polumjer kružnice.
	 */
	public Kruznica(int sredisteX, int sredisteY, int polumjer) {
		super(sredisteX, sredisteY, polumjer, polumjer);
	}

	/** Konstanta stvaratelja lika */
	public static final StvarateljLika STVARATELJ = new KruznicaStvaratelj();
	
	/**
	 * Privatni razred koji implementira sučelje StvarateljLika.
	 * Dani razred stvara lik kvadrata s imenom "KVADRAT"
	 * i parametrima danim u metodi stvoriIzStringa();
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 * 
	 */
	private static class KruznicaStvaratelj implements StvarateljLika {
		
		/**
		 * Vraća naziv geometrijskog lika kruga
		 * kao "KRUG".
		 * @return Niz znakova "KRUG".
		 */
		@Override
		public String nazivLika() {
			return "KRUG";
		}
		
		/**
		 * Stvara lik kružnice s danim arugumentima. Metoda kao argument prima
		 * string koji sadrži koordinatnu točku središta kružnice, te polumjer 
		 * krućnice, npr. u obliku "100 150 100". Ukoliko dimenzije i točka
		 * prelaze okvire slike iscrtat će se samo onaj dio koji bi bio vidljiv na dijelu slike od
		 * točke (0, 0) do točke (sirina-1, visina -1).
		 * @param parametri Točka središta kružnice, te polumjer kružnice.
		 * @return Stvoreni geometrijski lik razreda Kruznica.
		 * @throws IllegalArgumentException Ukoliko su ulazni parametri neispravni.
		 */
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			ParserArgumenata parser = new ParserArgumenata(parametri);
			if(parser.brojArgumenata() != 3) {
				throw new IllegalArgumentException("Neispravan broj argumenata za lik kruga!");
			}
			int[] arg = parser.getPoljeArgumenata();
			if(arg[2] <= 0) {
				throw new IllegalArgumentException("Polumjer kruga mora biti veći od nule!");
			}
			return new Kruznica(arg[0], arg[1], arg[2]);
		}
	}
}
