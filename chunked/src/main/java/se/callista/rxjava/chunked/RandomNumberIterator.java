package se.callista.rxjava;

import java.util.Iterator;
import java.util.Random;

public class RandomNumberIterator implements Iterator<Long> {

    Random random = new Random(1);
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Long next() {
        return random.nextLong();
    }
}
