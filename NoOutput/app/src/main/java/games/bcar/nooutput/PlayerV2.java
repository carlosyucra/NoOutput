package games.bcar.nooutput;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import games.bcar.nooutput.Recurso.MatrizAdyacente;
import games.bcar.nooutput.Recurso.NodoD;
import games.bcar.nooutput.Recurso.PuntoD;

/**
 * Created by bcar on 05/03/17.
 */

public class PlayerV2 {
    Laberinto laberinto;
    ArrayList<NodoD> nodos;
    ArrayList<PuntoD> vertices;
    MatrizAdyacente adyacentes;
    PuntoD punto;
    int LX,LY;
    int grosor;
    int posX, posY;
    Bitmap imagen;

    //VARIABLES PARA RECORRIDO
    int STOP = 0;
    int DERECHA = 1;
    int IZQUIERDA = 2;
    int ABAJO = 3;
    int ARRIBA = 4;
    int estadoActual = STOP;
    int movimientoActual;
    int movimientoMax;
    int velocidad;
    int restaX,restaY;
    boolean calculandoDireccion;

    //VARIABLES PARA REFERENCIA
    PuntoD dirige; //DONDE SE DIRIGE
    PuntoD antiguoDirige;

    public PlayerV2(Laberinto laberinto){
        this.laberinto=laberinto;
        nodos = laberinto.nodos;
        vertices = (ArrayList<PuntoD>) laberinto.vertices.clone();
        adyacentes = laberinto.adyacentes;
        punto = getPunto("d");
        LX = laberinto.LX;
        LY = laberinto.LY;
        grosor = LX+LY;
        posX = (LX)*punto.getX()-grosor/2;
        posY = (LY)*punto.getY()-grosor/2;
        imagen = imagenScale(grosor,grosor, R.drawable.personaje);

        calculandoDireccion = false;
        velocidad = laberinto.velocidad;
        dirige = punto;
    }
    public Bitmap imagenScale(int ancho, int alto, int id){
        Bitmap tmp = BitmapFactory.decodeResource(laberinto.getResources(),id);
        tmp = Bitmap.createScaledBitmap(tmp,ancho,alto,true);
        return tmp;
    }
    public PuntoD getPunto(String id){
        for(PuntoD p:vertices){
            if(p.getID().equals(id))
                return p.clone();
        }
        System.out.println("No encontrado el punto con id="+id);
        return null;
    }
    public void cambioDireccion(final int dir){
        if(calculandoDireccion==true)return;
        calculandoDireccion = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(estadoActual == STOP){
                    if(dir == DERECHA){
                        PuntoD nuevo = adyacentes.getPuntoAdyacente(punto.getID()).getDerecha();
                        if(nuevo!=null){
                            movimientoActual = LX*(nuevo.getX()-punto.getX());
                            dirige = nuevo.clone();
                            setEstado(DERECHA);
                        }
                    }else if(dir == IZQUIERDA){
                        PuntoD nuevo = adyacentes.getPuntoAdyacente(punto.getID()).getIzquierda();
                        if(nuevo!=null){
                            movimientoActual = LX*(punto.getX()-nuevo.getX());
                            dirige = nuevo.clone();
                            setEstado(IZQUIERDA);
                        }
                    }else if(dir == ARRIBA){
                        PuntoD nuevo = adyacentes.getPuntoAdyacente(punto.getID()).getArriba();
                        if(nuevo!=null){
                            movimientoActual = LY*(punto.getY()-nuevo.getY());
                            dirige = nuevo.clone();
                            setEstado(ARRIBA);
                        }
                    }else if(dir == ABAJO){
                        PuntoD nuevo = adyacentes.getPuntoAdyacente(punto.getID()).getAbajo();
                        if(nuevo!=null){
                            movimientoActual = LY*(nuevo.getY()-punto.getY());
                            dirige = nuevo.clone();
                            setEstado(ABAJO);
                        }
                    }
                }
                calculandoDireccion = false;
            }
        }).start();
    }
    public void setEstado(int estado){
        if(estado==1){
            restaX = velocidad;
            restaY = 0;
        }else if(estado == 2){
            restaX = -velocidad;
            restaY = 0;
        }else if(estado == 3){
            restaY = velocidad;
            restaX = 0;
        }else if(estado == 4){
            restaY = -velocidad;
            restaX = 0;
        }
        this.estadoActual = estado;
    }
    public void hilo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (laberinto.ejecutarJuego) {
                    if (estadoActual == STOP) {
                        //canvas.drawBitmap(imagen,posX,posY,null);
                    } else {
                        if (movimientoActual > 0) {
                            movimientoActual = movimientoActual - velocidad;
                            posX = posX + restaX;
                            posY = posY + restaY;
                        } else {
                            punto = dirige;
                            estadoActual = STOP;
                        }
                        laberinto.repaint();
                    }
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void dibujar(Canvas canvas){
        if(laberinto.control.dibujarSalida
                && Math.abs(laberinto.control.salidaX-posX)<=grosor/2 && Math.abs(laberinto.control.salidaY-posY)<=grosor/2){
            laberinto.gano=true;
            laberinto.nextNivel();
        }
        canvas.drawBitmap(imagen,posX,posY,null);
    }
}
