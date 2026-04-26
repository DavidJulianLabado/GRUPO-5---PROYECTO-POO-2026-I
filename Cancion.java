import java.util.ArrayList;

/**
 * Clase que representa una canción dentro de la plataforma.
 * Contiene información técnica y artística del archivo de audio (RF-06).
 */
public class Cancion {

    // Atributos
    private String id;
    private String titulo;
    private int duracion; // en segundos
    private String genero;
    private ArrayList<String> artistas; // nombres de artistas
    private int reproducciones;
    private boolean disponibleOffline;

    // Constructor
    public Cancion(String id, String titulo, int duracion, String genero, ArrayList<String> artistas) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.artistas = artistas;
        this.reproducciones = 0;
        this.disponibleOffline = false;
    }

    // Constructor simplificado (un solo artista)
    public Cancion(String id, String titulo, int duracion, String genero, String artista) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.artistas = new ArrayList<>();
        this.artistas.add(artista);
        this.reproducciones = 0;
        this.disponibleOffline = false;
    }

    // Métodos
    public void reproducir() {
        reproducciones++;
        System.out.println("▶  Reproduciendo: \"" + titulo + "\" - " + getArtistasString()
                + " [" + formatearDuracion() + "]");
    }

    public void pausar() {
        System.out.println("⏸  Pausado: \"" + titulo + "\"");
    }

    public String formatearDuracion() {
        int min = duracion / 60;
        int seg = duracion % 60;
        return String.format("%d:%02d", min, seg);
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

    public void mostrarInfo() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("  🎵 " + titulo);
        System.out.println("  👤 Artista(s): " + getArtistasString());
        System.out.println("  ⏱  Duración:   " + formatearDuracion());
        System.out.println("  🎸 Género:     " + genero);
        System.out.println("  📊 Escuchas:   " + reproducciones);
        System.out.println("  📥 Offline:    " + (disponibleOffline ? "Sí" : "No"));
        System.out.println("╚══════════════════════════════════════╝");
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getDuracion() { return duracion; }
    public String getGenero() { return genero; }
    public ArrayList<String> getArtistas() { return artistas; }
    public int getReproducciones() { return reproducciones; }
    public boolean isDisponibleOffline() { return disponibleOffline; }

    public void setDisponibleOffline(boolean disponibleOffline) {
        this.disponibleOffline = disponibleOffline;
    }
    public void incrementarReproducciones() { this.reproducciones++; }

    @Override
    public String toString() {
        return "\"" + titulo + "\" - " + getArtistasString() + " (" + formatearDuracion() + ")";
    }
}
