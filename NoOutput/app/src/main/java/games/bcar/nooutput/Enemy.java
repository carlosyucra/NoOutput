package games.bcar.nooutput;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import games.bcar.nooutput.Recurso.NodoD;
import games.bcar.nooutput.Recurso.PuntoD;

/**
 * Created by bcar on 07/03/17.
 */

public class Enemy extends Thread {
    Laberinto laberinto;
    PlayerV2 player;
    int LX,LY;
    int grosor;

    private ArrayList<PuntoD> vertices;
    private ArrayList<NodoD>nodos;
    PuntoD puntoActual;

    Bitmap cara;
    int posX;
    int posY;
    int velocidad;

    private IA inteli=new IA();
    public int tiempo=20;
    private PuntoD objetivo;

    public Enemy(Laberinto laberinto, String inicial){
        this.laberinto = laberinto;
        this.player=laberinto.player;
        this.LX = laberinto.LX;
        this.LY = laberinto.LY;
        grosor = LX+LY;

        nuevosVertices(laberinto.vertices);
        nuevosNodos(laberinto.nodos);
        puntoActual=getPunto(inicial).clone();
        velocidad= laberinto.velocidad;

        inicializar();
    }
    public void inicializar(){
        //Para el personaje es una imagen de una cara
        cara= BitmapFactory.decodeResource(laberinto.getResources(), R.drawable.enemigo);
        cara = Bitmap.createScaledBitmap(cara, grosor,grosor, true);

        posX=puntoActual.getX()*LX-grosor/2;
        posY=puntoActual.getY()*LY-grosor/2;

    }
    public void nuevosVertices(ArrayList<PuntoD> ver){
        //Son nuevos porque cada enemigo - jugador tienes sus propios predecesores
        //Estamos independisando al personaje
        vertices=new ArrayList<PuntoD>();
        for(PuntoD p:ver){
            vertices.add(new PuntoD(p.getID(),p.getX(),p.getY()));
        }
    }
    public void nuevosNodos(ArrayList<NodoD> nod){
        nodos=new ArrayList<NodoD>();
        for(NodoD p:nod){		//INICIALIZAMOS LOS NODOS
            nodos.add(
                    new NodoD(vertices.get(convertID(p.getPunto1().getID())),
                            vertices.get(convertID(p.getPunto2().getID()))));
        }
    }
    public PuntoD getPunto(String id){
        for(PuntoD p:vertices){
            if(p.getID().equals(id))
                return p;
        }
        System.out.println("No encontrado el punto con id="+id);
        return null;
    }
    public int convertID(String id){
        //LO QUE HACE EL PROGRAMA ES DEVOLVER UN NUMERO DE ACUERDO CON LA PALABRA, a=0,b=1,c=2,...
        int r=-1;
        String[] ids={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
                ,"a1","b1","c1","d1","e1","f1","g1","h1","i1","j1","k1","l1","m1","n1","o1","p1","q1","r1","s1","t1","u1","v1","w1","x1","y1","z1"
                ,"a2","b2","c2","d2","e2","f2","g2","h2","i2","j2","k2","l2","m2","n2","o2","p2","q2","r2","s2","t2","u2","v2","w2","x2","y2","z2"
                ,"a3","b3","c3","d3","e3","f3","g3","h3","i3","j3","k3","l3","m3","n3","o3","p3","q3","r3","s3","t3","u3","v3","w3","x3","y3","z3"};
        for(int i=0;i<ids.length;i++){
            if(ids[i].equals(id)){
                r=i;
                break;
            }
        }
        //System.out.println("No se encontra el id en convertID");
        return r;
    }
    public void perseguir(){
        for(PuntoD p:inteli.getCamino(vertices,nodos,puntoActual,player.punto)){
            mover(p);
            if(objetivo!=null && !objetivo.equals(player.dirige))break; //Si el objetivo es nuevo se hace nueva ruta por eso se rompe
        }
    }
    public void mover(PuntoD hasta){
        if(hasta == null)hasta=player.dirige;
        int caminoX=(puntoActual.getX()-hasta.getX())*LX;
        int caminoY=(puntoActual.getY()-hasta.getY())*LY;
        int restanteX,restanteY;
        posX=puntoActual.getX();posX=(posX*LX)-grosor/2;
        posY=puntoActual.getY();posY=(posY*LY)-grosor/2;
        while(caminoX!=0 || caminoY!=0){
            if(caminoX<0){caminoX=caminoX+velocidad;posX=posX+velocidad;}
            if(caminoX>0){caminoX=caminoX-velocidad;posX=posX-velocidad;}
            if(caminoY<0){caminoY=caminoY+velocidad;posY=posY+velocidad;}
            if(caminoY>0){caminoY=caminoY-velocidad;posY=posY-velocidad;}

            restanteX=Math.abs(caminoX)-velocidad;
            restanteY=Math.abs(caminoY)-velocidad;
            if(restanteX<=0 && restanteY<=0)break;

            laberinto.repaint();
            try{
                Thread.sleep(tiempo);
            }catch(InterruptedException e){
                System.out.println("Error bola1 "+e.getMessage());
            }
        }
        puntoActual.setX(hasta.getX());
        puntoActual.setY(hasta.getY());
        puntoActual.setID(hasta.getID());
    }
    public void dibujar(Canvas canvas){
        canvas.drawBitmap(cara,posX,posY,null);
        if(Math.abs(player.posX-posX)<=grosor/2 && Math.abs(player.posY-posY)<=grosor/2){
            laberinto.gano=false;
            laberinto.fin();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(laberinto.ejecutarJuego){
            if(laberinto.pintarDemo==true)return;
            objetivo=player.dirige;
            perseguir();
        }
    }
}
