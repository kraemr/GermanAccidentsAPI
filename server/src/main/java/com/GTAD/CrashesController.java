package com.GTAD;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.GTAD.entities.AccidentData;
import com.GTAD.entities.AccidentStats;
import com.GTAD.entities.AllAccidentDefinitions;
import com.GTAD.entities.PrettyAccidentData;
import com.GTAD.helpers.AccidentDataMapper;
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
    public ResponseEntity<?> getAccidentById(
        @PathVariable Long id,
        @RequestParam(name = "pretty", required = false, defaultValue = "false") boolean pretty
    ) {
        return accidentService.getAccidentsById(id)
                .map(accidentData -> {
                    if (pretty) {
                        PrettyAccidentData prettyAccidentData = 
                            AccidentDataMapper.generatePrettyAccidentData(accidentData, definitionsService.getAccidentDefinitions().getDefinitions());
                        return ResponseEntity.ok(
                            prettyAccidentData
                        );
                    } else {
                        return ResponseEntity.ok(accidentData);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

@GetMapping("/year/{year}")
public ResponseEntity<?> getAccidentsByYear(
        @PathVariable Short year,
        @RequestParam(name = "pretty", required = false, defaultValue = "false") boolean pretty
) {
    List<AccidentData> accidents = accidentService.getAccidentsByYear(year);

    if (pretty) {
        List<PrettyAccidentData> prettyList = accidents.stream()
            .map(acc -> AccidentDataMapper.generatePrettyAccidentData(
                acc,
                definitionsService.getAccidentDefinitions().getDefinitions()
            ))
            .toList();
        return ResponseEntity.ok(prettyList);
    }

    return ResponseEntity.ok(accidents);
}

    @GetMapping("/page")
    public ResponseEntity<?> getAccidents(
        @RequestParam int page,
        @RequestParam String col,
        @RequestParam String cond,
        @RequestParam String val,
        @RequestParam(name = "pretty", required = false, defaultValue = "false") boolean pretty
    ) {
        int pageSize = 100;
        Pageable pageable = PageRequest.of(page - 1, pageSize); // page=1 means first page

        List<AccidentData> accidents = accidentService.findAccidents(col, cond, val, pageable).getContent();

        if (pretty) {
            List<PrettyAccidentData> prettyList = accidents.stream()
                .map(acc -> AccidentDataMapper.generatePrettyAccidentData(
                    acc,
                    definitionsService.getAccidentDefinitions().getDefinitions()
                ))
                .toList();
            return ResponseEntity.ok(prettyList);
        }
        return ResponseEntity.ok(accidents);
    }

    @GetMapping("/stats")
    public List<AccidentStats> getAccidents(
            @RequestParam String col,
            @RequestParam String cond,
            @RequestParam String val) {


//        return accidentService.findAccidents(col, cond, val, pageable).getContent();
                return null;
}
     

}