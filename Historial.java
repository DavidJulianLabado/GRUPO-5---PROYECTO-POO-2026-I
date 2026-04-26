import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que gestiona el historial de reproducciones de un usuario.
 */
public class Historial {

    // Atributos
    private ArrayList<Cancion> canciones;
    private ArrayList<String> fechas;
    private int tiempoEscuchado; // en segundos

    // Constructor
    public Historial() {
        this.canciones = new ArrayList<>();
        this.fechas = new ArrayList<>();
        this.tiempoEscuchado = 0;
    }

    // Métodos
    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        fechas.add(fecha);
        tiempoEscuchado += cancion.getDuracion();
        System.out.println("📝 Añadido al historial: " + cancion.getTitulo());
    }

    public void limpiarHistorial() {
        canciones.clear();
        fechas.clear();
        tiempoEscuchado = 0;
        System.out.println("🗑  Historial limpiado correctamente.");
    }

    public void generarResumen() {
        if (canciones.isEmpty()) {
            System.out.println("El historial está vacío.");
            return;
        }
        System.out.println("\n╔══════════════ HISTORIAL DE REPRODUCCIÓN ══════════════╗");
        for (int i = canciones.size() - 1; i >= 0; i--) {
            System.out.printf("  %2d. %-35s [%s]%n",
                    (canciones.size() - i),
                    canciones.get(i).toString(),
                    fechas.get(i));
        }
        int min = tiempoEscuchado / 60;
        System.out.println("  Total escuchado: " + min + " minutos");
        System.out.println("  Total canciones: " + canciones.size());
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    // Getters
    public ArrayList<Cancion> getCanciones() { return canciones; }
    public int getTiempoEscuchado() { return tiempoEscuchado; }
    public int getTotalCanciones() { return canciones.size(); }
}
