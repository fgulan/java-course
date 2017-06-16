package hr.fer.zemris.java.raytracer.model;

/**
 * Class Sphere represents sphere object in space. It extends
 * {@link GraphicalObject} class.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class Sphere extends GraphicalObject {

	/** Center point */
	private Point3D center;
	/** Sphere radius */
	private double radius;
	/** Diffuse component for red color */
	private double kdr;
	/** Diffuse component for green color */
	private double kdg;
	/** Diffuse component for blue color */
	private double kdb;
	/** Reflective component for red color */
	private double krr;
	/** Reflective component for green color */
	private double krg;
	/** Reflective component for blue color */
	private double krb;
	/** Coefficient n for reflective component */
	private double krn;

	/**
	 * Constructor for Sphere class. Creates sphere object with given
	 * parameters.
	 * 
	 * @param center
	 *            Center point.
	 * @param radius
	 *            Sphere radius.
	 * @param kdr
	 *            Diffuse component for red color.
	 * @param kdg
	 *            Diffuse component for green color.
	 * @param kdb
	 *            Diffuse component for blue color.
	 * @param krr
	 *            Reflective component for red color.
	 * @param krg
	 *            Reflective component for green color.
	 * @param krb
	 *            Reflective component for blue color.
	 * @param krn
	 *            Coefficient n for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D rayDirection = ray.direction;
		Point3D rayStart = ray.start;
		Point3D eyeToCenter = ray.start.sub(this.center);

		// Discriminant components
		double a = 1; // because normalized_vector^2 = 1;
		double b = rayDirection.scalarMultiply(2.0).scalarProduct(eyeToCenter);
		double c = eyeToCenter.scalarProduct(eyeToCenter)
				- Math.pow(this.radius, 2);

		// Discriminant
		double discriminant = Math.pow(b, 2) - (4 * a * c);

		if (discriminant < 0) {
			return null;
		}

		// Quadratic equation
		double firstPoint = (-b + Math.sqrt(discriminant)) / (2 * a);
		double secondPoint = (-b - Math.sqrt(discriminant)) / (2 * a);

		if (firstPoint < 0.0 && secondPoint < 0.0) {
			return null;
		}

		// X = S + t*D
		Point3D firstIntersectionPoint = rayStart.add(rayDirection
				.scalarMultiply(firstPoint));
		Point3D secondIntersectionPoint = rayStart.add(rayDirection
				.scalarMultiply(secondPoint));

		// Distance between eye and intersection points
		double firstIntersectionDistance = firstIntersectionPoint.sub(rayStart)
				.norm();
		double secondIntersectionDistance = secondIntersectionPoint.sub(
				rayStart).norm();

		// Get one with minimal distance
		Point3D intersectionPoint = firstIntersectionPoint;
		double intersectionPointDistance = firstIntersectionDistance;
		if (firstIntersectionDistance > secondIntersectionDistance) {
			intersectionPoint = secondIntersectionPoint;
			intersectionPointDistance = secondIntersectionDistance;
		}

		boolean outerIntersection = radius < intersectionPoint.sub(center).norm();

		RayIntersection intersection = new RayIntersection(intersectionPoint,
				intersectionPointDistance, outerIntersection) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getNormal() {
				return this.getPoint().sub(center).normalize();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrr() {
				return krr;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrn() {
				return krn;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrg() {
				return krg;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrb() {
				return krb;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdr() {
				return kdr;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdg() {
				return kdg;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdb() {
				return kdb;
			}
		};

		return intersection;
	}

}
