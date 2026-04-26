import java.util.ArrayList;

/**
 * Clase que representa al administrador de la plataforma.
 * Tiene acceso a funciones de moderación y gestión de contenido.
 * Hereda de Cuenta.
 */
public class Administrador extends Cuenta {

    // Atributos
    private int nivelAcceso; // 1=moderador, 2=admin, 3=superadmin
    private String fechaRegistro;

    // Constructor
    public Administrador(String id, String nombre, String correo,
                         String contrasena, int nivelAcceso, String fechaRegistro) {
        super(id, nombre, correo, contrasena);
        this.nivelAcceso = nivelAcceso;
        this.fechaRegistro = fechaRegistro;
    }

    // ─── Gestión de Contenido ─────────────────────────────────────

    public void quitarCancion(ArrayList<Cancion> catalogo, String idCancion) {
        boolean eliminada = false;
        for (int i = 0; i < catalogo.size(); i++) {
            if (catalogo.get(i).getId().equals(idCancion)) {
                System.out.println("🗑  [ADMIN] Canción \"" + catalogo.get(i).getTitulo()
                        + "\" eliminada del catálogo.");
                catalogo.remove(i);
                eliminada = true;
                break;
            }
        }
        if (!eliminada) {
            System.out.println("❌ No se encontró ninguna canción con ID: " + idCancion);
        }
    }

    public void eliminarUsuario(ArrayList<Usuario> usuarios, String idUsuario) {
        boolean eliminado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(idUsuario)) {
                System.out.println("🗑  [ADMIN] Usuario \"" + usuarios.get(i).getNombre()
                        + "\" eliminado de la plataforma.");
                usuarios.remove(i);
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            System.out.println("❌ No se encontró ningún usuario con ID: " + idUsuario);
        }
    }

    public void validarArtista(Artista artista) {
        System.out.println("✅ [ADMIN] Artista \"" + artista.getNombre()
                + "\" validado y verificado en la plataforma. ✓");
    }

    public void moderarContenido(String descripcion) {
        System.out.println("🔍 [ADMIN] Moderando contenido: \"" + descripcion + "\"...");
        System.out.println("   Acción registrada. Contenido revisado por " + nombre + ".");
    }

    // ─── Panel Admin ─────────────────────────────────────────────

    public void mostrarPanelAdmin(ArrayList<Cancion> catalogo, ArrayList<Usuario> usuarios,
                                   ArrayList<Artista> artistas) {
        System.out.println("\n╔═════════════ PANEL ADMINISTRADOR ═════════════╗");
        System.out.println("  🛡  Admin:      " + nombre);
        System.out.println("  🔑 Nivel:       " + obtenerNivelTexto());
        System.out.println("  📅 Registro:    " + fechaRegistro);
        System.out.println("  ─────────────────────────────────────────────");
        System.out.println("  🎵 Canciones en catálogo: " + catalogo.size());
        System.out.println("  👤 Usuarios registrados:  " + usuarios.size());
        System.out.println("  🎤 Artistas registrados:  " + artistas.size());
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    private String obtenerNivelTexto() {
        switch (nivelAcceso) {
            case 1: return "Moderador";
            case 2: return "Administrador";
            case 3: return "Super Administrador";
            default: return "Sin nivel";
        }
    }

    @Override
    public void mostrarPerfil() {
        System.out.println("\n╔══════════ PERFIL ADMINISTRADOR ═══════════╗");
        System.out.println("  🛡  Nombre:    " + nombre);
        System.out.println("  📧 Correo:    " + correo);
        System.out.println("  🔑 Nivel:     " + obtenerNivelTexto() + " (Nivel " + nivelAcceso + ")");
        System.out.println("  📅 Registro:  " + fechaRegistro);
        System.out.println("╚════════════════════════════════════════════╝");
    }

    // Getters
    public int getNivelAcceso() { return nivelAcceso; }
    public String getFechaRegistro() { return fechaRegistro; }
}
