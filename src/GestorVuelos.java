import java.io.FileInputStream;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
//import java.util.Collections;
import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
//import java.util.*;

public class GestorVuelos {

	public class ComparatorHoraSalida implements Comparator<Vuelo> {//Orden natural???
		@Override
		public int compare(Vuelo p1, Vuelo p2) {
			if ((Integer.parseInt(p1.getHoraSalida().replace(":", "")))<(Integer.parseInt(p2.getHoraSalida().replace(":", "")))) return -1;
			else if ((Integer.parseInt(p1.getHoraSalida()))==(Integer.parseInt(p2.getHoraSalida()))) return 0;
			return 1;	 
		}
	}

	public class ComparatorHoraSalida2 implements Comparator<Vuelo> {//Orden natural???
		@Override
		public int compare(Vuelo o1, Vuelo o2) {
			return o2.compareTo(o1);
		}
	}

	public class ComparatorCompanhia implements Comparator<String> {//Orden Companhia alfabetico???
		@Override
		public int compare(String o1, String o2) {
			return o2.compareTo(o1);
		}


	}
		public static void main (String args []){
			FileInputStream flujo_entrada = null;
			try {
				//flujo_entrada = new FileInputStream("base_de_datos.txt"); // Uso del programa sin argumentos por linea de comandos.
				flujo_entrada = new FileInputStream(args [0]); //Se captura el nombre del fichero de entrada por linea de comandos.
			} // Se crea un flujo de datos al fichero.
			catch (FileNotFoundException excepcion1) { // Si el fichero no existe, salta excepcion y se muestra mensaje por pantalla.
				System.out.println("Fichero inexistente");
				System.exit(-1);
			}
			Scanner entrada = new Scanner(flujo_entrada);// Se crea un objeto para escanear la linea del fichero
			String linea = null; // Variable que contendra la informacion escaneada del fichero
			
			TreeMap<Integer, Vuelo> treeMapVuelos = new TreeMap<Integer, Vuelo>();
			TreeMap<Integer, Vuelo> treeMapVuelosHoraSalida = new TreeMap<Integer, Vuelo>();	
			
			TreeMap<String, Vuelo> treeMapVuelosCompanhia = new TreeMap <String, Vuelo> (new Comparator <String> (){
				@Override
				public int compare(String o1, String o2) {
					String o11[] = o1.split(" ");
					String o21[] = o2.split(" ");
					if ((o21[0].compareTo(o11[0]))==0){
						return o11[1].compareTo(o21[1]);
					}
					return o21[0].compareTo(o11[0]);
				}
			});
			
			TreeMap<String, Vuelo> treeMapVuelosBuscarCompanhia = new TreeMap<String, Vuelo>();	

			while (entrada.hasNextLine()) { // Mientras hay lineas por leer...
				linea = entrada.nextLine(); // Escaneamos la linea.
				StringTokenizer separador = new StringTokenizer(linea, "*");
				while (separador.hasMoreTokens()) { // Separamos los elementos de la linea escaneada
					String identificador = separador.nextToken().replace(" ", "");
					String companhia = separador.nextToken();
					String coste = separador.nextToken().replace(" ", "");
					String horaSalida = separador.nextToken().replace(" ", "");
					String horaLlegada = separador.nextToken().replace(" ", "");
					String duracion = Vuelo.duracionVuelo(horaSalida, horaLlegada);
					// Construimos un objeto Vuelo en cada iteracion con los
					// parametros escaneados anteriormente.
					Vuelo vueloActual = new Vuelo(identificador, companhia, coste, horaSalida, horaLlegada, duracion);
					treeMapVuelos.put(Integer.parseInt(vueloActual.getIdentificador()), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosHoraSalida.put(Integer.parseInt(vueloActual.getHoraSalida().replace(":", "")), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosCompanhia.put(vueloActual.getCompanhia() + " " + vueloActual.getIdentificador(), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosBuscarCompanhia.put(vueloActual.getCompanhia() + " " + vueloActual.getIdentificador(), vueloActual); //Añadimos el objeto creado a la lista enlazada.
				} //Fin del bucle de creacion de la lista dinamica que contiene la informacion de los vuelos extraida del txt
			}
entrada.close();
			Scanner teclado = new Scanner(System.in);
			int seleccion;
			do {
				System.out.printf("\n\t\t\t||  GESTOR DE VUELOS  ||\n\n\n\t1.-ANHADIR VUELO\n\n\t2.-ORDENAR VUELOS POR FECHA DE SALIDA\n\n\t"
						+ "3.-ORDENAR VUELOS POR COMPANHIA\n\n\t"
						+ "4.-BUSCAR VUELOS POR IDENTIFICADOR\n\n\t5.-BUSCAR VUELOS POR COMPANHIA\n\n\t6.-SALIR DEL PROGRAMA\n\n\n\tSelecione una opcion:  ");

				seleccion = teclado.nextInt();
				switch (seleccion){

				case 1:  //AÑADIR VUELO
					System.out.printf("\nIntroduzca los datos solicitados a continuacion:\n\tIdentificador: "); //RECOGER BIEN LOS DATOS
					String identificador1 = teclado.next();
					teclado.nextLine();
					System.out.printf("\n\tCompanhia: ");
					String companhia1 = teclado.nextLine();
					System.out.printf("\n\tCoste: ");
					String coste1 = teclado.next();
					System.out.printf("\n\tHora de salida: ");
					String horaSalida1 = teclado.next();
					System.out.printf("\n\tHora de llegada: ");
					String horaLlegada1 = teclado.next();
					String duracion1 = Vuelo.duracionVuelo(horaSalida1, horaLlegada1);
					Vuelo vueloActual = new Vuelo(identificador1, companhia1, coste1, horaSalida1, horaLlegada1, duracion1);
					treeMapVuelos.put(Integer.parseInt(vueloActual.getIdentificador()), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosHoraSalida.put(Integer.parseInt(vueloActual.getHoraSalida().replace(":", "")), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosCompanhia.put(vueloActual.getCompanhia() + " " + vueloActual.getIdentificador(), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					treeMapVuelosBuscarCompanhia.put(vueloActual.getCompanhia() + " " + vueloActual.getIdentificador(), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					//treeMapVuelosHoraSalida.put(vueloActual.getHoraSalida().replace(":", ""), vueloActual); //Añadimos el objeto creado a la lista enlazada.
					break;
					

				case 2:  //ORDENAR Y MOSTRAR POR PANTALLA VUELOS POR HORA DE SALIDA (P3)
					System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |\n");
					Iterator it = treeMapVuelosHoraSalida.keySet().iterator();
					while(it.hasNext()){
						Integer key = (Integer) it.next();
						System.out.println(treeMapVuelosHoraSalida.get(key)); //treeMapVuelos.get(key). ejecuta el metodo toString definido en la clase del objeto.
					}
					//ComparatorHoraSalida2 comparador2 = new ComparatorHoraSalida2();
				//Collections.sort(treeMapVuelos);	
					//TreeMap <Integer, Vuelo> treeMapVuelosFechaSalida = new TreeMap<Integer, Vuelo> (new ComparatorHoraSalida2());
					//Collections.sort((TreeMap<Integer, Vuelo>) treeMapVuelos ); Ordenar de forma natural con su compareTo.


					break;

				case 3: //ORDENAR Y MOSTRAR POR PANTALLA VUELOS ORDENADOS ALFABETICAMENTE INVERSO Y SI COINCIDE, POR NUMERO DE VUELO DE MENOR A MAYOR
					//Comparator <Vuelo> comparador= new ComparatorCompanhia();
					//Collections.sort( treeMapVuelos,  comparador);
					System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |\n");
					Iterator iti = treeMapVuelosCompanhia.keySet().iterator();
					while(iti.hasNext()){
						String key = (String) iti.next();
						System.out.println(treeMapVuelosCompanhia.get(key)); //treeMapVuelos.get(key). ejecuta el metodo toString definido en la clase del objeto.
					}
					break;

				case 4:  //MOSTRAR POR PANTALLA INFORMACION DEL VUELO INTRODUCIDO POR IDENTIFICADOR
					
					System.out.println("\n\tIntroduzca un identificador de vuelo: ");
					int identificadorBuscado = teclado.nextInt();
					teclado.nextLine(); //Se limpia el buffer.
					if(treeMapVuelos.containsKey(identificadorBuscado)) {
						System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |");
						System.out.println("\n" + treeMapVuelos.get(identificadorBuscado) + "\n\n");// treeMapVuelos.get(key2).metodo del objeto !!!;
						//System.out.println("Hay ese objeto");
					}
					else System.out.println("\tERROR: No existe ningun vuelo con ese identificador\n");
					break;
				
				case 5:  //MOSTRAR POR PANTALLA INFORMACION DE VUELOS POR COMPANHIA
					System.out.print("\n\tIntroduzca una companhia de vuelo: ");
					String companhiaBuscada = teclado.next();
					teclado.nextLine(); //Se limpia el buffer.
					
					if(treeMapVuelosBuscarCompanhia.containsKey(companhiaBuscada)) {
						System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |");
						System.out.println("\n" + treeMapVuelosBuscarCompanhia.get(companhiaBuscada) + "\n\n");// treeMapVuelos.get(key2).metodo del objeto !!!;
						//System.out.println("Hay ese objeto");
					}
					//if(treeMapVuelos..containsValue(companhiaBuscada)) System.out.println("----");
					//else System.out.println("+++++");
					break;
				
				case 6:  //SALIR DEL PROGRAMA
					System.out.println("*** Fin del programa ***");
					break;
				case 0: //HERRAMIENTA
					/*System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |\n");
					Iterator it = treeMapVuelosHoraSalida.keySet().iterator();
					while(it.hasNext()){
						Integer key = (Integer) it.next();
						System.out.println(treeMapVuelos.get(key)); //treeMapVuelos.get(key) ejecuta el metodo toString definido en la clase del objeto.
					}*/
					break;
				default: 
					System.out.println("\nERROR: Ha introducido una opcion no valida" + "  ("+ seleccion +")");
					break;
				}
			} while (seleccion!=6);

			teclado.close();
			
			FileWriter fichero = null; //Enviamos la informacion a un fichero txt.
			PrintWriter pw = null;
			try
			{
				//fichero = new FileWriter("salida.txt"); //Uso del programa sin paso de parametros por linea de comandos.
				fichero = new FileWriter(args [1]); //Se captura el nombre del fichero de salida por linea de comandos.
				pw = new PrintWriter(fichero);
				/*for(Vuelo e:treeMapVuelos){ //Se envia la informacion al fichero de salida con el mismo formato que el fichero de entrada.
				pw.println(e.getIdentificador() + "*" + e.getCompanhia() + "*" + e.getCoste() + "*" + e.getHoraSalida() + "*" + e.getHoraLlegada());
			}*/
				Iterator it = treeMapVuelos.keySet().iterator();
				while(it.hasNext()){
					Integer key = (Integer) it.next();
					pw.println(treeMapVuelos.get(key).getIdentificador() + "*" + treeMapVuelos.get(key).getCompanhia() + "*" + treeMapVuelos.get(key).getCoste()
							+ "*" + treeMapVuelos.get(key).getHoraSalida() + "*" + treeMapVuelos.get(key).getHoraLlegada()); //treeMapVuelos.get(key) ejecuta el metodo toString definido en la clase del objeto.
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally { //Aseguramos que se cierra el fichero.
				try {
					if (null != fichero)
						fichero.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}	
		}
	}


/*
 Mostrar por pantalla elementos del mapa:

Iterator it = treeMapVuelos.keySet().iterator();
			while(it.hasNext()){
			  Integer key = (Integer) it.next();
			System.out.println(treeMapVuelos.get(key).getHoraSalida()); //treeMapVuelos.get(key) ejecuta el metodo toString definido en la clase del objeto.
			}

-collection.reverse/reverseOrder
-


 */



