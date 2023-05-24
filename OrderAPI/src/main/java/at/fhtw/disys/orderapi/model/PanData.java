package at.fhtw.disys.orderapi.model;

import org.springframework.stereotype.Service;

@Service
public class PanData {
    private Pans cleanPans = new Pans();
    private Pans usedPans = new Pans();

    public PanData() {
        cleanPans.addPan(new Pan("Pan1", 30, true));
        cleanPans.addPan(new Pan("Pan2", 30, false));
        cleanPans.addPan(new Pan("Pan3", 20, true));
        cleanPans.addPan(new Pan("Pan4", 20, false));
        cleanPans.addPan(new Pan("Pan5", 40, true));
    }

    public int getCleanCount() {
        return cleanPans.getCount();
    }

    public Pan fetchPan() {
        Pan p = cleanPans.removeNextPan();
        usedPans.addPan(p);
        return p;
    }

    public void returnPan(Pan pan) {
        pan = usedPans.removePan(pan);
        cleanPans.addPan(pan);
    }
}
