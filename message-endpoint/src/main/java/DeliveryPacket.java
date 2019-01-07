/*
POJO class used by GSON to send JSON objects to the frontend
 */

import java.util.HashMap;

class DeliveryPacket {

    HashMap<String, String> originCountryGradients;
    HashMap<String, Integer> originCountryPercents;

    DeliveryPacket(){
        originCountryGradients = new HashMap<>();
        originCountryPercents = new HashMap<>();
    }


}
