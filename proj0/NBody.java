// public class NBody{
	
// 	public static double readRadius(String fileName) {
// 		In in = new In(fileName);
// 		int numBodys = in.readInt();
// 		double radius = in.readDouble();
// 		return radius;
// 	}

// 	public static Body[] readBodies(String fileName) {
// 		In in = new In(fileName);
// 		int numBodys = in.readInt();
// 		double radius = in.readDouble();

// 		Body[] resBodys = new Body[numBodys];

// 		for (int i = 0; i < numBodys; i++) {
// 			resBodys[i] = new Body(in.readDouble(), in.readDouble(), 
// 				in.readDouble(), in.readDouble(),
// 				 in.readDouble(), in.readString());
// 		}

// 		return resBodys;
// 	}

// 	public static void main(String[] args) {
// 		if (args.length < 3)
// 			System.out.println("Please specify arguments T and dT and fileName");

// 		double T = Double.valueOf(args[0]);
// 		double dT = Double.valueOf(args[1]);
// 		String fileName = args[2];

// 		double univRadius = readRadius(fileName);
// 		Body[] allBodys = readBodies(fileName);

// 		//draw background
// 		String backgroundImg = "./images/starfield.jpg";
// 		StdDraw.enableDoubleBuffering();
// 		StdDraw.setScale(-1.0*univRadius, univRadius);
// 		StdDraw.clear();

// 		StdDraw.picture(0, 0, backgroundImg);

// 		StdDraw.show();

// 		//draw all bodys
// 		for (Body body : allBodys) {
// 			body.draw();
// 		}

// 		//create animation

// 		int numBodys = allBodys.length;
// 		double[] xForces = new double[numBodys];
// 		double[] yForces = new double[numBodys];
// 		double time;

// 		for (time = 0.0; time <= T; time += dT) {
// 			for (int i = 0; i < numBodys; i++) {
// 				xForces[i] = allBodys[i].calcNetForceExertedByX(allBodys);
// 				yForces[i] = allBodys[i].calcNetForceExertedByY(allBodys);
// 			}
// 			for (int i = 0; i < numBodys; i++) {
// 				allBodys[i].update(dT, xForces[i], yForces[i]);
// 			}

// 			StdDraw.picture(0, 0, backgroundImg);
// 			for (Body body : allBodys) {
// 				body.draw();
// 			}
// 			StdDraw.show();
// 			StdDraw.pause(10);
// 		}

// 		//print out information
// 		StdOut.printf("%d\n", allBodys.length);
// 		StdOut.printf("%.2e\n", univRadius);
// 		for (int i = 0; i < allBodys.length; i++) {
// 			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
// 			allBodys[i].xxPos, allBodys[i].yyPos, allBodys[i].xxVel,
// 			allBodys[i].yyVel, allBodys[i].mass, allBodys[i].imgFileName);   
// 		}
// 	}
// }

//  adapt Planet.java
public class NBody{
	
	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int numPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int numPlanets = in.readInt();
		double radius = in.readDouble();

		Planet[] resPlanets = new Planet[numPlanets];

		for (int i = 0; i < numPlanets; i++) {
			resPlanets[i] = new Planet(in.readDouble(), in.readDouble(), 
				in.readDouble(), in.readDouble(),
				 in.readDouble(), in.readString());
		}

		return resPlanets;
	}

	public static void main(String[] args) {
		if (args.length < 3)
			System.out.println("Please specify arguments T and dT and fileName");

		double T = Double.valueOf(args[0]);
		double dT = Double.valueOf(args[1]);
		String fileName = args[2];

		double univRadius = readRadius(fileName);
		Planet[] allPlanets = readPlanets(fileName);

		//draw background
		String backgroundImg = "./images/starfield.jpg";
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-1.0*univRadius, univRadius);
		StdDraw.clear();

		StdDraw.picture(0, 0, backgroundImg);

		StdDraw.show();

		//draw all Planets
		for (Planet Planet : allPlanets) {
			Planet.draw();
		}

		//create animation

		int numPlanets = allPlanets.length;
		double[] xForces = new double[numPlanets];
		double[] yForces = new double[numPlanets];
		double time;

		for (time = 0.0; time <= T; time += dT) {
			for (int i = 0; i < numPlanets; i++) {
				xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
				yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
			}
			for (int i = 0; i < numPlanets; i++) {
				allPlanets[i].update(dT, xForces[i], yForces[i]);
			}

			StdDraw.picture(0, 0, backgroundImg);
			for (Planet Planet : allPlanets) {
				Planet.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}

		//print out information
		StdOut.printf("%d\n", allPlanets.length);
		StdOut.printf("%.2e\n", univRadius);
		for (int i = 0; i < allPlanets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
			allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
			allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);   
		}
	}
}