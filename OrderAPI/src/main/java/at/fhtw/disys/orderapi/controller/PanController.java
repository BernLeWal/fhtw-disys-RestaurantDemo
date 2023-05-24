package at.fhtw.disys.orderapi.controller;

import at.fhtw.disys.orderapi.model.Pan;
import at.fhtw.disys.orderapi.model.PanData;
import at.fhtw.disys.orderapi.model.Pans;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pans")
@Slf4j
public class PanController {

    private static Logger logger = LoggerFactory.getLogger(PanController.class);

    @Autowired
    private PanData pans;

    public PanController() {
    }

    @GetMapping("/clean/count")
    public int cleanPanCount() {
        logger.info("get cleanPanCount received");
        return pans.getCleanCount();
    }

    @GetMapping("/clean")
    public Pan fetchPan() {
        Pan pan = pans.fetchPan();
        logger.info("fetchPan received, returned " + pan);
        return pan;
    }

    @PostMapping("/clean")
    public void returnPan(@RequestBody Pan pan) {
        logger.info("returnPan " + pan + " received");
        pans.returnPan(pan);
    }
}
