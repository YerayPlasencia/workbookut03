package es.cip.yerayplasenciaramos.servidor;

import es.cip.yerayplasenciaramos.cliente.ConexionCliente;
import es.cip.yerayplasenciaramos.utilidades.MensajesChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor
 * 
 */
 
public class ServidorChat {

    public static void main(String[] args) {
       
        int puerto = 1234;
        int maximoConexiones = 10;
        ServerSocket servidor = null; 
        Socket socket = null;
        MensajesChat mensajes = new MensajesChat();
        
        try {
            servidor = new ServerSocket(puerto, maximoConexiones);
            System.out.println("CENTRAL DE INCIDENCIAS");
            System.out.println("Servidor a la espera de conexiones..");
            while (true) {
                socket = servidor.accept();
                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
                ConexionCliente cc = new ConexionCliente(socket, mensajes);
                //System.out.println("Cliente: " + cc.getName() + " Incidencia: ");
                cc.start();
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el servidor: " + ex.getMessage());
            }
        }
    }
}