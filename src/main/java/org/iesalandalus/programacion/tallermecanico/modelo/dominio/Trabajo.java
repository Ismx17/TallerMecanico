package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_DATE;
    private static final float FACTOR_DIA = 10;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private Cliente cliente;
    private Vehiculo vehiculo;

    protected Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        fechaFin = null;
        horas = 0;
    }

    protected Trabajo(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        cliente = trabajo.cliente;
        vehiculo = trabajo.vehiculo;
        fechaInicio = trabajo.fechaInicio;
        fechaFin = trabajo.fechaFin;
        horas = trabajo.horas;
    }

    public Trabajo copiar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        Trabajo trabajoCopia = null;
        if (trabajo instanceof Revision revision) {
            trabajoCopia = new Revision(revision);
        } else if (trabajo instanceof Mecanico mecanico) {
            trabajoCopia = new Mecanico(mecanico);
        }
        return trabajoCopia;
    }

    public Trabajo get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo.");
        return new Revision(Cliente.get("11111111H"), vehiculo, LocalDate.now());
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void añadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (fechaFin != null) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo esta cerrado.");
        } else if (horas <= 0) {
            throw new TallerMecanicoExcepcion("Las horas a añadir deben ser mayores que cero.");
        }
        this.horas =+ horas;
    }

    public boolean estaCerrado() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin)throws TallerMecanicoExcepcion {
        Objects.requireNonNull(fechaFin, "La fecha fin no puede ser nula.");
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("El trabajo ya esta cerrado.");
        }
        setFechaFin(fechaFin);
    }

    public float getPrecio() {
        return getPrecioFijo() + getPrecioEspecifico();
    }

    private float getPrecioFijo() {
        return estaCerrado() ? FACTOR_DIA * getDias() : 0;
    }

    private float getDias() {
        return estaCerrado() ? (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin) : 0;
    }

    public abstract float getPrecioEspecifico();

    @Override
    public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Trabajo trabajo)) return false;
            return Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(cliente, trabajo.cliente) && Objects.equals(vehiculo, trabajo.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, cliente, vehiculo);
    }
}
