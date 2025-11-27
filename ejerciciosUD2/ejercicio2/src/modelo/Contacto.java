package modelo;

public class Contacto {
    String nombre;
    Integer telefono;
    String correo;

    public Contacto(String nombre, Integer telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Contacto() {
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", correo='" + correo + '\'' +
                '}';
    }
}
