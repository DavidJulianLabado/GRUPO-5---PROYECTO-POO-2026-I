import java.util.ArrayList;

/**
 * Clase que representa un usuario estándar (gratuito) de la plataforma.
 * Hereda de Cuenta e implementa funcionalidades de escucha y gestión de playlists.
 */
public class Usuario extends Cuenta {

    // Atributos
    protected String tipoCuenta;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Cancion> favoritos;
    protected Reproductor reproductor;
    private int contadorPlaylists;

    // Constructor
    public Usuario(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena);
        this.tipoCuenta = "GRATUITO";
        this.playlists = new ArrayList<>();
        this.favoritos = new ArrayList<>();
        this.reproductor = new Reproductor();
        this.contadorPlaylists = 0;
    }

    // ─── Registro ────────────────────────────────────────────────

    public void registrarse() {
        System.out.println("✅ Usuario registrado exitosamente.");
        System.out.println("   Nombre: " + nombre);
        System.out.println("   Correo: " + correo);
        System.out.println("   Tipo:   " + tipoCuenta);
    }

    // ─── Gestión de Playlists (RF-04) ────────────────────────────

    public Playlist crearPlaylist(String nombrePlaylist) {
        contadorPlaylists++;
        String idPL = "PL" + id + "-" + contadorPlaylists;
        Playlist nueva = new Playlist(idPL, nombrePlaylist, nombre);
        playlists.add(nueva);
        System.out.println("✅ Playlist \"" + nombrePlaylist + "\" creada correctamente.");
        return nueva;
    }

    public void renombrarPlaylist(int indice, String nuevoNombre) {
        if (indice < 0 || indice >= playlists.size()) {
            System.out.println("❌ Índice de playlist inválido.");
            return;
        }
        String viejo = playlists.get(indice).getNombre();
        playlists.get(indice).setNombre(nuevoNombre);
        System.out.println("✏  Playlist renombrada de \"" + viejo + "\" a \"" + nuevoNombre + "\".");
    }

    public void eliminarPlaylist(int indice) {
        if (indice < 0 || indice >= playlists.size()) {
            System.out.println("❌ Índice de playlist inválido.");
            return;
        }
        System.out.println("🗑  Playlist \"" + playlists.get(indice).getNombre() + "\" eliminada.");
        playlists.remove(indice);
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("No tienes ninguna playlist creada.");
            return;
        }
        System.out.println("\n📂 Tus Playlists:");
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + playlists.get(i).toString());
        }
        System.out.println("──────────────────────────────────");
    }

    // ─── Sistema de Favoritos (RF-05) ────────────────────────────

    public void darMeGusta(Cancion cancion) {
        // Verificar que no esté ya en favoritos
        for (Cancion c : favoritos) {
            if (c.getId().equals(cancion.getId())) {
                System.out.println("💔 \"" + cancion.getTitulo() + "\" ya está en tus Favoritos.");
                return;
            }
        }
        favoritos.add(cancion);
        System.out.println("❤  \"" + cancion.getTitulo() + "\" añadida a Favoritos.");
    }

    public void quitarMeGusta(Cancion cancion) {
        boolean eliminada = favoritos.removeIf(c -> c.getId().equals(cancion.getId()));
        if (eliminada) {
            System.out.println("💔 \"" + cancion.getTitulo() + "\" eliminada de Favoritos.");
        } else {
            System.out.println("⚠  La canción no estaba en Favoritos.");
        }
    }

    public void verFavoritos() {
        if (favoritos.isEmpty()) {
            System.out.println("No tienes canciones en Favoritos todavía.");
            return;
        }
        System.out.println("\n❤  Canciones Favoritas (" + favoritos.size() + "):");
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < favoritos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + favoritos.get(i).toString());
        }
        System.out.println("──────────────────────────────────");
    }

    // ─── Búsqueda en playlists (RF-03) ───────────────────────────

    public void buscarEnPlaylists(String titulo) {
        System.out.println("🔍 Buscando \"" + titulo + "\" en todas tus playlists...");
        boolean hayResultados = false;
        for (Playlist pl : playlists) {
            ArrayList<Cancion> res = pl.buscarPorTitulo(titulo);
            if (!res.isEmpty()) {
                hayResultados = true;
                System.out.println("  En \"" + pl.getNombre() + "\":");
                for (Cancion c : res) {
                    System.out.println("    • " + c.toString());
                }
            }
        }
        if (!hayResultados) {
            System.out.println("  No se encontraron resultados en tus playlists.");
        }
    }

    // ─── Perfil ──────────────────────────────────────────────────

    @Override
    public void mostrarPerfil() {
        System.out.println("\n╔══════════ PERFIL DE USUARIO ══════════╗");
        System.out.println("  👤 Nombre:     " + nombre);
        System.out.println("  📧 Correo:     " + correo);
        System.out.println("  🏷  Tipo:       " + tipoCuenta);
        System.out.println("  📂 Playlists:  " + playlists.size());
        System.out.println("  ❤  Favoritos:  " + favoritos.size());
        System.out.println("  🎵 Escuchadas: " + reproductor.getHistorial().getTotalCanciones());
        System.out.println("╚═══════════════════════════════════════╝");
    }

    // Getters
    public String getTipoCuenta() { return tipoCuenta; }
    public ArrayList<Playlist> getPlaylists() { return playlists; }
    public ArrayList<Cancion> getFavoritos() { return favoritos; }
    public Reproductor getReproductor() { return reproductor; }
}
