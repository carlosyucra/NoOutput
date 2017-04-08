package games.bcar.nooutput;/*
 * Sera la clase de Inteligencia artificial las accion que requieran de
 * inteligencia artificial recurriran a esta clase
 * By: Carlos Yucra Condori
 * 	
 * 	Todos los derechos reservados
 * 	20 de marzo del 2015
 *  hora 19:05
 */

import java.util.ArrayList;

import games.bcar.nooutput.Recurso.NodoD;
import games.bcar.nooutput.Recurso.PuntoD;

public class IA {
	private ArrayList<PuntoD>verticesDijkstra;
	private ArrayList<NodoD>nodosDijkstra;
	private ArrayList<PuntoD> camino;
	private int lon;
	private int ite;
	private PuntoD z=null;
	public ArrayList<PuntoD> getCamino(ArrayList<PuntoD> vertices,ArrayList<NodoD> nodos,PuntoD buscado,PuntoD buscador){
		//Buscado es el buscado por lo que sera en el que tenemos que empezar a buscar hasya llegar al buscador
		this.camino=new ArrayList<PuntoD>();
		verticesDijkstra=new ArrayList<PuntoD>();
		nodosDijkstra=new ArrayList<NodoD>();
		for(PuntoD p:vertices){
			p.setLongitud(600);
			p.setIteraciones(600);
			p.setPredecesor(null);
		}
		this.verticesDijkstra=vertices;
		this.nodosDijkstra=nodos;
		lon=0;
		ite=0;
		caminoDijkstra(verticesDijkstra.get(convertID(buscador.getID())),z,lon); //ALGORITMO UFFFF ME COSTO HACERLO

		predecesor(buscado,buscador);
		return camino;
	}
	
	//Este es el algoritmo de la inteligencia artificial
	//Se llama el algorimto dijkstra para encontrar el menor camino
	//y asi poder tener un camino mas inteligente
	//Es mi primer algorimto bueno mi segundo
	//el primero fue la maquina de estado finito ...
	public void caminoDijkstra(PuntoD ini,PuntoD pre,int lon){
		ini.setLongitud(lon);
		if(pre != null)ini.setPredecesor(pre);
		ini.setIteraciones(ite);
		ite++;
		for(PuntoD p:adyacentesDijkstra(ini)){
			if(pre == null || !p.equals(pre)){
				lon = lon + longitud(p,ini);
				if(p.getLongitud()>lon){
					caminoDijkstra(p,ini,lon);
				}
				//Prueba 1-bien
				else if(p.getLongitud()==lon){
					int i = (int)(Math.random()*2); //Da valores entre 0 y 1 
					//sirve para que el camino no sea el mismo 100pre "IA"
					if(i==0){
						caminoDijkstra(p,ini,lon);
					}
				}
				
				lon = lon - longitud(p,ini);
			}
		}
		ite--;
		
	}
	public void predecesor(PuntoD buscado,PuntoD buscador){
		for(PuntoD p:verticesDijkstra){
			if(p.equals(buscado)){
				camino.add(p.getPredecesor());
				if(p.getPredecesor()==null|| p.getPredecesor().equals(buscador))break; 
				predecesor(p.getPredecesor(),buscador);  
			}
		}
	}

	public ArrayList<PuntoD> adyacentesDijkstra(PuntoD punto){
		ArrayList<PuntoD> adyacentes = new ArrayList<PuntoD>();
		for(NodoD n: nodosDijkstra){
			if(n.getPunto1().equals(punto)){adyacentes.add(n.getPunto2());}
			if(n.getPunto2().equals(punto)){adyacentes.add(n.getPunto1());}
		}
		return adyacentes;
	}
	public int longitud(PuntoD a,PuntoD b){
		int p=0;
		if(a.getX()==b.getX()){p=Math.abs(a.getY()-b.getY());}
		if(a.getY()==b.getY()){p=Math.abs(a.getX()-b.getX());}
		return p;
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
}
