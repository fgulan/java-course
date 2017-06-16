package hr.fer.zemris.java.student0036479428.hw08.newton;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Program Newton calculates and display fractals based on Newton-Raphson
 * iteration. The program asks user to enter complex roots (when done enter
 * "done"). After that program starts fractal viewer and display the fractal.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Newton {

	/** Minimal root distance */
	private static final double ROOT_DISTANCE = 0.002;
	/** Convergence threshold */
	private static final double CONV_THRESHOLD = 0.001;
	/** Number of iterations */
	private static final double MAX_ITERATIONS = Double.MAX_VALUE;
	/** Complex polynomial */
	private static ComplexRootedPolynomial polynomial;
	/** Derived complex polynomial */
	private static ComplexPolynomial derived;

	/**
	 * Start point of program Newton.
	 * 
	 * @param args
	 *            Command line arguments. Not used.
	 * @throws IOException
	 *             Error with reading from standard input.
	 */
	public static void main(String[] args) throws IOException {
		List<Complex> roots = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));

		System.out
				.println(String
						.format("Welcomese to Newton-Raphson iteration-based fractal viewer.%n"
								+ "Please enter at least two roots, one root per line. Enter 'done' when done"));

		int numberOfRoots = 0;
		while (true) {
			System.out.print("Root " + (numberOfRoots + 1) + "> ");
			String input = reader.readLine();
			if (input == null) {
				continue;
			}
			if (input.equals("done") && numberOfRoots < 2) {
				System.out.println("Please enter at least two roots.");
				continue;
			} else if (input.equals("done")) {
				break;
			} else if (input.isEmpty()) {
				System.out.println("Enter 'done' when done!");
				continue;
			}
			try {
				Complex root = Complex.parse(input);
				roots.add(root);
				numberOfRoots++;
			} catch (Exception e) {
				System.out
						.println("Unable to parse given number! Please try again.");
				continue;
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");

		polynomial = new ComplexRootedPolynomial(
				roots.toArray(new Complex[roots.size()]));
		derived = polynomial.toComplexPolynom().derive();
		FractalViewer.show(new FractalProducer());
	}

	/**
	 * FractalProducer class produces fractal based on Newton-Raphson iteration.
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private static class FractalProducer implements IFractalProducer {

		/** Executor service */
		ExecutorService pool;
		/** Number of threads */
		int numOfThreads;

		/**
		 * Constructor for FractalProducer class. It creates executor service
		 * with number of threads equal to current number of logical processors
		 * on current computer. Also all created threads are daemon threads.
		 */
		public FractalProducer() {
			numOfThreads = Runtime.getRuntime().availableProcessors();
			pool = Executors.newFixedThreadPool(numOfThreads,
					new ThreadFactory() {

						@Override
						public Thread newThread(Runnable r) {
							Thread thread = Executors.defaultThreadFactory()
									.newThread(r);
							thread.setDaemon(true);
							return thread;
						}
					});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			/**
			 * FractalCalculator class represents task for each created thread.
			 * It implements {@link Callable} interface with argument
			 * {@link Void}.
			 * 
			 * @author Filip Gulan
			 * @version 1.0
			 *
			 */
			class FractalCalculator implements Callable<Void> {

				/** Start point on y-axis */
				int yMin;
				/** End point on y-axis */
				int yMax;
				/** Pixels */
				short[] data;

				/**
				 * Constructor for FractalCalculator class.
				 * 
				 * @param yMin
				 *            Start point on y-axis.
				 * @param yMax
				 *            End point on y-axis.
				 * @param data
				 *            Color for each pixel.
				 */
				public FractalCalculator(int yMin, int yMax, short[] data) {
					super();
					this.yMin = yMin;
					this.yMax = yMax;
					this.data = data;
				}

				/**
				 * Computes color for each pixel from {@link #yMin} to
				 * {@link #yMax}.
				 * 
				 * @throws Exception
				 *             On error.
				 */
				@Override
				public Void call() throws Exception {
					int offset = this.yMin * width;
					for (int y = this.yMin; y < this.yMax; y++) {
						for (int x = 0; x < width; x++) {
							double cRe = x / (width - 1.0) * (reMax - reMin)
									+ reMin;
							double cIm = (height - 1 - y) / (height - 1.0)
									* (imMax - imMin) + imMin;
							Complex zn = new Complex(cRe, cIm);
							Complex zn1;
							int iterations = 0;
							double module = 0;
							do {
								Complex numerator = polynomial.apply(zn);
								Complex denominator = derived.apply(zn);
								Complex fraction = numerator
										.divide(denominator);
								zn1 = zn.sub(fraction);
								module = zn1.sub(zn).module();
								zn = zn1;

								iterations++;
							} while ((iterations < MAX_ITERATIONS)
									&& (module > CONV_THRESHOLD));

							short index = (short) polynomial
									.indexOfClosestRootFor(zn1, ROOT_DISTANCE);
							this.data[offset++] = (short) (index == -1 ? 0
									: index + 1);
						}
					}
					return null;
				}
			}
			List<Future<Void>> tasks = new ArrayList<Future<Void>>();
			short[] data = new short[width * height];

			System.out.println("Započinjem izračune...");
			int numOfSections = height / 8 * numOfThreads;
			int sectionHeight = height / numOfSections;

			for (int i = 0; i < numOfSections; i++) {
				int yMin = i * sectionHeight;
				int yMax = (i + 1) * sectionHeight;
				if (i == numOfSections - 1) {
					yMax = height;
				}
				FractalCalculator task = new FractalCalculator(yMin, yMax, data);
				tasks.add(pool.submit(task));
			}

			waitThreads(tasks);
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(data, (short) (polynomial.order() + 1),
					requestNo);
			System.out.println("Dojava gotova...");
		}
	}

	/**
	 * Waits for given threads in a loop.
	 * 
	 * @param tasks
	 *            List of tasks.
	 */
	private static void waitThreads(List<Future<Void>> tasks) {
		for (Future<Void> task : tasks) {
			while (true) {
				try {
					task.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
				}
			}
		}
	}
}
