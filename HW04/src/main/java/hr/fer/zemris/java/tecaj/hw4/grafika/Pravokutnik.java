package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred Pravokutnik je dijete razred razreda GeometrijskiLik.
 * Svaki pravokutnik je definiran s koordinatom gornje lijeve točke
 * te širinom i visinom.
 * 
 * <p>Razred Pravokutnik implementira svoju metodu popuniLik() koja je
 * učinkovitija od one dane u razredu GeometrijskiLik.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Pravokutnik extends GeometrijskiLik {

	/** Koordinata gornjeg lijevog ugla na x-osi */
	private int vrhX;
	/** Koordinata gornjeg lijevog ugla na y-osi */
	private int vrhY;
	/** Širina danog pravokutnika */
	private int sirina;
	/** Visina danog pravokutnika */
	private int visina;

	/**
	 * Konstruktor razreda Pravokutnik. Stvara geometrijski lik pravokutnika
	 * za zadanu točku gornjeg lijevog vrha, te širinu i visinu.
	 * @param vrhX Koordinata gornjeg lijevog ugla pravokutnika na x-osi.
	 * @param vrhY Koordinata gornjeg lijevog ugla pravokutnika na y-osi.
	 * @param sirina Širina pravokutnika.
	 * @param visina Visina pravokutnika.
	 */
	public Pravokutnik(int vrhX, int vrhY, int sirina, int visina) {
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}

	/**
	 * Provjerava da li trenutni pravokutnik sadržava danu koordinatnu točku.
	 * @param x Kooridinatna točka na x-osi.
	 * @param y Kooridinatna točka na y-osi.
	 * @return Vraća <code>true</code> ukoliko trenutni pravokuntik sadržava danu
	 * točku, <code>false</code> inače.
	 */
	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < vrhX || x >= vrhX + sirina) {
			return false;
		}
		if (y < vrhY || y >= vrhY + visina) {
			return false;
		}
		return true;
	}
	
	/**
	 * Popopunjava danu sliku s trenutnim pravokutnikom. Metoda nadjačava 
	 * metodu popuniLik() iz razreda geometrijski lik s učinkovitijim
	 * i bržim algoritmom za iscrtavanje pravokutnika.
	 * @param slika Slika na koju iscrtavamo trenutni pravokutnik.
	 */
	@Override
	public void popuniLik(Slika slika) {
		int yKraj = Math.min(vrhY + visina, slika.getVisina());
		int xKraj = Math.min(vrhX + sirina, slika.getSirina());
		vrhY = Math.max(0, vrhY);
		vrhX = Math.max(0, vrhX);
		
		for (int y = vrhY ; y < yKraj; y++) {
			for (int x = vrhX; x < xKraj; x++) {
				slika.upaliTocku(x, y);
			}
		}
	}
	
	/** Konstanta stvaratelja lika */
	public static final StvarateljLika STVARATELJ = new PravokutnikStvaratelj();
	
	/**
	 * Privatni razred koji implementira sučelje StvarateljLika.
	 * Dani razred stvara lik pravokutnika s imenom "PRAVOKUTNIK"
	 * i parametrima danim u metodi stvoriIzStringa();
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 * 
	 */
	private static class PravokutnikStvaratelj implements StvarateljLika {
		
		/**
		 * Vraća naziv geometrijskog lika pravokutnika
		 * kao "PRAVOKUTNIK".
		 * @return Niz znakova "PRAVOKUTNIK".
		 */
		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}
		
		/**
		 * Stvara lik pravokutnika s danim arugumentima. Metoda kao argument prima
		 * string koji sadrži koordinatnu točku gornjeg lijevog vrha, te širinu i
		 * visin pravokutnika, npr. u obliku "20 30 150 200". Ukoliko dimenzije i točka
		 * prelaze okvire slike iscrtat će se samo onaj dio koji bi bio vidljiv na dijelu slike od
		 * točke (0, 0) do točke (sirina-1, visina -1).
		 * @param parametri Točka gornjeg lijevog vrha, te širina i visina.
		 * @return Stvoreni geometrijski lik razreda Pravokutnik.
		 * @throws IllegalArgumentException Ukoliko su ulazni parametri neispravni.
		 */
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			ParserArgumenata parser = new ParserArgumenata(parametri);
			if(parser.brojArgumenata() != 4) {
				throw new IllegalArgumentException("Neispravan broj argumenata za lik pravokutnika!");
			}
			int[] arg = parser.getPoljeArgumenata();
			if(arg[2] <= 0 || arg[3] <= 0) {
				throw new IllegalArgumentException("Širina i visina pravokutnika moraju biti pozitivni cijeli broj!");
			}
			return new Pravokutnik(arg[0], arg[1], arg[2], arg[3]);
		}
	}

}
