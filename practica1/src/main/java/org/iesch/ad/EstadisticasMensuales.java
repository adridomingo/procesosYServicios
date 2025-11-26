package org.iesch.ad;

import org.iesch.ad.Temperaturas;

public class EstadisticasMensuales {
    private double maxTemp = Double.NEGATIVE_INFINITY;
    private String maxDate = null;
    private String maxHour = null;
    private String maxStation = null;

    private double minTemp = Double.POSITIVE_INFINITY;
    private String minDate = null;
    private String minHour = null;
    private String minStation = null;

    public synchronized void updateWithRecord(Temperaturas r) {
        double tmax = r.getTmax();
        if (tmax > maxTemp) {
            maxTemp = tmax;
            maxDate = r.getFecha();
            maxHour = r.getHoraTmax();
            maxStation = r.getEstacion();
        }

        double tmin = r.getTmin();
        if (tmin < minTemp) {
            minTemp = tmin;
            minDate = r.getFecha();
            minHour = r.getHoraTmin();
            minStation = r.getEstacion();
        }
    }

    public synchronized double getMaxTemp() { return maxTemp; }
    public synchronized String getMaxDate() { return maxDate; }
    public synchronized String getMaxHour() { return maxHour; }
    public synchronized String getMaxStation() { return maxStation; }

    public synchronized double getMinTemp() { return minTemp; }
    public synchronized String getMinDate() { return minDate; }
    public synchronized String getMinHour() { return minHour; }
    public synchronized String getMinStation() { return minStation; }
}