package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program RayCaster demonstrates rendering 3D scenes using ray-tracing
 * algorithm and Phong lighting model without parallelization.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class RayCaster {

	/** Minimal distance between two intersections. */
	static final double MIN_DISTANCE = 1e-3;

	/**
	 * Start point of program RayCaster.
	 * 
	 * @param args
	 *            Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Returns new ray tracer object which is capable to create scene snapshots
	 * by using ray-tracing technique.
	 * 
	 * @return IRayTracerProducer object.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D eyeView = (view.sub(eye)).normalize();

				// Point3D zAxis = new Point3D(0, 0, 0);
				Point3D yAxis = viewUp
						.normalize()
						.sub(eyeView.scalarMultiply(viewUp.normalize()
								.scalarProduct(eyeView))).normalize();
				Point3D xAxis = eyeView.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(
						xAxis.scalarMultiply(horizontal / 2.0)).add(
						yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(
								xAxis.scalarMultiply(x / (width - 1.0)
										* horizontal)).sub(
								yAxis.scalarMultiply(y / (height - 1.0)
										* vertical));

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Traces image on scene using ray-casting algorithm, computes RGB
	 * components for current pixel and fills <code>rgb</code> array.
	 * 
	 * @param scene
	 *            Current scene.
	 * @param ray
	 *            Current ray from eye to screen.
	 * @param rgb
	 *            RGB components for given pixel.
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		short[] rgbComputed = new short[3];

		RayIntersection intersection = getIntersection(ray, scene);
		if (intersection != null) {
			determineColorFor(ray, intersection, rgbComputed, scene);
		}
		for (int i = 0; i < rgbComputed.length; i++) {
			rgb[i] = (short) rgbComputed[i];
		}
	}

	/**
	 * Returns closest intersection of ray and object in scene.
	 * 
	 * @param ray
	 *            Current ray from eye to screen.
	 * @param scene
	 *            Current scene.
	 * @return Closes intersection of ray and object in scene.
	 */
	private static RayIntersection getIntersection(Ray ray, Scene scene) {
		RayIntersection visiblePixel = null;

		for (GraphicalObject figure : scene.getObjects()) {
			RayIntersection intersection = figure
					.findClosestRayIntersection(ray);

			if (intersection != null
					&& (visiblePixel == null || intersection.getDistance() < visiblePixel
							.getDistance())) {
				visiblePixel = intersection;
			}
		}
		return visiblePixel;
	}

	/**
	 * Computes RGB components for given ray and intersection on scene and fills
	 * <code>rgb</code> array.
	 * 
	 * @param ray
	 *            Current ray from eye to screen.
	 * @param intersection
	 *            Closest intersection.
	 * @param rgb
	 *            RGB components for given pixel.
	 * @param scene
	 *            Current scene.
	 */
	private static void determineColorFor(Ray ray,
			RayIntersection intersection, short[] rgb, Scene scene) {

		// Set ambient component
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = 15;
		}

		for (LightSource lightSource : scene.getLights()) {
			Ray lightRay = Ray.fromPoints(lightSource.getPoint(),
					intersection.getPoint());

			RayIntersection closestIntersection = getIntersection(lightRay,
					scene);

			if (closestIntersection != null) {
				double firstDistance = lightSource.getPoint()
						.sub(closestIntersection.getPoint()).norm();
				double secondDistance = lightSource.getPoint()
						.sub(intersection.getPoint()).norm();

				if (secondDistance - firstDistance <= MIN_DISTANCE) {
					diffuseComp(lightSource, rgb, closestIntersection);
					reflectionComp(lightSource, ray, rgb, closestIntersection);
				}
			}
		}
	}

	/**
	 * Computes diffuse component for given intersection and light source and
	 * fills <code>rgb</code> array.
	 * 
	 * @param light
	 *            Light source.
	 * @param rgb
	 *            RGB components for given pixel.
	 * @param intersection
	 *            Current closest intersection.
	 */
	private static void diffuseComp(LightSource light, short[] rgb,
			RayIntersection intersection) {
		Point3D n = intersection.getNormal();
		Point3D l = light.getPoint().sub(intersection.getPoint()).normalize();
		double cosine = l.scalarProduct(n);

		rgb[0] += light.getR() * intersection.getKdr() * Math.max(cosine, 0);
		rgb[1] += light.getG() * intersection.getKdg() * Math.max(cosine, 0);
		rgb[2] += light.getB() * intersection.getKdb() * Math.max(cosine, 0);
	}

	/**
	 * Computes reflection component for given intersection and light source and
	 * fills <code>rgb</code> array.
	 * 
	 * @param light
	 *            Light source.
	 * @param ray
	 *            Current ray from eye to screen.
	 * @param rgb
	 *            RGB components for given pixel.
	 * @param intersection
	 *            Current closest intersection.
	 */
	private static void reflectionComp(LightSource light, Ray ray, short[] rgb,
			RayIntersection intersection) {
		Point3D n = intersection.getNormal();
		Point3D l = light.getPoint().sub(intersection.getPoint());

		Point3D k = n.scalarMultiply(l.scalarProduct(n));
		Point3D r = k.scalarMultiply(2).sub(l).normalize();
		Point3D v = ray.start.sub(intersection.getPoint()).normalize();

		double cosine = r.scalarProduct(v);

		if (cosine >= 0.0) {
			cosine = Math.pow(cosine, intersection.getKrn());

			rgb[0] += light.getR() * intersection.getKrr() * cosine;
			rgb[1] += light.getG() * intersection.getKrg() * cosine;
			rgb[2] += light.getB() * intersection.getKrb() * cosine;
		}
	}
}
