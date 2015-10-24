import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class GestorVuelos {

	public static void main(String args[]) {
		FileInputStream flujo_entrada = null;
		try {
			flujo_entrada = new FileInputStream(args[0]); // nombre fichero linea de comandos.
		} // Se crea un flujo de datos al fichero.
		catch (FileNotFoundException excepcion1) { // Si el fichero no existe, salta excepcion
			System.out.println("Fichero inexistente");
			System.exit(-1);
		}
		Scanner entrada = new Scanner(flujo_entrada);
		String linea = null; // Variable que contendra la informacion escaneada del fichero

		//Creamos TreeMap para los datos
		TreeMap<Integer, Vuelo> treeMapVuelos = new TreeMap<Integer, Vuelo>();
		TreeMap<Integer, Vuelo> treeMapVuelosHoraSalida = new TreeMap<Integer, Vuelo>();
		TreeMap<String, Vuelo> treeMapVuelosCompanhia = new TreeMap<String, Vuelo>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				String o11[] = o1.split(" . ");
				String o21[] = o2.split(" . ");
				if ((o21[0].compareTo(o11[0])) == 0) {
					return o11[1].compareTo(o21[1]);
				}
				return o21[0].compareTo(o11[0]);
			}
		});
		
		while (entrada.hasNextLine()) { 
			linea = entrada.nextLine(); 
			StringTokenizer separador = new StringTokenizer(linea, "*");
			while (separador.hasMoreTokens()) { // Separamos los elementos de la linea escaneada
				String identificador = separador.nextToken().replace(" ", "");
				String companhia = separador.nextToken();
				String coste = separador.nextToken().replace(" ", "");
				String horaSalida = separador.nextToken().replace(" ", "");
				String horaLlegada = separador.nextToken().replace(" ", "");
				String duracion = Vuelo.duracionVuelo(horaSalida, horaLlegada);
				// Construimos un objeto Vuelo en cada iteracion con los parametros escaneados anteriormente.
				Vuelo vueloActual = new Vuelo(identificador, companhia, coste, horaSalida, horaLlegada, duracion);
				treeMapVuelos.put(Integer.parseInt(vueloActual.getIdentificador()), vueloActual); 
				treeMapVuelosHoraSalida.put(Integer.parseInt(vueloActual.getHoraSalida().replace(":", "")), vueloActual);
				treeMapVuelosCompanhia.put(vueloActual.getCompanhia() + " . " + vueloActual.getIdentificador(), vueloActual);
			} 
		}
		
		//Menú
		entrada.close();
		Scanner teclado = new Scanner(System.in);
		int seleccion;
		do {
			System.out.printf("\n\t\t\t||  GESTOR DE VUELOS  ||\n\n\n\t1.-ANHADIR VUELO\n\n\t2.-ORDENAR VUELOS POR FECHA DE SALIDA\n\n\t"
					+ "3.-ORDENAR VUELOS POR COMPANHIA\n\n\t"
					+ "4.-BUSCAR VUELOS POR IDENTIFICADOR\n\n\t5.-BUSCAR VUELOS POR COMPANHIA\n\n\t6.-SALIR DEL PROGRAMA\n\n\n\tSeleccione una opcion:  ");

			seleccion = teclado.nextInt();
			switch (seleccion) {

			case 1: // ANHADIR VUELO
				System.out.printf("\nIntroduzca los datos solicitados a continuacion:\n\tIdentificador: "); // RECOGER BIEN LOS DATOS
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
				treeMapVuelos.put(Integer.parseInt(vueloActual.getIdentificador()), vueloActual);
				treeMapVuelosHoraSalida.put(Integer.parseInt(vueloActual.getHoraSalida().replace(":", "")), vueloActual);
				treeMapVuelosCompanhia.put(vueloActual.getCompanhia() + " . " + vueloActual.getIdentificador(), vueloActual);
				break;

			case 2: // ORDENAR Y MOSTRAR POR PANTALLA VUELOS POR HORA DE SALIDA 
				System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |\n");
				Iterator it = treeMapVuelosHoraSalida.keySet().iterator();
				while (it.hasNext()) {
					Integer key = (Integer) it.next();
					System.out.println(treeMapVuelosHoraSalida.get(key)); 
				}
				break;

			case 3: // ORDENAR Y MOSTRAR POR PANTALLA VUELOS ORDENADOS ALFABETICAMENTE INVERSO Y SI COINCIDE, POR NUMERO DE VUELO DE MENOR A MAYOR
				System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |\n");
				Iterator iti = treeMapVuelosCompanhia.keySet().iterator();
				while (iti.hasNext()) {
					String key = (String) iti.next();
					System.out.println(treeMapVuelosCompanhia.get(key)); 
				}
				break;

			case 4: // MOSTRAR POR PANTALLA INFORMACION DEL VUELO INTRODUCIDO POR IDENTIFICADOR
				System.out.print("\n\tIntroduzca un identificador de vuelo: ");
				int identificadorBuscado = teclado.nextInt();
				teclado.nextLine(); // Se limpia el buffer.
				if (treeMapVuelos.containsKey(identificadorBuscado)) {
					System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |");
					System.out.println("\n" + treeMapVuelos.get(identificadorBuscado) + "\n\n");
				} else{
					System.out.println("\tERROR: No existe ningun vuelo con ese identificador\n");
				}
					break;

			case 5: // MOSTRAR POR PANTALLA INFORMACION DE VUELOS POR COMPANHIA
				System.out.print("\n\tIntroduzca una companhia de vuelo: ");
				String companhiaBuscada = teclado.next();
				teclado.nextLine(); // Se limpia el buffer.

				System.out.println("\n     |Identificador|\t| Companhia |\t\t| Precio |\t| Salida |\t| Llegada |\t| Duracion |");
				Iterator it2 = treeMapVuelosHoraSalida.keySet().iterator();
				while (it2.hasNext()) {
					Integer key = (Integer) it2.next();
					String companhia = treeMapVuelosHoraSalida.get(key).getCompanhia(); // Guardamos nombre de la companhia para comparar
					if (companhia.startsWith(companhiaBuscada)) {
						System.out.println(treeMapVuelosHoraSalida.get(key));
					}
				}
				break;

			case 6: // SALIR DEL PROGRAMA
				teclado.close();

				FileWriter fichero = null; // Enviamos la informacion a un fichero txt.
				PrintWriter pw = null;
				try {
					// fichero = new FileWriter("salida.txt"); //Uso del programa sin paso de parametros por linea de comandos.
					fichero = new FileWriter(args[1]); // Se captura el nombre del fichero de salida por linea de comandos.
					pw = new PrintWriter(fichero);
					/*
					 * for(Vuelo e:treeMapVuelos){ //Se envia la informacion al fichero de salida con el mismo formato que el fichero de entrada. pw.println(e.getIdentificador() + "*" + e.getCompanhia() + "*" + e.getCoste() + "*" + e.getHoraSalida() + "*" + e.getHoraLlegada()); }
					 */
					Iterator it3 = treeMapVuelos.keySet().iterator();
					while (it3.hasNext()) {
						Integer key = (Integer) it3.next();
						pw.println(treeMapVuelos.get(key).getIdentificador() + "*" + treeMapVuelos.get(key).getCompanhia() + "*"
								+ treeMapVuelos.get(key).getCoste() + "*" + treeMapVuelos.get(key).getHoraSalida() + "*"
								+ treeMapVuelos.get(key).getHoraLlegada()); // treeMapVuelos.get(key) ejecuta el metodo toString definido en la clase del objeto.
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally { // Aseguramos que se cierra el fichero.
					try {
						if (null != fichero)
							fichero.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				break;
			default:
				System.out.println("\nERROR: Ha introducido una opcion no valida  (" + seleccion + ")");
				break;
			}
		} while (seleccion != 6);
	}
}
