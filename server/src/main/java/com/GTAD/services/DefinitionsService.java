package com.GTAD.services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import com.GTAD.entities.AllAccidentDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefinitionsService {

    @PersistenceContext
    private EntityManager em;
    private AllAccidentDefinitions definitions;

    public AllAccidentDefinitions getAccidentDefinitions() {
        if (definitions == null) {
            this.definitions = initAllDefinitions();
        }
        return definitions;
    }

    private AllAccidentDefinitions initAllDefinitions() {
        @SuppressWarnings("unchecked")
        List<Object[]> raw = em.createNativeQuery("""
            SELECT 'land' AS def_type, CAST(land AS CHAR) AS def_key, land_str AS def_value
            FROM land_def
            UNION ALL
            SELECT 'municipality', CAST(munincipality AS CHAR), munincipality_str
            FROM munincipality_def
            UNION ALL
            SELECT 'kind', CAST(kind AS CHAR), kind_str
            FROM kind_def
            UNION ALL
            SELECT 'category', CAST(category AS CHAR), category_str
            FROM category_def
            UNION ALL
            SELECT 'type', CAST(type AS CHAR), type_str
            FROM type_def
            UNION ALL
            SELECT 'district', CAST(district AS CHAR), district_str
            FROM district_def
            UNION ALL
            SELECT 'light_condition', CAST(light_condition AS CHAR), light_condition_str
            FROM light_condition_def
            UNION ALL
            SELECT 'bicycle_involved', CAST(bycicle_involved AS CHAR), bycicle_involved_str
            FROM bycicle_involved_def
            UNION ALL
            SELECT 'car_involved', CAST(car_involved AS CHAR), car_involved_str
            FROM car_involved_def
            UNION ALL
            SELECT 'passenger_involved', CAST(passenger_involved AS CHAR), passenger_involved_str
            FROM passenger_involved_def
            UNION ALL
            SELECT 'motorcycle_involved', CAST(motorcycle_involved AS CHAR), motorcycle_involved_str
            FROM motorcycle_involved_def
            UNION ALL
            SELECT 'delivery_van_involved', CAST(delivery_van_involved AS CHAR), delivery_van_involved_str
            FROM delivery_van_involved_def
            UNION ALL
            SELECT 'truck_bus_or_tram_involved', CAST(truck_bus_or_tram_involved AS CHAR), truck_bus_or_tram_involved_str
            FROM truck_bus_or_tram_involved_def
            UNION ALL
            SELECT 'road_surface_condition', CAST(road_surface_condition AS CHAR), road_surface_condition_str
            FROM road_surface_condition_def
            UNION ALL
            SELECT 'day', CAST(day AS CHAR), day_str
            FROM day_def
            """)
            .getResultList();

            Map<String,Map<Integer,String>> defs = new HashMap<String,Map<Integer,String>>();            
            
            for (Object[] objects : raw) {    
                if(objects.length != 3) {
                    continue;
                }
                String definitionName = ((String)objects[0]); // category, land, type ...
                Integer definitionId = (Integer.parseInt((String)objects[1]));
                String definitionDescription = ((String)objects[2]);

                if (!defs.containsKey(definitionName)) {
                    Map<Integer,String> definition = new HashMap<Integer,String>();
                    definition.put(definitionId, definitionDescription);
                    defs.put(definitionName,definition);
                }else{
                    Map<Integer,String> definition = defs.get(definitionName);
                    definition.put(definitionId, definitionDescription);                    
                }
                 
            }

            return new AllAccidentDefinitions(defs);
    }
}
