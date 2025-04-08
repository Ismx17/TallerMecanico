package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;

public class Mecanico extends Trabajo{
    private static final float FACTOR_HORA = 30F;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5F;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente, vehiculo, fechaInicio);
        precioMaterial = 0;
    }

    public Mecanico(Mecanico mecanico){
        super(mecanico);
        precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if(precioMaterial <= 0){
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }

        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecioEspecifico(){
        return (estaCerrado()) ? getHoras() * FACTOR_HORA + FACTOR_PRECIO_MATERIAL * getPrecioMaterial() : 0;
    }

    @Override
    public String toString() {
        String resultado = String.format("Mecánico -> %s - %s (%s - %s): %d horas, %.2f € en material",
                getCliente(), getVehiculo(), getFechaInicio(), getFechaFin() != null ? getFechaFin() : "", getHoras(), getPrecioMaterial());
        if (estaCerrado()) {
            resultado += String.format(", %.2f € total", getPrecio());
        }
        return resultado;
    }
}
