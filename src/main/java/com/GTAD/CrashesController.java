package com.GTAD;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.GTAD.entities.AccidentData;
import com.GTAD.entities.AllAccidentDefinitions;
import com.GTAD.services.AccidentService;
import com.GTAD.services.DefinitionsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accidents")
public class CrashesController {
    private final AccidentService accidentService;
    private final DefinitionsService definitionsService;
    
    public CrashesController(AccidentService accidentService, DefinitionsService definitionsService) {
        this.accidentService = accidentService;
        this.definitionsService = definitionsService;        
    }        

    @GetMapping("/definitions")
    public AllAccidentDefinitions getDefinitions(){
        return definitionsService.getAccidentDefinitions();
    }

    @GetMapping("/id/{id}")
    public Optional<AccidentData> getAccidentById(@PathVariable Long id) {
        return accidentService.getAccidentsById(id);
    }

    @GetMapping("/year/{year}")
    public List<AccidentData> getAccidentsByYear(@PathVariable Short year) {
        return accidentService.getAccidentsByYear(year);
    }

    @GetMapping("/page")
    public List<AccidentData> getAccidents(
            @RequestParam int page,
            @RequestParam String col,
            @RequestParam String cond,
            @RequestParam String val) {

        int pageSize = 100;
        Pageable pageable = PageRequest.of(page - 1, pageSize); // page=1 means first page

        return accidentService.findAccidents(col, cond, val, pageable).getContent();
    }

     

}