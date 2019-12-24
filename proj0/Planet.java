/**
 * Planet
 */
public class Planet extends Body {
    public Planet(Planet p) {
        super(p);
    }
    public Planet(double xP, double yP, double xV, double yV,
    double m, String img) {
        super(xP, yP, xV, yV, m, img);
    }
}