import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    /** generate random action sequence, addFirst = 0， addLast = 1，
     * rmFirst = 2， rmLast = 3. */
    public Integer[] randomActionSeq(int seqLen) {
        Integer size = 0;
        Integer[] result = new Integer[seqLen];
        for (int i = 0; i < seqLen; i++) {
            if (size == 0) {
                result[i] = StdRandom.uniform(0, 2);
                size++;
            } else {
                result[i] = StdRandom.uniform(0, 4);
                size--;
            }
        }

        return result;
    }

    @Test
    public void autoTestDeque() {
        Integer i = 0;
        while (true) {
            Integer[] actionSeq = randomActionSeq(i);
            StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
            ArrayDequeSolution<Integer> exp = new ArrayDequeSolution<>();

            // build up array deque
            for (int j = 0; j < actionSeq.length; j++) {
                switch (actionSeq[j]) {
                    case 0:
                        sad.addFirst(j);
                        exp.addFirst(j);
                        break;
                    case 1:
                        sad.addLast(j);
                        exp.addLast(j);
                        break;
                    case 2:
                        sad.removeFirst();
                        exp.removeFirst();
                        break;
                    case 3:
                        sad.removeLast();
                        exp.removeLast();
                        break;
                }
            }

            String message = "";
            assertEquals("size mismatch: expected " + exp.size() + ", but got " + sad.size()
                    , exp.size(), sad.size());
            for (int j = 0; j < exp.size(); j++) {
                switch (actionSeq[j]) {
                    case 0:
                        message += "addFirst(" + exp.get(j) + ")\n";
                    case 1:
                        message += "addLast(" + exp.get(j) +")\n";
                    case 2:
                        message += "removeFirst()\n";
                    case 3:
                        message += "removeLast()\n";
                }
                assertEquals(message, exp.get(j), sad.get(j));
            }

            i++;
        }
    }
}
