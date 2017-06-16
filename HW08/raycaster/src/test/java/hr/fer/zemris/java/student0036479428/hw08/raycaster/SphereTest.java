package hr.fer.zemris.java.student0036479428.hw08.raycaster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

import org.junit.Test;

public class SphereTest {

	private static final double DELTA = 1e-5;
	private Ray ray;
	private Sphere sphere;
	private RayIntersection intersection;
	
	@Test
	public void interestionElements() {
		ray = Ray.fromPoints(new Point3D(), new Point3D(5, 5, 5));
		sphere = new Sphere(new Point3D(3.5, 3.5, 3.5), 2, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		intersection = sphere.findClosestRayIntersection(ray);
		assertEquals(1.0, intersection.getKdr(), DELTA);
		assertEquals(1.0, intersection.getKdg(), DELTA);
		assertEquals(1.0, intersection.getKdb(), DELTA);
		assertEquals(0.5, intersection.getKrr(), DELTA);
		assertEquals(0.5, intersection.getKrg(), DELTA);
		assertEquals(0.5, intersection.getKrb(), DELTA);
		assertEquals(10.0, intersection.getKrn(), DELTA);
		assertEquals(intersection.getPoint().x, 2.34529946162075, DELTA);
		assertEquals(intersection.getPoint().y, 2.34529946162075, DELTA);
		assertEquals(intersection.getPoint().z, 2.34529946162075, DELTA);
		assertEquals(-0.5773502691896257, intersection.getNormal().x, DELTA);
		assertEquals(-0.5773502691896257, intersection.getNormal().y, DELTA);
		assertEquals(-0.5773502691896257, intersection.getNormal().z, DELTA);
		
	}

	@Test
	public void ffindClosestRayIntersection_ValidResult_Test() {
		ray = Ray.fromPoints(new Point3D(), new Point3D(1, 0, 0));
		sphere = new Sphere(new Point3D(0, 1, 0), 1, 1, 1, 1, 0.5, 0.5,
				0.5, 10);
		intersection = sphere.findClosestRayIntersection(ray);
		assertEquals(1.0, intersection.getKdr(), DELTA);
		assertEquals(1.0, intersection.getKdg(), DELTA);
		assertEquals(1.0, intersection.getKdb(), DELTA);
		assertEquals(0.5, intersection.getKrr(), DELTA);
		assertEquals(0.5, intersection.getKrg(), DELTA);
		assertEquals(0.5, intersection.getKrb(), DELTA);
		assertEquals(10.0, intersection.getKrn(), DELTA);
		assertEquals(0.0, intersection.getPoint().x, DELTA);
		assertEquals(0.0, intersection.getPoint().y, DELTA);
		assertEquals(0.0, intersection.getPoint().z, DELTA);
	}

	@Test
	public void findClosestRayIntersection_NoIntersection_Test() {
		ray = Ray.fromPoints(new Point3D(), new Point3D(5, 5, 5));
		sphere = new Sphere(new Point3D(10, 3.5, 3.5), 1, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		intersection = sphere.findClosestRayIntersection(ray);
		assertTrue(intersection == null);
	}
	
	@Test
	public void findClosestRayIntersection_NoIntersection_Test2() {
		ray = Ray.fromPoints(new Point3D(), new Point3D(-5, -5, -5));
		sphere = new Sphere(new Point3D(3.5, 3.5, 3.5), 4, 1, 1, 1, 0.5,
				0.5, 0.5, 10);
		intersection = sphere.findClosestRayIntersection(ray);
		assertTrue(intersection == null);
	}
}
