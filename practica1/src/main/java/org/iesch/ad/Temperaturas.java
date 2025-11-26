package org.iesch.ad;

public class Temperaturas {

    private String fecha;
    private String indicativo;
    private String nombre;
    private String provincia;
    private String altitud;

    private String tmed;
    private String prec;

    private String tmin;
    private String horatmin;

    private String tmax;
    private String horatmax;

    // Getters simples
    public String getFecha() { return fecha; }
    public String getIndicativo() { return indicativo; }
    public String getNombre() { return nombre; }
    public String getProvincia() { return provincia; }
    public String getAltitud() { return altitud; }

    public String getTmedStr() { return tmed; }
    public String getPrecStr() { return prec; }

    public double getTmin() {
        if (tmin == null || tmin.trim().isEmpty()) return Double.NaN;
        try {
            return Double.parseDouble(tmin.replace(',', '.'));
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public String getHoraTmin() { return horatmin; }

    public double getTmax() {
        if (tmax == null || tmax.trim().isEmpty()) return Double.NaN;
        try {
            return Double.parseDouble(tmax.replace(',', '.'));
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public String getHoraTmax() { return horatmax; }

    public String getEstacion() {
        String id = indicativo == null ? "" : indicativo;
        String n = nombre == null ? "" : nombre;
        return (n + " " + id).trim();
    }

    @Override
    public String toString() {
        return String.format("%s %s tmin=%s horatmin=%s tmax=%s horatmax=%s",
                fecha, getEstacion(), tmin, horatmin, tmax, horatmax);
    }
}