package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sučelje za klase koje će imati mogućnost stvaranja
 * zadanog geometrijskog lika.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface StvarateljLika {
	
	/**
	 * Vraća naziv geometrijskog lika
	 * @return Naziv geometrijskog lika.
	 */
	String nazivLika();
	
	/**
	 * Na temelju zadanog niza znakova stvara geometrijski lik s parametrima
	 * koji se nalaze u argumentu <code>parametri</code>.
	 * @param parametri Znakovni niz koji sadrži dimenzije geometrijskog lika.
	 * @return Novi geometrijski lik s dimenzijama zadanim u ulaznom argumentu. 
	 */
	GeometrijskiLik stvoriIzStringa(String parametri);
}
