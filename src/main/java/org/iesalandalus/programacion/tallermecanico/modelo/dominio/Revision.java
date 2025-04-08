package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 35F;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision){
        super(revision);
    }

    public float getPrecioEspecifico() {
        return (estaCerrado()) ? getHoras() * FACTOR_HORA : 0;
    }

    @Override
    public String toString() {
        String resultado = String.format("Revisión -> %s - %s (%s - %s): %d horas",
                getCliente(), getVehiculo(), getFechaInicio(), getFechaFin() != null ? getFechaFin() : "", getHoras());
        if (estaCerrado()) {
            resultado += String.format(", %.2f € total", getPrecio());
        }
        return resultado;
    }
}