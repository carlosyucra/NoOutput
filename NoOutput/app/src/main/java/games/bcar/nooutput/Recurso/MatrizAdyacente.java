package games.bcar.nooutput.Recurso;

import java.util.ArrayList;

/**
 * Created by bcar on 05/03/17.
 */

public class MatrizAdyacente {
    ArrayList<PuntoAdyacente> puntos;
    public MatrizAdyacente(ArrayList<PuntoD> vertices, ArrayList<NodoD> nodos){
        puntos = new ArrayList<>();
        for (int i=0;i<vertices.size();i++){
            puntos.add(new PuntoAdyacente(vertices.get(i)));
        }
        for (int i=0;i<nodos.size();i++){
            PuntoAdyacente primero = getPuntoAdyacente(nodos.get(i).getPunto1().getID());
            PuntoAdyacente segundo = getPuntoAdyacente(nodos.get(i).getPunto2().getID());
            if(primero.getActual().getX()==segundo.getActual().getX()){//VERTICAL
                if(primero.getActual().getY()>segundo.getActual().getY()){//primero esta abajo de segundo
                    segundo.abajo=primero.getActual();
                    primero.arriba=segundo.getActual();
                }else{//segundo esta abajo de primero
                    segundo.arriba=primero.getActual();
                    primero.abajo=segundo.getActual();
                }
            }else if(primero.getActual().getY()==segundo.getActual().getY()){//HORIZONTAL
                if(primero.getActual().getX()>segundo.getActual().getX()){//primero esta a la derecha de segundo
                    segundo.derecha=primero.getActual();
                    primero.izquierda=segundo.getActual();
                }else{//segundo esta a la derecha de primero
                    primero.derecha=segundo.getActual();
                    segundo.izquierda=primero.getActual();
                }
            }
        }
    }
    public void addMedios(ArrayList<PuntoD> vertices){
        for (int i=0;i<vertices.size();i++){
            puntos.add(new PuntoAdyacente(vertices.get(i)));
        }
    }
    public void addNodos(ArrayList<NodoD> nodos){
        for (int i=0;i<nodos.size();i++){
            PuntoAdyacente primero = getPuntoAdyacente(nodos.get(i).getPunto1().getID());
            PuntoAdyacente segundo = getPuntoAdyacente(nodos.get(i).getPunto2().getID());
            if(primero.getActual().getX()==segundo.getActual().getX()){//VERTICAL
                if(primero.getActual().getY()>segundo.getActual().getY()){//primero esta abajo de segundo
                    segundo.abajo=primero.getActual();
                    primero.arriba=segundo.getActual();
                }else{//segundo esta abajo de primero
                    segundo.arriba=primero.getActual();
                    primero.abajo=segundo.getActual();
                }
            }else if(primero.getActual().getY()==segundo.getActual().getY()){//HORIZONTAL
                if(primero.getActual().getX()>segundo.getActual().getX()){//primero esta a la derecha de segundo
                    segundo.derecha=primero.getActual();
                    primero.izquierda=segundo.getActual();
                }else{//segundo esta a la derecha de primero
                    primero.derecha=segundo.getActual();
                    segundo.izquierda=primero.getActual();
                }
            }
        }
    }
    public PuntoAdyacente getPuntoAdyacente(String id){
        for (int i=0;i<puntos.size();i++){
            if(puntos.get(i).getActual().getID().equals(id))
                return puntos.get(i);
        }
        return null;
    }
    public PuntoD getPuntoDerecha(String id){
        for (int i=0;i<puntos.size();i++){
            if(puntos.get(i).getActual().getID().equals(id))
                return puntos.get(i).getDerecha();
        }
        return null;
    }
    public PuntoD getPuntoIzquierda(String id){
        for (int i=0;i<puntos.size();i++){
            if(puntos.get(i).getActual().getID().equals(id))
                return puntos.get(i).getIzquierda();
        }
        return null;
    }
    public PuntoD getPuntoArriba(String id){
        for (int i=0;i<puntos.size();i++){
            if(puntos.get(i).getActual().getID().equals(id))
                return puntos.get(i).getArriba();
        }
        return null;
    }
    public PuntoD getPuntoAbajo(String id){
        for (int i=0;i<puntos.size();i++){
            if(puntos.get(i).getActual().getID().equals(id))
                return puntos.get(i).getAbajo();
        }
        return null;
    }
}
