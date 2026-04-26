import java.util.ArrayList;

/**
 * Clase que representa a un artista/creador de contenido en la plataforma.
 * Hereda de Cuenta.
 */
public class Artista extends Cuenta {

    // Atributos
    private String genero;
    private int seguidores;
    private ArrayList<Album> albumes;
    private ArrayList<Cancion> canciones;
    private String biografia;
    private int contadorCanciones;
    private int contadorAlbumes;

    // Constructor
    public Artista(String id, String nombre, String correo, String contrasena,
                   String genero, String biografia) {
        super(id, nombre, correo, contrasena);
        this.genero = genero;
        this.biografia = biografia;
        this.seguidores = 0;
        this.albumes = new ArrayList<>();
        this.canciones = new ArrayList<>();
        this.contadorCanciones = 0;
        this.contadorAlbumes = 0;
    }

    // ─── Gestión de Contenido ─────────────────────────────────────

    public Cancion subirCancion(String titulo, int duracion, String generoCancion) {
        contadorCanciones++;
        String idCancion = "C" + id + "-" + contadorCanciones;
        ArrayList<String> artistas = new ArrayList<>();
        artistas.add(nombre);
        Cancion nueva = new Cancion(idCancion, titulo, duracion, generoCancion, artistas);

        canciones.add(nueva);
        // También la añadimos al catálogo global si existe
        if (catalogoGlobal != null) {
            catalogoGlobal.add(nueva);
        }

        System.out.println("🎵 Canción \"" + titulo + "\" subida exitosamente por " + nombre + ".");
        return nueva;
    }

    public Album crearAlbum(String tituloAlbum, int anio) {
        contadorAlbumes++;
        String idAlbum = "AL" + id + "-" + contadorAlbumes;
        Album nuevo = new Album(idAlbum, tituloAlbum, anio);
        nuevo.agregarArtista(nombre);
        albumes.add(nuevo);
        System.out.println("💿 Álbum \"" + tituloAlbum + "\" creado por " + nombre + ".");
        return nuevo;
    }

    public void agregarCancionAAlbum(Cancion cancion, Album album) {
        album.agregarCancion(cancion);
    }

    public void verEstadisticas() {
        int totalReproducciones = 0;
        for (Cancion c : canciones) {
            totalReproducciones += c.getReproducciones();
        }

        System.out.println("\n╔═══════════ ESTADÍSTICAS DE ARTISTA ═══════════╗");
        System.out.println("  🎤 Artista:          " + nombre);
        System.out.println("  🎸 Género:           " + genero);
        System.out.println("  👥 Seguidores:       " + seguidores);
        System.out.println("  🎵 Canciones subidas:" + canciones.size());
        System.out.println("  💿 Álbumes:          " + albumes.size());
        System.out.println("  ▶  Total escuchas:   " + totalReproducciones);

        if (!canciones.isEmpty()) {
            Cancion masEscuchada = canciones.get(0);
            for (Cancion c : canciones) {
                if (c.getReproducciones() > masEscuchada.getReproducciones()) {
                    masEscuchada = c;
                }
            }
            System.out.println("  🏆 Más escuchada:    " + masEscuchada.getTitulo()
                    + " (" + masEscuchada.getReproducciones() + " escuchas)");
        }
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    public void listarCanciones() {
        if (canciones.isEmpty()) {
            System.out.println(nombre + " no ha subido canciones aún.");
            return;
        }
        System.out.println("\n🎵 Canciones de " + nombre + ":");
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + canciones.get(i).toString()
                    + " | ▶ " + canciones.get(i).getReproducciones());
        }
        System.out.println("──────────────────────────────────");
    }

    public void listarAlbumes() {
        if (albumes.isEmpty()) {
            System.out.println(nombre + " no tiene álbumes aún.");
            return;
        }
        System.out.println("\n💿 Álbumes de " + nombre + ":");
        for (int i = 0; i < albumes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + albumes.get(i).toString());
        }
    }

    public void ganarSeguidor() {
        seguidores++;
    }

    @Override
    public void mostrarPerfil() {
        System.out.println("\n╔══════════ PERFIL DE ARTISTA ══════════╗");
        System.out.println("  🎤 Nombre:     " + nombre);
        System.out.println("  📧 Correo:     " + correo);
        System.out.println("  🎸 Género:     " + genero);
        System.out.println("  👥 Seguidores: " + seguidores);
        System.out.println("  🎵 Canciones:  " + canciones.size());
        System.out.println("  💿 Álbumes:    " + albumes.size());
        System.out.println("  📝 Biografía:  " + biografia);
        System.out.println("╚═══════════════════════════════════════╝");
    }

    // Getters
    public String getGenero() { return genero; }
    public int getSeguidores() { return seguidores; }
    public ArrayList<Album> getAlbumes() { return albumes; }
    public ArrayList<Cancion> getCanciones() { return canciones; }
    public String getBiografia() { return biografia; }
}
