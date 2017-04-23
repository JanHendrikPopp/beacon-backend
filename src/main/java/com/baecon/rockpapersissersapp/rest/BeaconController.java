package com.baecon.rockpapersissersapp.rest;

import org.springframework.web.bind.annotation.*;

import static com.baecon.rockpapersissersapp.util.ApiConstants.VALID_BEACON;

@RestController
public class BeaconController {


    private static final String ALLOWED_BEACON = "f7826da6-4fa2-4e98-8024-bc5b71e0893e";

    @RequestMapping(value = VALID_BEACON, method = RequestMethod.GET)
    public Boolean checkBeacon(@PathVariable("beaconId") String beaconId) {
        return ALLOWED_BEACON.equals(beaconId);
    }

}
