/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.actores;

import co.edu.autonoma.redes.RedCliente;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikof
 */
public class Cliente {
    
    private RedCliente red;
    
    public Cliente(String ipString) throws UnknownHostException, SocketException{
        
        InetAddress destino = InetAddress.getByName(ipString);
        this.red = new RedCliente(destino, 4400);
    }
    
    public String agregarTerminoADiccionario(String palabra, String definicion){
        String mensaje = "agregar::"+palabra+"::"+definicion;
        
        String respuesta;
        
        try {
            respuesta = new String(this.red.trabajar(mensaje.getBytes()));
        } catch (IOException ex) {
            return "Error agregando el nuevo termino, problemas de conexion";
        }
        
        System.out.println("Respuesta servidor 'AGREGAR'>> " + respuesta);
        
        if(respuesta.trim().equals("200"))
            return "Termino agregado con exito";
        else{
            return "El termino no pudo ser agregado, es posible que ya se encuentre en el diccionario";
        }
    }
    
    public String consultarTerminoDiccionario(String palabra){
        String mensaje = "consultar::"+palabra;
        
        String respuesta;
        
        String[] respuestas;
        
        try {
            respuesta = new String(this.red.trabajar(mensaje.getBytes()));
        } catch (IOException ex) {
            return "Error en la consulta, problemas de conexion";
        }
        
        System.out.println("Respuesta servidor 'CONSULTAR'>> " + respuesta);
        
        respuestas = respuesta.split("::");
        
        if(respuestas[0].equals("200"))
            return respuestas[1];
        else{
            return "El termino no ha sido agregado en el diccionario";
        }
    }
    
    public String editarTerminoDiccionario(String palabra, String definicion){
        String mensaje = "editar::"+palabra+"::"+definicion;
        
        String respuesta;
        
        try {
            respuesta = new String(this.red.trabajar(mensaje.getBytes()));
        } catch (IOException ex) {
            return "Error editando el termino, problemas de conexion";
        }
        
        System.out.println("Respuesta servidor 'EDITAR'>> " + respuesta);
        
        if(respuesta.trim().equals("200"))
            return "Termino editado con exito";
        else{
            return "El termino no pudo ser editado";
        }
    }
    
    public String eliminarTerminoDiccionario(String palabra){
        String mensaje = "eliminar::"+palabra;
        
        String respuesta;
        
        try {
            respuesta = new String(this.red.trabajar(mensaje.getBytes()));
        } catch (IOException ex) {
            return "Error eliminando el termino, problemas de conexion";
        }
        
        System.out.println("Respuesta servidor 'ELIMINAR'>> " + respuesta);
        
        if(respuesta.trim().equals("200"))
            return "Termino eliminado con exito";
        else{
            return "El termino no pudo ser eliminado";
        }
    }
    
    public String listarTerminosDiccionario(){
        String mensaje = "listar";
        
        String respuesta;
        String[] respuestas;
        
        try {
            respuesta = new String(this.red.trabajar(mensaje.getBytes()));
        } catch (IOException ex) {
            return "Error obteniendo lista de terminos, problemas de conexion";
        }
        
        System.out.println("Respuesta servidor 'LISTAR'>> " + respuesta);
        
        respuestas = respuesta.split("::");
        
        if(respuestas[0].equals("200")){
            String terminosDisponibles = "Los terminos disponibles son: \n";
        
            for (int i = 1; i < respuestas.length; i++) {
                terminosDisponibles += respuestas[i]+"\n";
            }
            return terminosDisponibles;
        }  
        else{
            return "El diccionario no tiene terminos";
        }
    }
}
