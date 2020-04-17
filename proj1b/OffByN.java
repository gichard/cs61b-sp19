public class OffByN implements CharacterComparator {
    int dist;

    public OffByN(int N) {
        dist = N;
    }
    @Override
    public boolean equalChars(char x, char y){
        int diff = x - y;
        return (diff == dist) || (diff == -dist);
    }
}
