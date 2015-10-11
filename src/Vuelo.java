import java.util.Calendar;
//import java.util.Comparator;
//import java.util.Collections;
import java.util.GregorianCalendar;

public class Vuelo implements Comparable {

	private String identificador; // Atributos.
	private String companhia;
	private String coste;
	private String horaSalida; 
	private String horaLlegada; 
	private String duracion;

	public Vuelo(String identificador, String companhia, String coste, String horaSalida, String horaLlegada, String duracion) { //Constructor.	
	this.identificador = identificador;
		this.companhia = companhia;
		this.coste = coste;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.duracion = duracion; //Invocar metodo duracionVuelo directamente para mostrar por pantalla ???
	}

	public String getIdentificador() { //Getters
		return identificador;
	}

	public String getCompanhia() {
		return companhia;
	}

	public String getCoste() {
		return coste;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public String getHoraLlegada() {
		return horaLlegada;
	}
	
	
	public static String duracionVuelo(String hora1, String hora2) { //Metodo que calcula la duracion del vuelo.

		// Separo en dos strings
		String TimeSalida[] = hora1.split(":");
		String TimeLlegada[] = hora2.split(":");

		// Creo dos calendarios con ambas horas
		Calendar Salida = new GregorianCalendar(0, 0, 0, Integer.parseInt(TimeSalida[0]), Integer.parseInt(TimeSalida[1]));
		Calendar Llegada = new GregorianCalendar(0, 0, 0, Integer.parseInt(TimeLlegada[0]), Integer.parseInt(TimeLlegada[1]));

		// Calculo tiempo entre fechas en milisegundos
		long diferencia = (Llegada.getTimeInMillis() - Salida.getTimeInMillis());
		float n_millisec = new Float(diferencia); // convierto en float

		float divisor = (float) 3.6E6;// (1000*3600) milisec a horas
		int hora = (int) Math.floor(n_millisec / divisor);
		n_millisec %= divisor; // Resto

		divisor = 60000; // millisec a minutos
		int min = (int) Math.floor(n_millisec / divisor);

		return Integer.toString(hora) + ":" + Integer.toString(min);
	}

	@Override
	public String toString() { // Metodo toString() modificado para mostrar las propiedades del vuelo.
		String mensaje = "     | " + identificador + " |    | " + companhia + " |   | " + coste + " |   | " 
	+ horaSalida + " |   | " + horaLlegada + " |   | " + duracion + " |";
		return mensaje; //OPTIMIZAR INTERFAZ GRAFICA gotoxy();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*@Override
	public int compareTo(Object o) { //Metodo compareTo() modificado para definir el orden natural de los objetos en la lista.
		Vuelo vuelo = (Vuelo) o;
		return this.horaSalida.replace(":", "").compareTo(vuelo.horaSalida.replace(":", ""));
	}*/
	
}