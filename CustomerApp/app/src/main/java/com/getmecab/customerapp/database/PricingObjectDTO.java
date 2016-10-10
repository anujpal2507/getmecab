package com.getmecab.customerapp.database;

import java.util.ArrayList;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 8/10/16.
 */
public class PricingObjectDTO {
    private ArrayList<Pricing> pricing;

    public ArrayList<Pricing> getPricing() {
        return pricing;
    }

    public void setPricing(ArrayList<Pricing> pricing) {
        this.pricing = pricing;
    }
}
