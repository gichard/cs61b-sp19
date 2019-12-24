public class Body {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double G = 6.67e-11;

	public Body(double xP, double yP, double xV, double yV,
		double m, String img) {
		xxPos = xP;
		xxVel = xV;
		yyPos = yP;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		xxVel = b.xxVel;
		yyPos = b.yyPos;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body oBody) {
		double dx = this.xxPos - oBody.xxPos;
		double dy = this.yyPos - oBody.yyPos;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public double calcForceExertedBy(Body oBody) {
		double dist = calcDistance(oBody);
		return G * this.mass * oBody.mass / (dist * dist);
	}

	public double calcForceExertedByX(Body oBody) {
		double dist = calcDistance(oBody);
		double totalForce = calcForceExertedBy(oBody);
		double dX = oBody.xxPos - xxPos;
		return totalForce * dX / dist;
	}

	public double calcForceExertedByY(Body oBody) {
		double dist = calcDistance(oBody);
		double totalForce = calcForceExertedBy(oBody);
		double dY = oBody.yyPos - yyPos;
		return totalForce * dY / dist;
	}

	public double calcNetForceExertedByX(Body[] allBodys) {
		double netX = 0.0;
		for (Body oBody : allBodys) {
			if (!this.equals(oBody))
				netX += calcForceExertedByX(oBody);
		}

		return netX;
	}

	public double calcNetForceExertedByY(Body[] allBodys) {
		double netY = 0.0;
		for (Body oBody : allBodys) {
			if (!this.equals(oBody))
				netY += calcForceExertedByY(oBody);
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