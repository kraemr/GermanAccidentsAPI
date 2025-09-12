package com.GTAD.entities;
import java.util.Map;
public class AllAccidentDefinitions {


    private Map<String,Map<Integer,String>> definitions;


    public AllAccidentDefinitions(Map<String,Map<Integer,String>> definitions){
        this.definitions = definitions;
    }

    public Map<String, Map<Integer, String>> getDefinitions() {        
        return definitions;
    }



}