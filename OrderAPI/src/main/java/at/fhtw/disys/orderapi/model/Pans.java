package at.fhtw.disys.orderapi.model;

import java.util.LinkedList;
import java.util.List;

public class Pans {
    private final List<Pan> pans = new LinkedList<>();

    public int getCount() {
        return pans.size();
    }

    public Pan removeNextPan() {
        return pans.remove(0);
    }

    public Pan removePan(Pan pan) {
        return pans.stream().filter( p -> p.getId()==pan.getId()).findFirst().orElseThrow();
    }

    public void addPan(Pan pan) {
        pans.add(pan);
    }

}
