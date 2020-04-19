import edu.princeton.cs.algs4.StdAudio;
/*import es.datastructur.synthesizer.GuitarString;*/
import synthesizer.GuitarString;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static int key2GS(char k) {
        for (int i = 0; i < 37; i++) {
            if (k == KEYBOARD.charAt(i)) {
                return i;
            }
        }
        return 37;
    }

    public static void main(String[] args) {
        /*GuitarString[] keys = (GuitarString []) new Object[37];*/
        Object[] keys = new Object[37];
        for (int i = 0; i < 37; i++) {
            keys[i] = new GuitarString(CONCERT_A * Math.pow(2, (i - 24) / 12.0));
        }

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char k = StdDraw.nextKeyTyped();
                int concert = key2GS(k);
                if (concert != 37) {
                    ((GuitarString) keys[concert]).pluck();
                }
            }

            double sample = 0.0;
            for (int i = 0; i < 37; i++) {
                sample += ((GuitarString) keys[i]).sample();
            }

            StdAudio.play(sample);

            for (int i = 0; i < 37; i++) {
                ((GuitarString) keys[i]).tic();
            }
        }
    }

}
