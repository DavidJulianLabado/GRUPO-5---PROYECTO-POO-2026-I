import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase que representa una lista de reproducción personalizada del usuario.
 * Implementa RF-04 (Gestión de Bibliotecas).
 */
public class Playlist {

    // Atributos
    private String id;
    private String nombre;
    private ArrayList<Cancion> canciones;
    private String propietario; // nombre del usuario dueño

    // Constructor
    public Playlist(String id, String nombre, String propietario) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.canciones = new ArrayList<>();
    }

    // Métodos
    public void agregarCancion(Cancion cancion) {
        // Verificar que no esté duplicada
        for (Cancion c : canciones) {
            if (c.getId().equals(cancion.getId())) {
                System.out.println("⚠  La canción \"" + cancion.getTitulo() + "\" ya está en la playlist.");
                return;
            }
        }
        canciones.add(cancion);
        System.out.println("✅ \"" + cancion.getTitulo() + "\" añadida a \"" + nombre + "\".");
    }

    public void eliminarCancion(String titulo) {
        boolean encontrada = false;
        for (int i = 0; i < canciones.size(); i++) {
            if (canciones.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                System.out.println("🗑  \"" + canciones.get(i).getTitulo() + "\" eliminada de \"" + nombre + "\".");
                canciones.remove(i);
                encontrada = true;
                break;
            }
        }
        if (!encontrada) {
            System.out.println("❌ No se encontró la canción \"" + titulo + "\" en la playlist.");
        }
    }

    /**
     * Ordena las canciones: 1=Título, 2=Duración, 3=Artista
     */
    public void ordenar(int criterio) {
        switch (criterio) {
            case 1:
                Collections.sort(canciones, Comparator.comparing(Cancion::getTitulo));
                System.out.println("📋 Playlist ordenada alfabéticamente por título.");
                break;
            case 2:
                Collections.sort(canciones, Comparator.comparingInt(Cancion::getDuracion));
                System.out.println("📋 Playlist ordenada por duración.");
                break;
            case 3:
                Collections.sort(canciones, Comparator.comparing(Cancion::getArtistasString));
                System.out.println("📋 Playlist ordenada por artista.");
                break;
            default:
                System.out.println("❌ Criterio de ordenamiento no válido.");
        }
    }

    public ArrayList<Cancion> buscarPorTitulo(String titulo) {
        ArrayList<Cancion> resultados = new ArrayList<>();
        for (Cancion c : canciones) {
            if (c.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public void listarCanciones() {
        if (canciones.isEmpty()) {
            System.out.println("La playlist \"" + nombre + "\" está vacía.");
            return;
        }
        System.out.println("\n🎵 Playlist: " + nombre + " (" + canciones.size() + " canciones)");
        System.out.println("─────────────────────────────────────────────────");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.printf("  %2d. %s%n", (i + 1), canciones.get(i).toString());
        }
        System.out.println("─────────────────────────────────────────────────");
    }

    public int getDuracionTotal() {
        int total = 0;
        for (Cancion c : canciones) total += c.getDuracion();
        return total;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public ArrayList<Cancion> getCanciones() { return canciones; }
    public String getPropietario() { return propietario; }
    public boolean estaVacia() { return canciones.isEmpty(); }

    @Override
    public String toString() {
        return "📁 " + nombre + " (" + canciones.size() + " canciones)";
    }
}
