package org.sjlchatham.sjlcweb.helpers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MilitaryStandardTimes {

    /* HashMap<String, String> hours
     * ================
     * Contains military time hours and their equivalent in standard time as key/value pairs.
     * k = hour in military time; v = hour in standard time
     * e.g k=13; v=01
     * AM and PM are not added to make these values easier to parse; this will need to be done manually.
     */
    private static final Map<String, String> HOURS = initMap();

    // Init method
    private static Map<String, String> initMap() {
        Map<String, String> map = new LinkedHashMap<>();

        // AM hours
        map.put("00", "12"); // 00:00 = 12:00 AM (midnight)
        map.put("01", "01"); // 01:00 = 01:00 AM
        map.put("02", "02"); // 02:00 = 02:00 AM
        map.put("03", "03"); // 03:00 = 03:00 AM
        map.put("04", "04"); // 04:00 = 04:00 AM
        map.put("05", "05"); // 05:00 = 05:00 AM
        map.put("06", "06"); // 06:00 = 06:00 AM
        map.put("07", "07"); // 07:00 = 07:00 AM
        map.put("08", "08"); // 08:00 = 08:00 AM
        map.put("09", "09"); // 09:00 = 09:00 AM
        map.put("10", "10"); // 10:00 = 10:00 AM
        map.put("11", "11"); // 11:00 = 11:00 AM

        // PM hours
        map.put("12", "12"); // 12:00 = 12:00 PM (noon)
        map.put("13", "01"); // 13:00 = 01:00 PM
        map.put("14", "02"); // 14:00 = 02:00 PM
        map.put("15", "03"); // 15:00 = 03:00 PM
        map.put("16", "04"); // 16:00 = 04:00 PM
        map.put("17", "05"); // 17:00 = 05:00 PM
        map.put("18", "06"); // 18:00 = 06:00 PM
        map.put("19", "07"); // 19:00 = 07:00 PM
        map.put("20", "08"); // 20:00 = 08:00 PM
        map.put("21", "09"); // 21:00 = 09:00 PM
        map.put("22", "10"); // 22:00 = 10:00 PM
        map.put("23", "11"); // 23:00 = 11:00 PM
        map.put("24", "12"); // 24:00 = 12:00 AM (midnight)

        // Make map unmodifiable
        return Collections.unmodifiableMap(map);
    }

    // Getter methods
    public static Map<String, String> getHours() {
        return HOURS;
    }

    // toString method
    public void printMap() {
        for (Map.Entry<String, String> entry : HOURS.entrySet())
            System.out.println(
                    "Military: " + entry.getKey() + ", " + "Standard: " + entry.getValue()
            );
    }
}
