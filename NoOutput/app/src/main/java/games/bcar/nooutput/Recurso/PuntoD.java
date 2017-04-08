package games.bcar.nooutput.Recurso;

public class PuntoD {
	private String id ;
	private int x;
	private int y;
	private int longitud=600;			//Es la longitud para el algoritmo de dijkstra
	private PuntoD predecesor=null;	//El antecesor
	private int iteraciones=600;		//Las iteraciones que se hace desde un punto "Dijkstra"
	
	public PuntoD(String id,int x,int y){
		this.id=id;
		this.x=x;
		this.y=y;
	}
	//METODOS DE SET O MODIFICADORES
	public void setID(String id){
		this.id=id;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public void setLongitud(int lon){
		this.longitud=lon;
	}
	public void setPredecesor(PuntoD pre){
		if(pre==null)this.predecesor=null;
		else this.predecesor=pre.clone();  //## En evaluacion se clonara
	}
	public void setIteraciones(int ite){
		this.iteraciones=ite;
	}
	
	//METODOS DE GET O PERMUTADORES
	public String getID(){
		return id;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getLongitud(){
		return longitud;
	}
	public PuntoD getPredecesor(){
		return predecesor;
	}
	public int getIteraciones(){
		return iteraciones;
	}
	
	//CLONACION PARA QUE NO DEPENDA DE DE UNA SOLA POR SI ALGO SALE O SE MODIFICA
	public PuntoD clone(){
		return new PuntoD(id,x,y);
	}
	
	//COMPARAR DOS OBJETOS
	public boolean equals(PuntoD punt){ 
		if(this.getX()==punt.getX() && this.getY()==punt.getY())
			return true;
		return false;
	}
	//IMPRIMIR UN OBJETO
	public String toString(){
		String retorna="";
		retorna="ID="+id+", Vertices X="+getX()+",Y="+getY();
		if(predecesor!=null)retorna+="   Pre = ("+predecesor.getID()+","+predecesor.getX()+
				","+predecesor.getY()+")"+" Lon = "+longitud+" ite = "+iteraciones;
		
		return retorna;
	}
}
