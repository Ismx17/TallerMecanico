package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {

    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {}

    public static void mostraCabecera(String mensaje) {
        System.out.printf("%n%s%n" , mensaje);
        System.out.printf("-".repeat(mensaje.length()).concat("%n%n"));
    }

    public static void mostrarMenu(){
        String cabecera = "Gestión Del Taller Mecánico";
        mostraCabecera(cabecera);
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        Opcion opcion = null;
        do {
            try {
                opcion = Opcion.get(leerEntero("\nElige una opción: "));
            } catch (TallerMecanicoExcepcion e) {
                System.out.printf("ERROR: %s%n", e.getMessage());
            }
        } while (opcion == null);
        return opcion;
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    private static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    private static LocalDate leerFecha(String mensaje) {
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        mensaje = String.format("%s (%s): ", mensaje,CADENA_FORMATO_FECHA);
        try {
            fecha = LocalDate.parse(leerCadena(mensaje) , formatoFecha);
        }catch (DateTimeParseException e) {
            fecha = null;
        }
        return fecha;
    }

    public static Cliente leerCliente() {
        String nombre = leerCadena("Introduce el nombre: ");
        String dni = leerCadena("Introduce el dni: ");
        String telefono = leerCadena("Introduce el teléfono: ");
        return new Cliente(nombre,dni,telefono);
    }

    public static Cliente leerClienteDni() {
        return Cliente.get(leerCadena("Introduce el DNI: "));
    }

    public  static String leerNuevoNombre() {
        return leerCadena("Introduce el nuevo nombre: ");
    }

    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo telefono: " );
    }

    public static Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca: ");
        String modelo = leerCadena("Introduce el modelo: ");
        String matricula = leerCadena("Introduce la matricula: ");
        return new Vehiculo(marca,modelo,matricula);
    }

    public static Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(leerCadena("Introduce la matricula: "));
    }

    public static Revision leerRevision() {
        return new Revision(leerCliente() , leerVehiculo() , leerFecha("Introduce la fecha de inicio de la revisión: "));
    }

    public static int leerHoras() {
        return leerEntero("Introduce las horas a añadir: ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material: ");
    }

    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre de la revision: ");
    }

}