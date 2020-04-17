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
            String message = "";
            for (int j = 0; j < actionSeq.length; j++) {
                switch (actionSeq[j]) {
                    case 0:
                        sad.addFirst(j);
                        exp.addFirst(j);
                        message += "addFirst(" + j + ")\n";
                        break;
                    case 1:
                        sad.addLast(j);
                        exp.addLast(j);
                        message += "addLast(" + j +")\n";
                        break;
                    case 2:
                        sad.removeFirst();
                        exp.removeFirst();
                        message += "removeFirst()\n";
                        break;
                    case 3:
                        sad.removeLast();
                        exp.removeLast();
                        message += "removeLast()\n";
                        break;
                }
//                assertEquals(message + "size()\n", exp.size(), sad.size());
                for (int k = 0; k < exp.size(); k++) {
                    assertEquals(message, exp.get(k), sad.get(k));
                }
            }
            i++;
        }
    }
}
