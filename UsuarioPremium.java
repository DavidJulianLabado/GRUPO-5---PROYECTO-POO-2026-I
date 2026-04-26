import java.util.ArrayList;

/**
 * Clase que representa un usuario Premium de la plataforma.
 * Hereda de Usuario y añade funcionalidades exclusivas como descargas
 * y audio de alta calidad (RF-07).
 */
public class UsuarioPremium extends Usuario {

    // Atributos exclusivos Premium
    private String fechaSuscripcion;
    private String calidadAudio; // "ALTA", "MEDIA", "BAJA"
    private int limiteDescargas;
    private ArrayList<Cancion> descargas;
    private boolean autoRenovacion;

    // Constructor
    public UsuarioPremium(String id, String nombre, String correo,
                          String contrasena, String fechaSuscripcion) {
        super(id, nombre, correo, contrasena);
        this.tipoCuenta = "PREMIUM";
        this.fechaSuscripcion = fechaSuscripcion;
        this.calidadAudio = "ALTA";
        this.limiteDescargas = 100;
        this.descargas = new ArrayList<>();
        this.autoRenovacion = true;
    }

    // ─── Descargas (RF-07) ───────────────────────────────────────

    public void descargarCancion(Cancion cancion) {
        if (descargas.size() >= limiteDescargas) {
            System.out.println("❌ Límite de descargas alcanzado (" + limiteDescargas + ").");
            return;
        }
        // Verificar que no esté ya descargada
        for (Cancion d : descargas) {
            if (d.getId().equals(cancion.getId())) {
                System.out.println("⚠  \"" + cancion.getTitulo() + "\" ya está descargada.");
                return;
            }
        }
        descargas.add(cancion);
        cancion.setDisponibleOffline(true);
        System.out.println("📥 Descargando \"" + cancion.getTitulo() + "\"... ✅");
        System.out.println("   Disponible offline. (" + descargas.size() + "/" + limiteDescargas + " descargas usadas)");
    }

    public void descargarPlaylist(Playlist playlist) {
        System.out.println("📥 Descargando playlist \"" + playlist.getNombre() + "\"...");
        int descargadas = 0;
        for (Cancion c : playlist.getCanciones()) {
            if (descargas.size() < limiteDescargas) {
                boolean yaDescargada = false;
                for (Cancion d : descargas) {
                    if (d.getId().equals(c.getId())) { yaDescargada = true; break; }
                }
                if (!yaDescargada) {
                    descargas.add(c);
                    c.setDisponibleOffline(true);
                    descargadas++;
                }
            }
        }
        System.out.println("✅ " + descargadas + " cancion(es) descargada(s).");
    }

    public void verDescargas() {
        if (descargas.isEmpty()) {
            System.out.println("No tienes canciones descargadas.");
            return;
        }
        System.out.println("\n📥 Canciones descargadas (" + descargas.size() + "/" + limiteDescargas + "):");
        System.out.println("──────────────────────────────────");
        for (int i = 0; i < descargas.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + descargas.get(i).toString());
        }
        System.out.println("──────────────────────────────────");
    }

    public void eliminarDescarga(Cancion cancion) {
        boolean eliminada = descargas.removeIf(c -> c.getId().equals(cancion.getId()));
        if (eliminada) {
            cancion.setDisponibleOffline(false);
            System.out.println("🗑  \"" + cancion.getTitulo() + "\" eliminada de descargas.");
        } else {
            System.out.println("⚠  La canción no estaba en descargas.");
        }
    }

    // ─── Compartir Playlist ───────────────────────────────────────

    public void compartirPlaylist(Playlist playlist) {
        if (playlist == null) {
            System.out.println("❌ Playlist inválida.");
            return;
        }
        System.out.println("🔗 Compartiendo playlist \"" + playlist.getNombre() + "\"...");
        System.out.println("   Link generado: https://musicapp.co/playlist/" + playlist.getId());
        System.out.println("   (" + playlist.getCanciones().size() + " canciones | Propietario: " + nombre + ")");
    }

    // ─── Suscripción ─────────────────────────────────────────────

    public void cancelarSuscripcion() {
        this.autoRenovacion = false;
        System.out.println("⚠  Suscripción Premium cancelada.");
        System.out.println("   Seguirás disfrutando de los beneficios hasta el " + fechaSuscripcion + ".");
        System.out.println("   Después pasarás a cuenta GRATUITA.");
    }

    public void activarAutoRenovacion() {
        this.autoRenovacion = true;
        System.out.println("✅ Auto-renovación activada.");
    }

    // ─── Perfil ──────────────────────────────────────────────────

    @Override
    public void mostrarPerfil() {
        System.out.println("\n╔════════════ PERFIL PREMIUM ══════════╗");
        System.out.println("  👑 Nombre:      " + nombre);
        System.out.println("  📧 Correo:      " + correo);
        System.out.println("  🏷  Tipo:        " + tipoCuenta);
        System.out.println("  📅 Suscripción: " + fechaSuscripcion);
        System.out.println("  🎧 Calidad:     " + calidadAudio);
        System.out.println("  📥 Descargas:   " + descargas.size() + "/" + limiteDescargas);
        System.out.println("  🔁 AutoRenov.:  " + (autoRenovacion ? "Sí" : "No"));
        System.out.println("  📂 Playlists:   " + playlists.size());
        System.out.println("  ❤  Favoritos:   " + favoritos.size());
        System.out.println("╚═══════════════════════════════════════╝");
    }

    // Getters
    public String getFechaSuscripcion() { return fechaSuscripcion; }
    public String getCalidadAudio() { return calidadAudio; }
    public int getLimiteDescargas() { return limiteDescargas; }
    public ArrayList<Cancion> getDescargas() { return descargas; }
    public boolean isAutoRenovacion() { return autoRenovacion; }
}
