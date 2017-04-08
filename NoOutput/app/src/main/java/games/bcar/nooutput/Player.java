package games.bcar.nooutput;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import games.bcar.nooutput.Recurso.NodoD;
import games.bcar.nooutput.Recurso.PuntoD;

/**
 * Created by bcar on 03/03/17.
 */

public class Player {

    Laberinto laberinto;
    Bitmap imagen;
    int LX,LY;
    int posX, posY;
    int grosor;
    PuntoD punto;
    ArrayList<NodoD> nodos;
    ArrayList<PuntoD> vertices;

    //PARA COORDENADAS:
    int estado = 0;
    int STOP = 0;
    int DERECHA = 1;
    int IZQUIERDA = 2;
    int ABAJO = 3;
    int ARRIBA = 4;
    boolean detener = false;
    int movimiento;
    PuntoD dirige;

    public Player(Laberinto laberinto, int LX, int LY){
        this.laberinto=laberinto;
        this.LX=LX;
        this.LY=LY;
        this.grosor=(LX+LY);
        imagen = imagenScale(grosor,grosor, R.drawable.personaje);
        this.nodos = laberinto.nodos;
        nuevosVertices(laberinto.vertices);
        punto = getPunto("d");
        posX = (LX)*punto.getX()-grosor/2;
        posY = (LY)*punto.getY()-grosor/2;
        dirige=punto;
    }
    public Bitmap imagenScale(int ancho, int alto, int id){
        Bitmap tmp = BitmapFactory.decodeResource(laberinto.getResources(),id);
        tmp = Bitmap.createScaledBitmap(tmp,ancho,alto,true);
        return tmp;
    }
    public void nuevosVertices(ArrayList<PuntoD> ver){
        //Son nuevos porque cada enemigo - jugador tienes sus propios predecesores
        //Estamos independisando al personaje
        vertices = new ArrayList<>();
        for(PuntoD p:ver){
            vertices.add(new PuntoD(p.getID(),p.getX(),p.getY()));
        }
    }

    public PuntoD getPunto(String id){
        for(PuntoD p:vertices){
            if(p.getID().equals(id))
                return p.clone();
        }
        System.out.println("No encontrado el punto con id="+id);
        return null;
    }
    public ArrayList<PuntoD> getAdyacentes(){
        //?¡¡?¡?¡? REFORZAR PUEDE SER QUE EN adyacente hayan repetidos¡¡¡¡
        ArrayList<PuntoD> adyacentes = new ArrayList<PuntoD>();
        for(NodoD n: nodos){
            if(n.getPunto1().equals(punto) && !n.getPunto2().getID().equals(punto.getID()))adyacentes.add(n.getPunto2());
            if(n.getPunto2().equals(punto) && !n.getPunto1().getID().equals(punto.getID()))adyacentes.add(n.getPunto1());
        }
        return adyacentes;
    }

    boolean cambiando = false;
    boolean regresar = false;
    PuntoD tmp;
    public void cambioDireccion(final int dir){
        if(cambiando==true || dir==0)return;
        cambiando = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                    if(estado==STOP){
                        estado=dir;
                        if(1==2)
                        for(PuntoD p:getAdyacentes()){
                            System.out.println("asdasdasdas");
                            if(dir==DERECHA && p.getY()==punto.getY() &&p.getX()>punto.getX()) {
                                estado=dir;
                                dirige = p;
                                movimiento = LX*(p.getX()-punto.getX());
                                break;
                            }
                            else if(dir==IZQUIERDA && p.getY()==punto.getY() && p.getX()<punto.getX()){
                                estado=dir;
                                dirige = p;
                                movimiento = LX*(punto.getX()-p.getX());
                                break;
                            }
                            else if(dir==ARRIBA && p.getX()==punto.getX() && p.getY()<punto.getY()) {
                                estado=dir;
                                dirige = p;
                                movimiento = LY*(punto.getY()-p.getY());
                                break;
                            }
                            else if(dir==ABAJO && p.getX()==punto.getX() && p.getY()>punto.getY()){
                                estado=dir;
                                dirige = p;
                                movimiento = LY*(p.getY()-punto.getY());
                                break;
                            }
                        }
                    }else if(movimiento>0){
                        if(estado==dir){
                            System.out.println("NADA");
                            cambiando=false;
                            return;
                        }else if(estado==DERECHA && dir == IZQUIERDA){
                            if(regresar){
                                movimiento = LX*(dirige.getX()-tmp.getX())-movimiento;
                                dirige=tmp.clone();
                                estado=dir;
                                regresar=false;
                            }else{
                                movimiento = LX*(dirige.getX()-punto.getX())-movimiento;
                                tmp=dirige.clone();
                                dirige=punto.clone();
                                estado=dir;
                                regresar=true;
                            }
                        }else if(estado==IZQUIERDA && dir==DERECHA){
                            if(regresar){
                                movimiento = LX*(tmp.getX()-dirige.getX())-movimiento;
                                dirige=tmp.clone();
                                estado=dir;
                                regresar=false;
                            }else{
                                movimiento = LX*(punto.getX()-dirige.getX())-movimiento;
                                tmp=dirige.clone();
                                dirige=punto.clone();
                                estado=dir;
                                regresar=true;
                            }
                        }else if(estado==ARRIBA && dir==ABAJO){
                            if(regresar){
                                System.out.println("MODIFICANDO");
                                int suma = LY*(tmp.getY()-dirige.getY());
                                movimiento = suma - movimiento;
                                dirige=tmp.clone();

                                estado=dir;
                                regresar=false;
                            }else{
                                movimiento = LY*(punto.getY()-dirige.getY())-movimiento;
                                tmp=dirige.clone();
                                dirige=punto.clone();
                                estado=dir;
                                regresar=true;
                            }
                        }else if(estado==ABAJO && dir==ARRIBA){
                            if(regresar){
                                movimiento = LY*(dirige.getY()-tmp.getY())-movimiento;
                                dirige=tmp.clone();
                                estado=dir;
                                regresar=false;
                            }else{
                                movimiento = LY*(dirige.getY()-punto.getY())-movimiento;
                                tmp=dirige.clone();
                                dirige=punto.clone();
                                estado=dir;
                                regresar=true;
                            }
                        }
                    }
                cambiando=false;
            }
        }).start();
    }
    public void actualizarPunto(){
        punto = dirige.clone();
        //posX = (LX)*punto.getX()-grosor/2;
        //posY = (LY)*punto.getY()-grosor/2;
    }
    public void dibujar(Canvas canvas){
        if(estado==STOP){
            canvas.drawBitmap(imagen,posX,posY,null);
            return;
        }if(detener){
            if(movimiento<=0){
                actualizarPunto();
                estado = STOP;
            }else if(estado==DERECHA){
                //posX++;movimiento--;
                posX=posX+2;movimiento=movimiento-2;
            }else if(estado==IZQUIERDA){
                //posX--;movimiento--;
                posX=posX+2;movimiento=movimiento-2;
            }else if(estado==ARRIBA){
                //posY--;movimiento--;
                posY=posY-2;movimiento=movimiento-2;
            }else if(estado==ABAJO){
                //posY++;movimiento--;
                posY=posY+2;movimiento=movimiento-2;
            }
        }else if(movimiento==0){
            actualizarPunto();
            boolean hayPaso = false;
            for (PuntoD p:getAdyacentes()) {
                if(estado==DERECHA && p.getY()==punto.getY() &&p.getX()>punto.getX()) {
                    dirige = p;
                    movimiento = LX*(p.getX()-punto.getX());
                    hayPaso=true;
                    break;
                }
                else if(estado==IZQUIERDA && p.getY()==punto.getY() && p.getX()<punto.getX()){
                    dirige = p;
                    movimiento = LX*(punto.getX()-p.getX());
                    hayPaso=true;
                    break;
                }
                else if(estado==ARRIBA && p.getX()==punto.getX() && p.getY()<punto.getY()) {
                    dirige = p;
                    movimiento = LY*(punto.getY()-p.getY());
                    hayPaso=true;
                    break;
                }
                else if(estado==ABAJO && p.getX()==punto.getX() && p.getY()>punto.getY()){
                    dirige = p;
                    movimiento = LY*(p.getY()-punto.getY());
                    hayPaso=true;
                    break;
                }
            }

            if(hayPaso==false)
                estado = STOP;
        }else{
            if(estado==DERECHA){
                //posX++;movimiento--;
                posX=posX+2;movimiento=movimiento-2;
            }else if(estado==IZQUIERDA){
                //posX--;movimiento--;
                posX=posX-2;movimiento=movimiento-2;
            }else if(estado==ARRIBA){
                //posY--;movimiento--;
                posY=posY-2;movimiento=movimiento-2;
            }else if(estado==ABAJO){
                //posY++;movimiento--;
                posY=posY+2;movimiento=movimiento-2;
            }
        }
        canvas.drawBitmap(imagen,posX,posY,null);
    }


}
