import java.util.ArrayList;

/**
 * Clase abstracta base para todos los tipos de cuenta en la plataforma.
 * Define los atributos y métodos comunes a Usuario, Artista y Administrador.
 */
public abstract class Cuenta {

    // Atributos (encapsulados)
    protected String id;
    protected String nombre;
    protected String correo;
    protected String contrasena;

    // Referencia al catálogo global de canciones
    protected ArrayList<Cancion> catalogoGlobal;

    // Constructor
    public Cuenta(String id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.catalogoGlobal = new ArrayList<>();
    }

    // Métodos concretos compartidos

    /**
     * Valida credenciales de inicio de sesión.
     */
    public boolean iniciarSesion(String correoIngresado, String contrasenaIngresada) {
        if (this.correo.equals(correoIngresado) && this.contrasena.equals(contrasenaIngresada)) {
            System.out.println("✅ Sesión iniciada correctamente. ¡Bienvenido/a, " + nombre + "!");
            return true;
        } else {
            System.out.println("❌ Correo o contraseña incorrectos.");
            return false;
        }
    }

    /**
     * Busca canciones en el catálogo global por título (RF-03).
     */
    public ArrayList<Cancion> buscarCancion(String titulo) {
        ArrayList<Cancion> resultados = new ArrayList<>();
        for (Cancion c : catalogoGlobal) {
            if (c.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(c);
            }
        }
        if (resultados.isEmpty()) {
            System.out.println("🔍 No se encontraron canciones con \"" + titulo + "\".");
        } else {
            System.out.println("🔍 Se encontraron " + resultados.size() + " resultado(s):");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + resultados.get(i).toString());
            }
        }
        return resultados;
    }

    /**
     * Asigna el catálogo global de la plataforma a esta cuenta.
     */
    public void setCatalogoGlobal(ArrayList<Cancion> catalogo) {
        this.catalogoGlobal = catalogo;
    }

    // Método abstracto: cada tipo de cuenta se presenta diferente
    public abstract void mostrarPerfil();

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return nombre + " <" + correo + ">";
    }
}
