/**
 * Planet
 */
public class Planet {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV,
		double m, String img) {
		xxPos = xP;
		xxVel = xV;
		yyPos = yP;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet b) {
		xxPos = b.xxPos;
		xxVel = b.xxVel;
		yyPos = b.yyPos;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Planet oPlanet) {
		double dx = this.xxPos - oPlanet.xxPos;
		double dy = this.yyPos - oPlanet.yyPos;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public double calcForceExertedBy(Planet oPlanet) {
		double dist = calcDistance(oPlanet);
		return G * this.mass * oPlanet.mass / (dist * dist);
	}

	public double calcForceExertedByX(Planet oPlanet) {
		double dist = calcDistance(oPlanet);
		double totalForce = calcForceExertedBy(oPlanet);
		double dX = oPlanet.xxPos - xxPos;
		return totalForce * dX / dist;
	}

	public double calcForceExertedByY(Planet oPlanet) {
		double dist = calcDistance(oPlanet);
		double totalForce = calcForceExertedBy(oPlanet);
		double dY = oPlanet.yyPos - yyPos;
		return totalForce * dY / dist;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double netX = 0.0;
		for (Planet oPlanet : allPlanets) {
			if (!this.equals(oPlanet))
				netX += calcForceExertedByX(oPlanet);
		}

		return netX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double netY = 0.0;
		for (Planet oPlanet : allPlanets) {
			if (!this.equals(oPlanet))
				netY += calcForceExertedByY(oPlanet);
		}

		return netY;
	}

	public void update(double dT, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;
		// update velocity
		this.xxVel += aX * dT;
		this.yyVel += aY * dT;
		// update position
		this.xxPos += this.xxVel * dT;
		this.yyPos += this.yyVel * dT;
	}

	public void draw() {
		// StdDraw.enableDoubleBuffering();
		StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
		StdDraw.show();
	}
}