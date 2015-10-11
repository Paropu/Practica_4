import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class GestorVuelos {
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
		Map<Integer, Vuelo> treeMapVuelos = new TreeMap<Integer, Vuelo>();
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
			} //Fin del bucle de creacion de la lista dinamica que contiene la informacion de los vuelos extraida del txt
		}
		 
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
				break;
				
			case 2:  //MOSTRAR POR PANTALLA VUELOS ORDENADOS POR FECHA DE SALIDA (P3)

				/*Collections.sort(treeMapVuelos); //Se ordena la lista por hora de salida.

				FileWriter fichero = null; //Enviamos la informacion a un fichero txt.
				PrintWriter pw = null;
				try
				{
					//fichero = new FileWriter("salida.txt"); //Uso del programa sin paso de parametros por linea de comandos.
					fichero = new FileWriter(args [1]); //Se captura el nombre del fichero de salida por linea de comandos.
					pw = new PrintWriter(fichero);
					for(Vuelo e:treeMapVuelos){ //Se envia la informacion al fichero de salida con el mismo formato que el fichero de entrada.
						pw.println(e.getIdentificador() + "*" + e.getCompanhia() + "*" + e.getCoste() + "*" + e.getHoraSalida() + "*" + e.getHoraLlegada());
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
				*/
				break;
			case 3: //MOSTRAR POR PANTALLA VUELOS ORDENADOS ALFABETICAMENTE INVERSO Y SI COINCIDE, POR NUMERO DE VUELO
				break;
			case 4:  //MOSTRAR POR PANTALLA INFORMACION DEL VUELO INTRODUCIDO POR IDENTIFICADOR
				break;
			case 5:  //MOSTRAR POR PANTALLA INFORMACION DE VUELOS POR COMPANHIA
				break;
			case 6:  //SALIR DEL PROGRAMA
				System.out.println("*** Fin del programa ***");
				break;
			case 0: //HERRAMIENTA
				Iterator it = treeMapVuelos.keySet().iterator();
				while(it.hasNext()){
				  Integer key = (Integer) it.next();
				System.out.println(treeMapVuelos.get(key)); //treeMapVuelos.get(key) ejecuta el metodo toString definido en la clase del objeto.
				}
				break;
			default: 
				System.out.println("\nERROR: Ha introducido una opcion no valida" + "  ("+ seleccion +")");
				break;
			}
		} while (seleccion!=6);
		
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
  
 */
	

