package com.GTAD.helpers;

import java.util.Map;

import com.GTAD.entities.AccidentData;
import com.GTAD.entities.PrettyAccidentData;

public class AccidentDataMapper {

    public static PrettyAccidentData generatePrettyAccidentData(
            AccidentData accidentData,
            Map<String, Map<Integer, String>> definitions
    ) {
        PrettyAccidentData pretty = new PrettyAccidentData();

        // Direct string conversions
        pretty.setUident(String.valueOf(accidentData.getUident()));
        pretty.setYear(String.valueOf(accidentData.getYear()));
        pretty.setDay(String.valueOf(accidentData.getDay()));
        pretty.setHour(String.valueOf(accidentData.getHour()));
        pretty.setMonth(String.valueOf(accidentData.getMonth()));
        pretty.setCoordinateUtmX(String.valueOf(accidentData.getCoordinateUtmX()));
        pretty.setCoordinateUtmY(String.valueOf(accidentData.getCoordinateUtmY()));
        pretty.setLongitude(String.valueOf(accidentData.getLongitude()));
        pretty.setLatitude(String.valueOf(accidentData.getLatitude()));

        // Lookups using definitions map (with fallback to raw value if no mapping exists)
        pretty.setLand(resolve("land", accidentData.getLand(), definitions));
        pretty.setRegion(resolve("region", accidentData.getRegion(), definitions));
        pretty.setDistrict(resolve("district", accidentData.getDistrict(), definitions));
        pretty.setMunincipality(resolve("municipality", accidentData.getMunincipality(), definitions));
        pretty.setCategory(resolve("category", accidentData.getCategory(), definitions));
        pretty.setKind(resolve("kind", accidentData.getKind(), definitions));
        pretty.setType(resolve("type", accidentData.getType(), definitions));
        pretty.setLightCondition(resolve("light_condition", accidentData.getLightCondition(), definitions));
        pretty.setRoadSurfaceCondition(resolve("road_surface_condition", accidentData.getRoadSurfaceCondition(), definitions));

        // Booleans → Strings
        pretty.setBycicleInvolved(boolToString(accidentData.getBycicleInvolved()));
        pretty.setCarInvolved(boolToString(accidentData.getCarInvolved()));
        pretty.setPassengerInvolved(boolToString(accidentData.getPassengerInvolved()));
        pretty.setMotorcycleInvolved(boolToString(accidentData.getMotorcycleInvolved()));
        pretty.setDeliveryVanInvolved(boolToString(accidentData.getDeliveryVanInvolved()));
        pretty.setTruckBusOrTramInvolved(boolToString(accidentData.getTruckBusOrTramInvolved()));

        return pretty;
    }

    // Takes in a Number and resolves it to a String from our definitinos
    private static <T extends Number> String resolve(String field, T key, Map<String, Map<Integer, String>> definitions) {
        Map<Integer, String> fieldMap = definitions.get(field);
        Integer intKey = key.intValue();
        if (fieldMap != null && fieldMap.containsKey(intKey)) {
            return fieldMap.get(intKey);
        }
        return key.toString(); // fallback: return raw value
    }

    private static String boolToString(Boolean value) {
        if (value == null) return null;
        return value ? "Yes" : "No";
    }
}
