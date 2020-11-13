package es.cip.yerayplasenciaramos.cliente;

import es.cip.yerayplasenciaramos.utilidades.MensajesChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Esta clase gestiona el envio de datos entre el servidor y el cliente al que atiende.
 * 
 */

public class ConexionCliente extends Thread implements Observer{
    
    private Socket socket; 
    private MensajesChat mensajes;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;
    
    public ConexionCliente (Socket socket, MensajesChat mensajes){
        this.socket = socket;
        this.mensajes = mensajes;
        
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
        	System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
        }
    }
    
    @Override
    public void run(){
        String mensajeRecibido;
        boolean conectado = true;
        mensajes.addObserver(this);
        while (conectado) {
            try {
                mensajeRecibido = entradaDatos.readUTF();
                mensajes.setMensaje(mensajeRecibido);
                System.out.println(mensajeRecibido);
            } catch (IOException ex) {
                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
                conectado = false;
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException ex2) {
                	System.out.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
                }
            }
        }   
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            salidaDatos.writeUTF(arg.toString());
        } catch (IOException ex) {
        	System.out.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }
} 