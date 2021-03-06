package games.bcar.nooutput.Recurso;

public class Mapas {
	
	private static String nivel1Vertices= // a - n1
			"a,1,7;b,3,7;c,3,5;d,3,1;e,13,1;f,13,5;g,7,5;h,7,9;i,15,9;j,15,5;"
			+"k,21,5;l,21,3;m,21,1;n,25,3;o,31,3;p,31,1;q,31,9;r,25,13;s,31,13;t,31,17;u,13,13;"
			+"v,7,13;w,7,17;x,3,15;y,1,15;z,1,23;a1,5,17;b1,9,17;c1,9,23;d1,13,23;e1,13,17;"
			+"f1,17,17;g1,17,23;h1,21,23;i1,21,17;j1,25,17;k1,25,21;l1,31,21;m1,31,23;n1,5,23;";
	private static String nivel1Nodos=
			"a,b;b,c;b,x;c,d;c,g;d,e;e,f;e,m;m,l;f,g;f,j;g,h;h,i;h,v;j,i;l,k;k,j;"
			+"l,n;n,o;n,r;o,p;o,q;r,s;s,t;v,u;v,w;x,y;y,z;z,n1;n1,a1;w,a1;w,b1;b1,c1;c1,d1;"
			+"d1,e1;e1,f1;f1,g1;g1,h1;h1,i1;i1,j1;j1,t;j1,k1;k1,l1;l1,m1;";
	private static String nivel2Vertices=
			"a,1,1;b,3,1;c,1,2;d,2,2;e,2,3;f,3,3;g,3,4;h,1,4;i,4,4;j,1,5;k,3,5;l,4,6;m,3,6;n,1,6;"
			+"o,1,8;p,3,8;q,3,9;r,4,9;s,1,11;t,3,11;u,3,10;v,4,10;w,6,10;x,6,11;y,7,11;z,7,10;"
			+"a1,4,8;b1,6,8;c1,6,9;d1,6,7;e1,7,7;f1,7,5;g1,8,8;h1,8,7;i1,9,7;j1,8,6;k1,9,6;l1,8,5;m1,8,4;n1,10,4;"
			+"o1,10,6;p1,11,6;q1,11,8;r1,10,8;s1,10,9;t1,11,9;u1,11,10;v1,9,10;w1,9,11;x1,14,11;y1,15,11;z1,15,10;"
			+"a2,14,10;b2,12,10;c2,12,9;d2,13,9;e2,13,8;f2,12,8;g2,12,6;h2,14,6;i2,14,9;j2,15,9;k2,14,4;l2,13,4;m2,12,4;n2,13,2;"
			+"o2,14,2;p2,14,1;q2,12,2;r2,12,1;s2,12,3;t2,11,3;u2,11,2;v2,11,1;w2,7,1;x2,9,2;y2,9,3;z2,7,3;"
			+"a3,7,2;b3,5,2;c3,4,2;d3,4,3;e3,5,3;f3,6,3;g3,6,5;h3,5,5;i3,5,7;j3,4,7;k3,8,10;l3,8,9;m3,9,9;n3,9,8;"
			+"o3,10,7;";
//	
//	"a,1,1;b,3,1;c,1,2;d,2,2;e,2,3;f,3,3;g,3,4;h,2,4;i,4,4;j,2,5;k,3,5;l,4,6;m,3,6;n,2,6;"
//	+"o,2,8;p,3,8;q,3,9;r,4,9;s,2,11;t,3,11;u,3,10;v,4,10;w,6,10;x,6,11;y,7,11;z,7,10;"
//	+"a1,4,8;b1,6,8;c1,6,9;d1,6,7;e1,7,7;f1,7,5;g1,8,8;h1,8,7;i1,9,7;j1,8,6;k1,9,6;l1,8,5;m1,8,4;n1,10,4;"
//	+"o1,10,6;p1,11,6;q1,11,8;r1,10,8;s1,10,9;t1,11,9;u1,11,10;v1,9,10;w1,9,11;x1,14,11;y1,15,11;z1,15,10;"
//	+"a2,14,10;b2,12,10;c2,12,9;d2,13,9;e2,13,8;f2,12,8;g2,12,6;h2,14,6;i2,14,9;j2,15,9;k2,14,4;l2,13,4;m2,12,4;n2,13,2;"
//	+"o2,14,2;p2,14,1;q2,12,2;r2,12,1;s2,12,3;t2,11,3;u2,11,2;v2,11,1;w2,7,1;x2,9,2;y2,9,3;z2,7,3;"
//	+"a3,7,2;b3,5,2;c3,4,2;d3,4,3;e3,5,3;f3,6,3;g3,6,5;h3,5,5;i3,5,7;j3,4,7;k3,8,10;l3,8,9;m3,9,9;n3,9,8;"
//	+"o3,10,7;";
	private static String nivel2Nodos=
			"a,b;a,c;c,d;d,e;b,f;e,f;f,g;g,h;g,i;h,j;j,k;i,l;k,m;m,l;n,m;n,o;o,p;o,s;p,q;q,r;s,t;t,u;u,v;v,r;"
			+"r,a1;a1,b1;r,c1;v,w;c1,b1;b1,d1;d1,e1;e1,f1;f1,l1;l1,m1;l1,j1;m1,n1;w,x;x,y;y,z;z,k3;k3,l3;l3,m3;"
			+"m3,n3;b1,g1;g1,h1;h1,i1;i1,k1;k1,j1;i1,o3;n1,m2;n1,o1;o1,p1;p1,q1;q1,r1;r1,s1;s1,t1;t1,u1;t1,c2;u1,v1;"
			+"v1,w1;w1,x1;x1,y1;x1,a2;a2,z1;a2,b2;b2,c2;c2,d2;d2,e2;e2,f2;f2,g2;g2,h2;h2,i2;i2,j2;h2,k2;k2,l2;"
			+"l2,n2;n2,o2;o2,p2;n2,q2;q2,r2;l2,m2;m2,s2;s2,t2;t2,u2;u2,v2;u2,x2;x2,y2;t2,y2;y2,z2;v2,w2;w2,a3;"
			+"z2,a3;a3,b3;b3,e3;b3,c3;c3,d3;d3,f;e3,f3;f3,g3;g3,h3;h3,i3;i3,j3;j3,l;";
	
	public static String [] getMapa(int i){
		String [] nivel=new String [2];
		if(i==1){
			nivel[0]=nivel1Vertices;
			nivel[1]=nivel1Nodos;
		}
		if(i==2){
			nivel[0]=nivel2Vertices;
			nivel[1]=nivel2Nodos;
		}
		return nivel;
	}
}
