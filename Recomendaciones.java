import java.util.ArrayList;

/**
 * Clase que genera recomendaciones personalizadas basadas en
 * el historial y favoritos del usuario.
 */
public class Recomendaciones {

    // Atributos
    private ArrayList<Cancion> patron;   // canciones del historial/favoritos
    private Usuario usuario;
    private double similitud;            // porcentaje de similitud calculado

    // Constructor
    public Recomendaciones(Usuario usuario) {
        this.usuario = usuario;
        this.patron = new ArrayList<>();
        this.similitud = 0.0;
        actualizarPatron();
    }

    // ─── Actualización del patrón ─────────────────────────────────

    public void actualizarPatron() {
        patron.clear();
        // El patrón se basa en favoritos + historial reciente
        for (Cancion c : usuario.getFavoritos()) {
            patron.add(c);
        }
        for (Cancion c : usuario.getReproductor().getHistorial().getCanciones()) {
            boolean yaEsta = false;
            for (Cancion p : patron) {
                if (p.getId().equals(c.getId())) { yaEsta = true; break; }
            }
            if (!yaEsta) patron.add(c);
        }
    }

    // ─── Comparación ─────────────────────────────────────────────

    public double compararCanciones(Cancion a, Cancion b) {
        double score = 0.0;
        // Mismo género: +50 puntos
        if (a.getGenero().equalsIgnoreCase(b.getGenero())) score += 50.0;
        // Artista en común: +40 puntos
        for (String artistaA : a.getArtistas()) {
            for (String artistaB : b.getArtistas()) {
                if (artistaA.equalsIgnoreCase(artistaB)) score += 40.0;
            }
        }
        // Duración similar (diferencia menor a 60 seg): +10 puntos
        if (Math.abs(a.getDuracion() - b.getDuracion()) < 60) score += 10.0;
        return score;
    }

    // ─── Recomendación principal ─────────────────────────────────

    public ArrayList<Cancion> recomendar(ArrayList<Cancion> catalogo, int cantidad) {
        actualizarPatron();
        ArrayList<Cancion> recomendadas = new ArrayList<>();

        if (patron.isEmpty()) {
            System.out.println("💡 Escucha más canciones para recibir recomendaciones personalizadas.");
            // Sin patrón: recomendar las más populares
            ArrayList<Cancion> populares = new ArrayList<>(catalogo);
            populares.sort((a, b) -> b.getReproducciones() - a.getReproducciones());
            for (int i = 0; i < Math.min(cantidad, populares.size()); i++) {
                recomendadas.add(populares.get(i));
            }
            return recomendadas;
        }

        // Calcular score de cada canción del catálogo
        ArrayList<double[]> scores = new ArrayList<>(); // [índice, score]
        for (int i = 0; i < catalogo.size(); i++) {
            Cancion candidata = catalogo.get(i);
            // Saltar si ya está en el patrón
            boolean enPatron = false;
            for (Cancion p : patron) {
                if (p.getId().equals(candidata.getId())) { enPatron = true; break; }
            }
            if (enPatron) continue;

            double totalScore = 0.0;
            for (Cancion p : patron) {
                totalScore += compararCanciones(candidata, p);
            }
            scores.add(new double[]{i, totalScore});
        }

        // Ordenar por mayor score
        scores.sort((a, b) -> Double.compare(b[1], a[1]));

        int agregadas = 0;
        for (double[] entry : scores) {
            if (agregadas >= cantidad) break;
            recomendadas.add(catalogo.get((int) entry[0]));
            agregadas++;
        }

        if (!scores.isEmpty()) {
            similitud = scores.get(0)[1];
        }

        return recomendadas;
    }

    public void sugerirCanciones(ArrayList<Cancion> catalogo) {
        System.out.println("\n✨ Recomendaciones para " + usuario.getNombre() + ":");
        System.out.println("──────────────────────────────────");
        ArrayList<Cancion> sugerencias = recomendar(catalogo, 5);
        if (sugerencias.isEmpty()) {
            System.out.println("  No hay suficientes canciones en el catálogo.");
        } else {
            for (int i = 0; i < sugerencias.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + sugerencias.get(i).toString());
            }
        }
        System.out.println("──────────────────────────────────");
    }

    public void recomendaciones(ArrayList<Cancion> catalogo) {
        sugerirCanciones(catalogo);
    }

    // Getters
    public double getSimilitud() { return similitud; }
    public ArrayList<Cancion> getPatron() { return patron; }
}
