package com.baecon.rockpapersissersapp.rest;

import org.springframework.web.bind.annotation.*;

import static com.baecon.rockpapersissersapp.util.ApiConstants.VALID_BEACON;

@RestController
public class BeaconController {


    private static final String ALLOWED_BEACON = "4LKv";

    @RequestMapping(value = VALID_BEACON, method = RequestMethod.GET)
    public Boolean checkBeacon(@PathVariable("beaconId") String beaconId) {
        return ALLOWED_BEACON.equals(beaconId);
    }

}
