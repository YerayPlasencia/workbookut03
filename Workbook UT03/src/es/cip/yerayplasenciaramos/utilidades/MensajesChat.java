package es.cip.yerayplasenciaramos.utilidades;

import java.util.Observable;

/**
 * Objeto observable.
 * 
 */

public class MensajesChat extends Observable{

    private String mensaje;
    
    public MensajesChat(){
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        this.setChanged();
        this.notifyObservers(this.getMensaje());
    }
}
