import java.util.ArrayList;

/**
 * Clase que representa un álbum musical creado por un artista.
 */
public class Album {

    // Atributos
    private String id;
    private String titulo;
    private ArrayList<Cancion> canciones;
    private ArrayList<String> artistas; // nombres de artistas
    private int anioLanzamiento;

    // Constructor
    public Album(String id, String titulo, int anioLanzamiento) {
        this.id = id;
        this.titulo = titulo;
        this.canciones = new ArrayList<>();
        this.artistas = new ArrayList<>();
        this.anioLanzamiento = anioLanzamiento;
    }

    // Métodos
    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
        System.out.println("✅ \"" + cancion.getTitulo() + "\" añadida al álbum \"" + titulo + "\".");
    }

    public void listarCanciones() {
        if (canciones.isEmpty()) {
            System.out.println("El álbum \"" + titulo + "\" no tiene canciones.");
            return;
        }
        System.out.println("\n💿 Álbum: " + titulo + " (" + anioLanzamiento + ")");
        System.out.println("   Artista(s): " + getArtistasString());
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.printf("  %2d. %s%n", (i + 1), canciones.get(i).toString());
        }
        System.out.println("──────────────────────────────────");
        System.out.println("   Total: " + canciones.size() + " canciones");
    }

    public Cancion buscarCancion(String titulo) {
        for (Cancion c : canciones) {
            if (c.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                return c;
            }
        }
        System.out.println("❌ No se encontró \"" + titulo + "\" en el álbum.");
        return null;
    }

    public void agregarArtista(String nombreArtista) {
        artistas.add(nombreArtista);
    }

    public String getArtistasString() {
        if (artistas.isEmpty()) return "Desconocido";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < artistas.size(); i++) {
            sb.append(artistas.get(i));
            if (i < artistas.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public ArrayList<Cancion> getCanciones() { return canciones; }
    public int getAnioLanzamiento() { return anioLanzamiento; }

    @Override
    public String toString() {
        return "💿 " + titulo + " (" + anioLanzamiento + ") - " + canciones.size() + " canciones";
    }
}
