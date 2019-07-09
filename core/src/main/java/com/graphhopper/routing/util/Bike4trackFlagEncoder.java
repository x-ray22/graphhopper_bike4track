package com.graphhopper.routing.util;

        import com.graphhopper.reader.ReaderWay;
        import com.graphhopper.util.PMap;

public class Bike4trackFlagEncoder extends BikeCommonFlagEncoder {
    public Bike4trackFlagEncoder() {
        this(4, 2, 0);
    }

    public Bike4trackFlagEncoder(String propertiesString) {
        this(new PMap(propertiesString));
    }

    public Bike4trackFlagEncoder(PMap properties) {
        this((int) properties.getLong("speed_bits", 4),
                properties.getLong("speed_factor", 2),
                properties.getBool("turn_costs", false) ? 1 : 0);
        this.properties = properties;
        this.setBlockFords(properties.getBool("block_fords", true));
    }

    public Bike4trackFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts) {
        super(speedBits, speedFactor, maxTurnCosts);
        addPushingSection("path");
        addPushingSection("footway");
        addPushingSection("pedestrian");
        addPushingSection("steps");
        addPushingSection("platform");

        addPushingSection("cycleway");

        avoidHighwayTags.add("trunk");
        avoidHighwayTags.add("trunk_link");
        avoidHighwayTags.add("primary");
        avoidHighwayTags.add("primary_link");
        avoidHighwayTags.add("secondary");
        avoidHighwayTags.add("secondary_link");

        avoidHighwayTags.add("lane");
        avoidHighwayTags.add("road");

        //preferHighwayTags.add("road");
        preferHighwayTags.add("cycleway");
        preferHighwayTags.add("track");
        //preferHighwayTags.add("service");
        //preferHighwayTags.add("tertiary");
        //preferHighwayTags.add("tertiary_link");
        //preferHighwayTags.add("residential");
        preferHighwayTags.add("unclassified");
        preferHighwayTags.remove("residential");

        absoluteBarriers.add("kissing_gate");
        setSpecificClassBicycle("touring");

        init();
    }

    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    boolean isPushingSection(ReaderWay way) {
        String highway = way.getTag("highway");
        String trackType = way.getTag("tracktype");
        return super.isPushingSection(way) || "track".equals(highway) && trackType != null && !"grade1".equals(trackType);
    }

    @Override
    public String toString() {
        return "bike4track";
    }
}