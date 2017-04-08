package games.bcar.nooutput.Recurso;

public class NodoD {
	private PuntoD ex1; //Extremo 1
	private PuntoD ex2; //Extremo 2
	
	public NodoD(PuntoD desde,PuntoD hasta){
		ex1=desde;
		ex2=hasta;
	}
	
	public PuntoD getPunto1(){
		return ex1;
	}
	public PuntoD getPunto2(){
		return ex2;
	}
	
//	public NodoD clone(){
//		return new NodoD(ex1.clone(),ex2.clone());
//	}
	
	public boolean equals(NodoD other){
		return ex1.equals(other.getPunto1()) && ex2.equals(other.getPunto2());
	}
	
	public String toString(){
		return "Enpieza en ("+ex1.getID()+","+ex1.getX()+","+ex1.getY()+")"+
				 "hasta ("+ex2.getID()+","+ex2.getX()+","+ex2.getY()+")";
	}
}