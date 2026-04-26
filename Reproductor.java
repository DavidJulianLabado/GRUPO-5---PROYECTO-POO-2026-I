import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que actúa como núcleo de procesamiento de audio.
 * Implementa RF-01 (Gestión de Reproducción Multimedia) y RF-02 (Control de Audio).
 */
public class Reproductor {

    // Atributos
    private EstadoReproductor estado;
    private int volumen; // 0 a 100
    private Cancion cancionActual;
    private ArrayList<Cancion> cola; // fila de reproducción
    private int indiceActual;
    private boolean modoAleatorio;
    private boolean modoRepeticion; // repetir canción actual
    private boolean modoRepetirLista; // repetir toda la cola
    private Historial historial;

    // Constructor
    public Reproductor() {
        this.estado = EstadoReproductor.DETENIDO;
        this.volumen = 50;
        this.cancionActual = null;
        this.cola = new ArrayList<>();
        this.indiceActual = 0;
        this.modoAleatorio = false;
        this.modoRepeticion = false;
        this.modoRepetirLista = false;
        this.historial = new Historial();
    }

    // ─── Controles de Reproducción ───────────────────────────────

    public void reproducir() {
        if (cola.isEmpty()) {
            System.out.println("⚠  La cola de reproducción está vacía.");
            return;
        }
        if (cancionActual == null) {
            indiceActual = 0;
            cancionActual = cola.get(indiceActual);
        }
        estado = EstadoReproductor.REPRODUCIENDO;
        cancionActual.reproducir();
        historial.agregarCancion(cancionActual);
        mostrarEstado();
    }

    public void pausar() {
        if (estado == EstadoReproductor.REPRODUCIENDO) {
            estado = EstadoReproductor.PAUSADO;
            cancionActual.pausar();
        } else if (estado == EstadoReproductor.PAUSADO) {
            estado = EstadoReproductor.REPRODUCIENDO;
            System.out.println("▶  Reanudando: \"" + cancionActual.getTitulo() + "\"");
        } else {
            System.out.println("⚠  No hay nada en reproducción.");
        }
        mostrarEstado();
    }

    public void siguiente() {
        if (cola.isEmpty()) {
            System.out.println("⚠  La cola está vacía.");
            return;
        }
        if (modoAleatorio) {
            indiceActual = (int) (Math.random() * cola.size());
        } else {
            indiceActual++;
            if (indiceActual >= cola.size()) {
                if (modoRepetirLista) {
                    indiceActual = 0;
                    System.out.println("🔁 Reiniciando lista...");
                } else {
                    estado = EstadoReproductor.DETENIDO;
                    cancionActual = null;
                    System.out.println("⏹  Fin de la cola de reproducción.");
                    return;
                }
            }
        }
        cancionActual = cola.get(indiceActual);
        estado = EstadoReproductor.REPRODUCIENDO;
        System.out.println("⏭  Siguiente canción:");
        cancionActual.reproducir();
        historial.agregarCancion(cancionActual);
        mostrarEstado();
    }

    public void anterior() {
        if (cola.isEmpty()) {
            System.out.println("⚠  La cola está vacía.");
            return;
        }
        indiceActual--;
        if (indiceActual < 0) {
            indiceActual = 0;
            System.out.println("⚠  Ya estás en la primera canción.");
        }
        cancionActual = cola.get(indiceActual);
        estado = EstadoReproductor.REPRODUCIENDO;
        System.out.println("⏮  Canción anterior:");
        cancionActual.reproducir();
        historial.agregarCancion(cancionActual);
        mostrarEstado();
    }

    public void detener() {
        estado = EstadoReproductor.DETENIDO;
        cancionActual = null;
        System.out.println("⏹  Reproducción detenida.");
    }

    // ─── Control de Volumen (RF-02) ──────────────────────────────

    public void ajustarVolumen(int nuevoVolumen) {
        if (nuevoVolumen < 0 || nuevoVolumen > 100) {
            System.out.println("❌ El volumen debe estar entre 0 y 100.");
            return;
        }
        this.volumen = nuevoVolumen;
        String barraVolumen = generarBarraVolumen();
        System.out.println("🔊 Volumen: " + barraVolumen + " " + volumen + "%");
    }

    private String generarBarraVolumen() {
        int llenos = volumen / 10;
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            barra.append(i < llenos ? "█" : "░");
        }
        barra.append("]");
        return barra.toString();
    }

    // ─── Gestión de Cola (RF-04) ─────────────────────────────────

    public void agregarACola(Cancion cancion) {
        cola.add(cancion);
        System.out.println("➕ \"" + cancion.getTitulo() + "\" añadida a la cola. (Total: " + cola.size() + ")");
    }

    public void agregarPlaylistACola(Playlist playlist) {
        if (playlist.estaVacia()) {
            System.out.println("⚠  La playlist está vacía.");
            return;
        }
        for (Cancion c : playlist.getCanciones()) {
            cola.add(c);
        }
        System.out.println("✅ Playlist \"" + playlist.getNombre() + "\" añadida a la cola ("
                + playlist.getCanciones().size() + " canciones).");
    }

    public void limpiarCola() {
        cola.clear();
        cancionActual = null;
        indiceActual = 0;
        estado = EstadoReproductor.DETENIDO;
        System.out.println("🗑  Cola de reproducción limpiada.");
    }

    public void mostrarCola() {
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }
        System.out.println("\n🎵 Cola de Reproducción (" + cola.size() + " canciones):");
        System.out.println("─────────────────────────────────────────────────");
        for (int i = 0; i < cola.size(); i++) {
            String marcador = (i == indiceActual && estado != EstadoReproductor.DETENIDO) ? "▶ " : "  ";
            System.out.printf("%s%2d. %s%n", marcador, (i + 1), cola.get(i).toString());
        }
        System.out.println("─────────────────────────────────────────────────");
    }

    // ─── Modos ───────────────────────────────────────────────────

    public void toggleModoAleatorio() {
        modoAleatorio = !modoAleatorio;
        System.out.println("🔀 Modo aleatorio: " + (modoAleatorio ? "ACTIVADO" : "DESACTIVADO"));
    }

    public void toggleRepeticion() {
        modoRepeticion = !modoRepeticion;
        System.out.println("🔂 Repetir canción: " + (modoRepeticion ? "ACTIVADO" : "DESACTIVADO"));
    }

    public void toggleRepetirLista() {
        modoRepetirLista = !modoRepetirLista;
        System.out.println("🔁 Repetir lista: " + (modoRepetirLista ? "ACTIVADO" : "DESACTIVADO"));
    }

    // ─── Visualización ───────────────────────────────────────────

    public void mostrarEstado() {
        String estadoStr;
        switch (estado) {
            case REPRODUCIENDO: estadoStr = "▶ REPRODUCIENDO"; break;
            case PAUSADO:       estadoStr = "⏸ PAUSADO";       break;
            default:            estadoStr = "⏹ DETENIDO";      break;
        }
        System.out.println("  Estado: " + estadoStr
                + " | Vol: " + volumen + "%"
                + " | 🔀 " + (modoAleatorio ? "ON" : "OFF")
                + " | 🔂 " + (modoRepeticion ? "ON" : "OFF"));
    }

    public void mostrarCancionActual() {
        if (cancionActual == null) {
            System.out.println("No hay ninguna canción en reproducción.");
        } else {
            cancionActual.mostrarInfo();
            mostrarEstado();
        }
    }

    // Getters
    public EstadoReproductor getEstado() { return estado; }
    public int getVolumen() { return volumen; }
    public Cancion getCancionActual() { return cancionActual; }
    public ArrayList<Cancion> getCola() { return cola; }
    public boolean isModoAleatorio() { return modoAleatorio; }
    public Historial getHistorial() { return historial; }
}
