package com.test.rovers.rest;

import com.test.rovers.model.Point;
import com.test.rovers.model.Rover;
import com.test.rovers.sevices.MarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarsController {

    @Autowired
    private MarsService marsService;

    @PostMapping("/dimensions")
    public void setDimensions(@RequestParam("xy") String xy) {
        marsService.setDimensions(xy);
    }

    @PostMapping("/rover")
    public int deployRover(@RequestParam("position") String position) {
        return marsService.deployRover(position);
    }

    @GetMapping("/rovers")
    public List<Rover> getRovers() {
        return marsService.getRovers();
    }

    @GetMapping("/rover/{id}")
    public Rover getRover(@PathVariable int id) {
        return marsService.getRoverById(id);
    }

    @DeleteMapping("/rover/{id}")
    public void deleteRover(@PathVariable int id) {
        marsService.deleteRoverById(id);
    }

    @PostMapping("/execute")
    public Rover executeCommands(@RequestParam("commands") String commands) {
        return marsService.executeCommands(commands);
    }

    @PostMapping("/shortestPath")
    public String computeShortestPath(@RequestParam("roverId") int roverId,
                                           @RequestParam("x") int x,
                                           @RequestParam("y") int y) {
        return marsService.calculateShortestPath(roverId, x, y);
    }

}