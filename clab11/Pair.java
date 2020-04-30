/**
 * Utility class. Useful for returning two objects. Immutable.
 */

public class Pair<T, S> {

    private T first;
    private S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return this.first;
    }

    public S second() {
        return this.second;
    }

    public void setSecond(S newSecond) {
        second = newSecond;
    }

    public void swapSecond(Pair<T, S> other) {
        S tS = this.second;
        this.setSecond(other.second());
        other.setSecond(tS);
    }

    public void setFirst(T newFirst) {
        first = newFirst;
    }

    public void swapFirst(Pair<T, S> other) {
        T tT = this.first;
        this.setFirst(other.first());
        other.setFirst(tT);
    }
}
