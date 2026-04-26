import java.util.ArrayList;
import java.util.Scanner;

/**
 * ╔═══════════════════════════════════════════════════════════╗
 *  PLATAFORMA DE MÚSICA - Programación Orientada a Objetos
 *  Universidad Nacional de Colombia - Grupo 5 - 2026
 * ╚═══════════════════════════════════════════════════════════╝
 *
 * Clase principal que inicializa la plataforma y lanza el menú
 * interactivo de consola. Actúa como controlador central.
 */
public class Main {

    // ─── Datos globales de la plataforma ──────────────────────────
    static ArrayList<Cancion>       catalogo  = new ArrayList<>();
    static ArrayList<Usuario>       usuarios  = new ArrayList<>();
    static ArrayList<Artista>       artistas  = new ArrayList<>();
    static ArrayList<Administrador> admins    = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    // Sesión activa
    static Usuario       usuarioActivo = null;
    static Artista       artistaActivo = null;
    static Administrador adminActivo   = null;

    // ─── Punto de entrada ─────────────────────────────────────────
    public static void main(String[] args) {
        cargarDatosDemostracion();
        mostrarBienvenida();

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenuPrincipal();
            int opcion = leerInt();
            switch (opcion) {
                case 1: menuIniciarSesion();         break;
                case 2: menuRegistrarse();           break;
                case 3: menuCatalogoPublico();       break;
                case 0:
                    System.out.println("\n👋 ¡Hasta pronto! Gracias por usar MusicApp.\n");
                    ejecutando = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
        sc.close();
    }

    // ════════════════════════════════════════════════════════════
    //  DATOS DE DEMOSTRACIÓN
    // ════════════════════════════════════════════════════════════
    static void cargarDatosDemostracion() {
        // ── Artistas ──
        Artista a1 = new Artista("A01", "Carlos Vives",
                "cvives@music.co", "pass123", "Vallenato",
                "Ícono del vallenato colombiano.");
        Artista a2 = new Artista("A02", "Shakira",
                "shakira@music.co", "pass456", "Pop/Rock",
                "Artista barranquillera de fama mundial.");
        Artista a3 = new Artista("A03", "J Balvin",
                "balvin@music.co", "pass789", "Reggaeton",
                "Rey del reggaeton urbano colombiano.");

        artistas.add(a1);
        artistas.add(a2);
        artistas.add(a3);

        // Asociar catálogo global a artistas para que sus canciones queden en el catálogo
        a1.setCatalogoGlobal(catalogo);
        a2.setCatalogoGlobal(catalogo);
        a3.setCatalogoGlobal(catalogo);

        // ── Canciones (subidas por artistas) ──
        Cancion c1  = a1.subirCancion("La Bicicleta",      237, "Vallenato");
        Cancion c2  = a1.subirCancion("Frío Frío",         198, "Vallenato");
        Cancion c3  = a2.subirCancion("Hips Don't Lie",    218, "Pop");
        Cancion c4  = a2.subirCancion("Waka Waka",         213, "Pop");
        Cancion c5  = a2.subirCancion("She Wolf",          192, "Pop/Rock");
        Cancion c6  = a3.subirCancion("Mi Gente",          182, "Reggaeton");
        Cancion c7  = a3.subirCancion("Safari",            222, "Reggaeton");
        Cancion c8  = a3.subirCancion("Con Calma",         200, "Reggaeton");

        // Canciones extra sin artista registrado
        Cancion c9  = new Cancion("C99", "Bohemian Rhapsody", 354, "Rock",    "Queen");
        Cancion c10 = new Cancion("C10", "Blinding Lights",   200, "Synth-pop","The Weeknd");
        catalogo.add(c9);
        catalogo.add(c10);

        // ── Álbumes ──
        Album al1 = a1.crearAlbum("Corazón Profundo", 2013);
        al1.agregarCancion(c1);
        al1.agregarCancion(c2);

        Album al2 = a2.crearAlbum("She Wolf", 2009);
        al2.agregarCancion(c3);
        al2.agregarCancion(c5);

        // ── Usuarios ──
        Usuario u1 = new Usuario("U01", "Ana García", "ana@mail.com", "1234");
        u1.setCatalogoGlobal(catalogo);
        u1.darMeGusta(c1);
        u1.darMeGusta(c6);
        u1.getReproductor().agregarACola(c1);
        u1.getReproductor().agregarACola(c6);
        u1.getReproductor().agregarACola(c9);
        Playlist pl = u1.crearPlaylist("Mis Favoritos");
        pl.agregarCancion(c1);
        pl.agregarCancion(c3);
        pl.agregarCancion(c6);
        usuarios.add(u1);

        UsuarioPremium u2 = new UsuarioPremium("U02", "Juan Pérez",
                "juan@mail.com", "5678", "2025-12-31");
        u2.setCatalogoGlobal(catalogo);
        u2.darMeGusta(c4);
        u2.descargarCancion(c4);
        u2.descargarCancion(c7);
        usuarios.add(u2);

        // ── Administrador ──
        Administrador admin = new Administrador("ADM1", "David Herrera",
                "admin@music.co", "admin2026", 3, "2024-01-01");
        admin.setCatalogoGlobal(catalogo);
        admins.add(admin);

        System.out.println("\n✅ Datos de demostración cargados correctamente.\n");
    }

    // ════════════════════════════════════════════════════════════
    //  MENÚS PRINCIPALES
    // ════════════════════════════════════════════════════════════

    static void mostrarBienvenida() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║          🎵  PLATAFORMA DE MÚSICA  🎵            ║");
        System.out.println("║     Universidad Nacional de Colombia - 2026       ║");
        System.out.println("║         Programación Orientada a Objetos          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    static void mostrarMenuPrincipal() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         MENÚ PRINCIPAL           │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Iniciar sesión               │");
        System.out.println("│  2. Registrarse                  │");
        System.out.println("│  3. Ver catálogo (sin sesión)    │");
        System.out.println("│  0. Salir                        │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("  Opción: ");
    }

    // ════════════════════════════════════════════════════════════
    //  INICIO DE SESIÓN
    // ════════════════════════════════════════════════════════════

    static void menuIniciarSesion() {
        System.out.println("\n─── INICIO DE SESIÓN ───");
        System.out.println("  1. Usuario");
        System.out.println("  2. Artista");
        System.out.println("  3. Administrador");
        System.out.println("  0. Volver");
        System.out.print("  Tipo de cuenta: ");
        int tipo = leerInt();

        if (tipo == 0) return;

        System.out.print("  Correo: ");
        String correo = sc.nextLine().trim();
        System.out.print("  Contraseña: ");
        String pass = sc.nextLine().trim();

        switch (tipo) {
            case 1:
                for (Usuario u : usuarios) {
                    if (u.iniciarSesion(correo, pass)) {
                        usuarioActivo = u;
                        menuUsuario();
                        usuarioActivo = null;
                        return;
                    }
                }
                System.out.println("❌ No se encontró ningún usuario con esas credenciales.");
                break;
            case 2:
                for (Artista a : artistas) {
                    if (a.iniciarSesion(correo, pass)) {
                        artistaActivo = a;
                        menuArtista();
                        artistaActivo = null;
                        return;
                    }
                }
                System.out.println("❌ No se encontró ningún artista con esas credenciales.");
                break;
            case 3:
                for (Administrador ad : admins) {
                    if (ad.iniciarSesion(correo, pass)) {
                        adminActivo = ad;
                        menuAdministrador();
                        adminActivo = null;
                        return;
                    }
                }
                System.out.println("❌ No se encontró ningún administrador con esas credenciales.");
                break;
            default:
                System.out.println("❌ Tipo de cuenta no válido.");
        }
    }

    // ════════════════════════════════════════════════════════════
    //  REGISTRO
    // ════════════════════════════════════════════════════════════

    static void menuRegistrarse() {
        System.out.println("\n─── REGISTRO DE NUEVO USUARIO ───");
        System.out.println("  1. Usuario gratuito");
        System.out.println("  2. Usuario Premium");
        System.out.println("  0. Volver");
        System.out.print("  Opción: ");
        int op = leerInt();
        if (op == 0) return;

        System.out.print("  Nombre completo: ");
        String nombre = sc.nextLine().trim();
        System.out.print("  Correo:          ");
        String correo = sc.nextLine().trim();
        System.out.print("  Contraseña:      ");
        String pass = sc.nextLine().trim();

        // Validar correo único
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println("❌ Ya existe una cuenta con ese correo.");
                return;
            }
        }

        String nuevoId = "U" + String.format("%02d", usuarios.size() + 1);

        if (op == 1) {
            Usuario nuevo = new Usuario(nuevoId, nombre, correo, pass);
            nuevo.setCatalogoGlobal(catalogo);
            nuevo.registrarse();
            usuarios.add(nuevo);
        } else if (op == 2) {
            UsuarioPremium nuevo = new UsuarioPremium(nuevoId, nombre, correo, pass, "2027-04-25");
            nuevo.setCatalogoGlobal(catalogo);
            nuevo.registrarse();
            usuarios.add(nuevo);
        }
    }

    // ════════════════════════════════════════════════════════════
    //  MENÚ USUARIO
    // ════════════════════════════════════════════════════════════

    static void menuUsuario() {
        boolean activo = true;
        while (activo) {
            boolean esPremium = (usuarioActivo instanceof UsuarioPremium);
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│  🎵 MENÚ USUARIO" + (esPremium ? " 👑 PREMIUM" : "        ") + "              │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│  REPRODUCTOR                             │");
            System.out.println("│   1. Ver/controlar reproductor           │");
            System.out.println("│   2. Buscar canción en catálogo          │");
            System.out.println("│   3. Ver catálogo completo               │");
            System.out.println("│  PLAYLISTS                               │");
            System.out.println("│   4. Gestionar mis playlists             │");
            System.out.println("│   5. Ver favoritos (❤)                  │");
            System.out.println("│   6. Ver historial                       │");
            System.out.println("│   7. Recomendaciones ✨                  │");
            if (esPremium) {
                System.out.println("│  PREMIUM                                 │");
                System.out.println("│   8. Descargas offline 📥               │");
                System.out.println("│   9. Compartir playlist 🔗              │");
            }
            System.out.println("│  CUENTA                                  │");
            System.out.println("│  10. Ver mi perfil                       │");
            System.out.println("│   0. Cerrar sesión                       │");
            System.out.println("└──────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1:  menuReproductor();                  break;
                case 2:  menuBuscarCancion();                break;
                case 3:  mostrarCatalogoCompleto();          break;
                case 4:  menuPlaylists();                    break;
                case 5:  usuarioActivo.verFavoritos();       break;
                case 6:  usuarioActivo.getReproductor().getHistorial().generarResumen(); break;
                case 7:  menuRecomendaciones();              break;
                case 8:
                    if (esPremium) menuDescargas();
                    else System.out.println("❌ Función exclusiva Premium.");
                    break;
                case 9:
                    if (esPremium) menuCompartirPlaylist();
                    else System.out.println("❌ Función exclusiva Premium.");
                    break;
                case 10: usuarioActivo.mostrarPerfil();      break;
                case 0:
                    System.out.println("👋 Sesión cerrada. ¡Hasta pronto, " + usuarioActivo.getNombre() + "!");
                    activo = false;
                    break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ─── Submenú: Reproductor ────────────────────────────────────

    static void menuReproductor() {
        Reproductor rep = usuarioActivo.getReproductor();
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌─────────────── REPRODUCTOR ────────────────┐");
            rep.mostrarCancionActual();
            System.out.println("├─────────────────────────────────────────────┤");
            System.out.println("│  1. ▶  Reproducir / Reanudar                │");
            System.out.println("│  2. ⏸  Pausar                               │");
            System.out.println("│  3. ⏭  Siguiente                            │");
            System.out.println("│  4. ⏮  Anterior                             │");
            System.out.println("│  5. ⏹  Detener                              │");
            System.out.println("│  6. 🔊 Ajustar volumen                      │");
            System.out.println("│  7. 🔀 Activar/desactivar aleatorio          │");
            System.out.println("│  8. 🔂 Activar/desactivar repetir canción    │");
            System.out.println("│  9. 🔁 Activar/desactivar repetir lista      │");
            System.out.println("│ 10. 📋 Ver cola de reproducción              │");
            System.out.println("│ 11. ➕ Agregar canción a la cola             │");
            System.out.println("│ 12. 🗑  Limpiar cola                         │");
            System.out.println("│  0. Volver                                   │");
            System.out.println("└─────────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1:  rep.reproducir();                  break;
                case 2:  rep.pausar();                      break;
                case 3:  rep.siguiente();                   break;
                case 4:  rep.anterior();                    break;
                case 5:  rep.detener();                     break;
                case 6:
                    System.out.print("  Nuevo volumen (0-100): ");
                    int vol = leerInt();
                    rep.ajustarVolumen(vol);
                    break;
                case 7:  rep.toggleModoAleatorio();         break;
                case 8:  rep.toggleRepeticion();            break;
                case 9:  rep.toggleRepetirLista();          break;
                case 10: rep.mostrarCola();                 break;
                case 11: menuAgregarACola(rep);             break;
                case 12: rep.limpiarCola();                 break;
                case 0:  activo = false;                    break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    static void menuAgregarACola(Reproductor rep) {
        mostrarCatalogoCompleto();
        System.out.print("  Número de canción a añadir (0 para cancelar): ");
        int num = leerInt();
        if (num > 0 && num <= catalogo.size()) {
            Cancion seleccionada = catalogo.get(num - 1);
            rep.agregarACola(seleccionada);
        } else if (num != 0) {
            System.out.println("❌ Número inválido.");
        }
    }

    // ─── Submenú: Búsqueda ───────────────────────────────────────

    static void menuBuscarCancion() {
        System.out.print("\n🔍 Ingresa el título a buscar: ");
        String titulo = sc.nextLine().trim();
        ArrayList<Cancion> resultados = usuarioActivo.buscarCancion(titulo);

        if (!resultados.isEmpty()) {
            System.out.println("\n¿Qué deseas hacer con los resultados?");
            System.out.println("  1. Añadir una canción a la cola");
            System.out.println("  2. Dar Like ❤ a una canción");
            System.out.println("  3. Añadir a una playlist");
            System.out.println("  0. Volver");
            System.out.print("  Opción: ");
            int op = leerInt();

            if (op >= 1 && op <= 3) {
                System.out.print("  Número de canción (1-" + resultados.size() + "): ");
                int num = leerInt();
                if (num < 1 || num > resultados.size()) {
                    System.out.println("❌ Número inválido.");
                    return;
                }
                Cancion elegida = resultados.get(num - 1);
                switch (op) {
                    case 1: usuarioActivo.getReproductor().agregarACola(elegida); break;
                    case 2: usuarioActivo.darMeGusta(elegida); break;
                    case 3: menuAgregarAPlaylist(elegida); break;
                }
            }
        }
    }

    static void menuAgregarAPlaylist(Cancion cancion) {
        usuarioActivo.listarPlaylists();
        if (usuarioActivo.getPlaylists().isEmpty()) {
            System.out.println("No tienes playlists. Crea una primero.");
            return;
        }
        System.out.print("  Número de playlist: ");
        int num = leerInt();
        if (num < 1 || num > usuarioActivo.getPlaylists().size()) {
            System.out.println("❌ Número inválido.");
            return;
        }
        usuarioActivo.getPlaylists().get(num - 1).agregarCancion(cancion);
    }

    // ─── Submenú: Playlists ──────────────────────────────────────

    static void menuPlaylists() {
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌────────────── MIS PLAYLISTS ───────────────┐");
            System.out.println("│  1. Ver mis playlists                        │");
            System.out.println("│  2. Crear playlist                           │");
            System.out.println("│  3. Renombrar playlist                       │");
            System.out.println("│  4. Eliminar playlist                        │");
            System.out.println("│  5. Abrir playlist                           │");
            System.out.println("│  0. Volver                                   │");
            System.out.println("└─────────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1: usuarioActivo.listarPlaylists();  break;
                case 2:
                    System.out.print("  Nombre de la nueva playlist: ");
                    String nombre = sc.nextLine().trim();
                    if (!nombre.isEmpty()) usuarioActivo.crearPlaylist(nombre);
                    break;
                case 3:
                    usuarioActivo.listarPlaylists();
                    if (!usuarioActivo.getPlaylists().isEmpty()) {
                        System.out.print("  Número de playlist a renombrar: ");
                        int idx = leerInt() - 1;
                        System.out.print("  Nuevo nombre: ");
                        String nuevoNombre = sc.nextLine().trim();
                        usuarioActivo.renombrarPlaylist(idx, nuevoNombre);
                    }
                    break;
                case 4:
                    usuarioActivo.listarPlaylists();
                    if (!usuarioActivo.getPlaylists().isEmpty()) {
                        System.out.print("  Número de playlist a eliminar: ");
                        int idx = leerInt() - 1;
                        usuarioActivo.eliminarPlaylist(idx);
                    }
                    break;
                case 5:  menuAbrirPlaylist();  break;
                case 0:  activo = false;       break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    static void menuAbrirPlaylist() {
        usuarioActivo.listarPlaylists();
        if (usuarioActivo.getPlaylists().isEmpty()) return;
        System.out.print("  Número de playlist: ");
        int idx = leerInt() - 1;
        if (idx < 0 || idx >= usuarioActivo.getPlaylists().size()) {
            System.out.println("❌ Número inválido.");
            return;
        }
        Playlist pl = usuarioActivo.getPlaylists().get(idx);
        boolean activo = true;
        while (activo) {
            pl.listarCanciones();
            System.out.println("\n  1. Añadir canción del catálogo");
            System.out.println("  2. Eliminar canción");
            System.out.println("  3. Buscar en esta playlist");
            System.out.println("  4. Ordenar playlist");
            System.out.println("  5. Cargar en cola del reproductor");
            System.out.println("  0. Volver");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1:
                    mostrarCatalogoCompleto();
                    System.out.print("  Número de canción a añadir: ");
                    int numC = leerInt();
                    if (numC >= 1 && numC <= catalogo.size()) {
                        pl.agregarCancion(catalogo.get(numC - 1));
                    }
                    break;
                case 2:
                    System.out.print("  Título de canción a eliminar: ");
                    String tit = sc.nextLine().trim();
                    pl.eliminarCancion(tit);
                    break;
                case 3:
                    System.out.print("  Buscar por título: ");
                    String busq = sc.nextLine().trim();
                    ArrayList<Cancion> res = pl.buscarPorTitulo(busq);
                    if (res.isEmpty()) System.out.println("No se encontraron resultados.");
                    else res.forEach(c -> System.out.println("  • " + c.toString()));
                    break;
                case 4:
                    System.out.println("  Ordenar por: 1=Título  2=Duración  3=Artista");
                    System.out.print("  Criterio: ");
                    int crit = leerInt();
                    pl.ordenar(crit);
                    break;
                case 5:
                    usuarioActivo.getReproductor().agregarPlaylistACola(pl);
                    break;
                case 0:
                    activo = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ─── Submenú: Recomendaciones ────────────────────────────────

    static void menuRecomendaciones() {
        Recomendaciones rec = new Recomendaciones(usuarioActivo);
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌───────── ✨ RECOMENDACIONES ✨ ──────────┐");
            System.out.println("│  1. Ver mis recomendaciones               │");
            System.out.println("│  2. Añadir recomendación a la cola        │");
            System.out.println("│  0. Volver                                │");
            System.out.println("└───────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();
            switch (op) {
                case 1: rec.sugerirCanciones(catalogo); break;
                case 2:
                    ArrayList<Cancion> sugs = rec.recomendar(catalogo, 5);
                    if (sugs.isEmpty()) { System.out.println("No hay sugerencias."); break; }
                    for (int i = 0; i < sugs.size(); i++)
                        System.out.println("  " + (i+1) + ". " + sugs.get(i).toString());
                    System.out.print("  Número a añadir a la cola: ");
                    int n = leerInt();
                    if (n >= 1 && n <= sugs.size())
                        usuarioActivo.getReproductor().agregarACola(sugs.get(n-1));
                    break;
                case 0: activo = false; break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ─── Submenú: Descargas (Premium) ────────────────────────────

    static void menuDescargas() {
        UsuarioPremium premium = (UsuarioPremium) usuarioActivo;
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌──────────── 📥 DESCARGAS OFFLINE ─────────┐");
            System.out.println("│  1. Ver canciones descargadas              │");
            System.out.println("│  2. Descargar una canción del catálogo     │");
            System.out.println("│  3. Descargar una playlist completa        │");
            System.out.println("│  4. Eliminar una descarga                  │");
            System.out.println("│  0. Volver                                 │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1: premium.verDescargas(); break;
                case 2:
                    mostrarCatalogoCompleto();
                    System.out.print("  Número de canción a descargar: ");
                    int numC = leerInt();
                    if (numC >= 1 && numC <= catalogo.size())
                        premium.descargarCancion(catalogo.get(numC - 1));
                    break;
                case 3:
                    usuarioActivo.listarPlaylists();
                    if (!usuarioActivo.getPlaylists().isEmpty()) {
                        System.out.print("  Número de playlist: ");
                        int idx = leerInt() - 1;
                        if (idx >= 0 && idx < usuarioActivo.getPlaylists().size())
                            premium.descargarPlaylist(usuarioActivo.getPlaylists().get(idx));
                    }
                    break;
                case 4:
                    premium.verDescargas();
                    if (!premium.getDescargas().isEmpty()) {
                        System.out.print("  Número de descarga a eliminar: ");
                        int idx = leerInt() - 1;
                        if (idx >= 0 && idx < premium.getDescargas().size())
                            premium.eliminarDescarga(premium.getDescargas().get(idx));
                    }
                    break;
                case 0: activo = false; break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ─── Submenú: Compartir Playlist (Premium) ───────────────────

    static void menuCompartirPlaylist() {
        UsuarioPremium premium = (UsuarioPremium) usuarioActivo;
        usuarioActivo.listarPlaylists();
        if (usuarioActivo.getPlaylists().isEmpty()) return;
        System.out.print("  Número de playlist a compartir: ");
        int idx = leerInt() - 1;
        if (idx >= 0 && idx < usuarioActivo.getPlaylists().size()) {
            premium.compartirPlaylist(usuarioActivo.getPlaylists().get(idx));
        } else {
            System.out.println("❌ Número inválido.");
        }
    }

    // ════════════════════════════════════════════════════════════
    //  MENÚ ARTISTA
    // ════════════════════════════════════════════════════════════

    static void menuArtista() {
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌──────────── MENÚ ARTISTA 🎤 ────────────────┐");
            System.out.println("│  1. Ver mi perfil                            │");
            System.out.println("│  2. Subir canción                            │");
            System.out.println("│  3. Crear álbum                              │");
            System.out.println("│  4. Añadir canción a un álbum               │");
            System.out.println("│  5. Ver mis canciones                        │");
            System.out.println("│  6. Ver mis álbumes                          │");
            System.out.println("│  7. Ver estadísticas                         │");
            System.out.println("│  0. Cerrar sesión                            │");
            System.out.println("└─────────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1: artistaActivo.mostrarPerfil(); break;
                case 2:
                    System.out.print("  Título de la canción: ");
                    String titulo = sc.nextLine().trim();
                    System.out.print("  Duración (segundos): ");
                    int dur = leerInt();
                    System.out.print("  Género musical: ");
                    String genero = sc.nextLine().trim();
                    artistaActivo.subirCancion(titulo, dur, genero);
                    break;
                case 3:
                    System.out.print("  Nombre del álbum: ");
                    String nomAlbum = sc.nextLine().trim();
                    System.out.print("  Año de lanzamiento: ");
                    int anio = leerInt();
                    artistaActivo.crearAlbum(nomAlbum, anio);
                    break;
                case 4:
                    artistaActivo.listarCanciones();
                    artistaActivo.listarAlbumes();
                    ArrayList<Cancion> misC = artistaActivo.getCanciones();
                    ArrayList<Album> misA = artistaActivo.getAlbumes();
                    if (!misC.isEmpty() && !misA.isEmpty()) {
                        System.out.print("  Número de canción: ");
                        int nc = leerInt() - 1;
                        System.out.print("  Número de álbum: ");
                        int na = leerInt() - 1;
                        if (nc >= 0 && nc < misC.size() && na >= 0 && na < misA.size()) {
                            artistaActivo.agregarCancionAAlbum(misC.get(nc), misA.get(na));
                        }
                    }
                    break;
                case 5: artistaActivo.listarCanciones();   break;
                case 6: artistaActivo.listarAlbumes();     break;
                case 7: artistaActivo.verEstadisticas();   break;
                case 0:
                    System.out.println("👋 Sesión cerrada. ¡Hasta pronto, " + artistaActivo.getNombre() + "!");
                    activo = false;
                    break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ════════════════════════════════════════════════════════════
    //  MENÚ ADMINISTRADOR
    // ════════════════════════════════════════════════════════════

    static void menuAdministrador() {
        boolean activo = true;
        while (activo) {
            System.out.println("\n┌────────────── MENÚ ADMIN 🛡 ───────────────┐");
            System.out.println("│  1. Panel de administración                  │");
            System.out.println("│  2. Ver catálogo completo                    │");
            System.out.println("│  3. Eliminar canción del catálogo            │");
            System.out.println("│  4. Ver lista de usuarios                    │");
            System.out.println("│  5. Eliminar usuario                         │");
            System.out.println("│  6. Ver artistas registrados                 │");
            System.out.println("│  7. Validar artista                          │");
            System.out.println("│  8. Moderar contenido                        │");
            System.out.println("│  0. Cerrar sesión                            │");
            System.out.println("└─────────────────────────────────────────────┘");
            System.out.print("  Opción: ");
            int op = leerInt();

            switch (op) {
                case 1:
                    adminActivo.mostrarPanelAdmin(catalogo, usuarios, artistas);
                    break;
                case 2:
                    mostrarCatalogoCompleto();
                    break;
                case 3:
                    mostrarCatalogoCompleto();
                    System.out.print("  ID de canción a eliminar (ej: C01-1): ");
                    String idC = sc.nextLine().trim();
                    adminActivo.quitarCancion(catalogo, idC);
                    break;
                case 4:
                    System.out.println("\n👤 Usuarios registrados:");
                    for (int i = 0; i < usuarios.size(); i++) {
                        System.out.println("  " + (i+1) + ". [" + usuarios.get(i).getId() + "] "
                                + usuarios.get(i).getNombre()
                                + " (" + usuarios.get(i).getTipoCuenta() + ")");
                    }
                    break;
                case 5:
                    System.out.print("  ID de usuario a eliminar: ");
                    String idU = sc.nextLine().trim();
                    adminActivo.eliminarUsuario(usuarios, idU);
                    break;
                case 6:
                    System.out.println("\n🎤 Artistas registrados:");
                    for (int i = 0; i < artistas.size(); i++) {
                        System.out.println("  " + (i+1) + ". [" + artistas.get(i).getId() + "] "
                                + artistas.get(i).getNombre()
                                + " - " + artistas.get(i).getGenero());
                    }
                    break;
                case 7:
                    System.out.println("\n🎤 Artistas registrados:");
                    for (int i = 0; i < artistas.size(); i++) {
                        System.out.println("  " + (i+1) + ". " + artistas.get(i).getNombre());
                    }
                    System.out.print("  Número de artista a validar: ");
                    int idx = leerInt() - 1;
                    if (idx >= 0 && idx < artistas.size())
                        adminActivo.validarArtista(artistas.get(idx));
                    break;
                case 8:
                    System.out.print("  Descripción del contenido a moderar: ");
                    String desc = sc.nextLine().trim();
                    adminActivo.moderarContenido(desc);
                    break;
                case 0:
                    System.out.println("👋 Sesión cerrada. ¡Hasta pronto, " + adminActivo.getNombre() + "!");
                    activo = false;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }

    // ════════════════════════════════════════════════════════════
    //  CATÁLOGO PÚBLICO
    // ════════════════════════════════════════════════════════════

    static void menuCatalogoPublico() {
        mostrarCatalogoCompleto();
        System.out.println("\n🔍 Buscar canción:");
        System.out.print("  Título (Enter para omitir): ");
        String titulo = sc.nextLine().trim();
        if (!titulo.isEmpty()) {
            System.out.println("Resultados:");
            for (Cancion c : catalogo) {
                if (c.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                    System.out.println("  • " + c.toString());
                }
            }
        }
    }

    static void mostrarCatalogoCompleto() {
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
            return;
        }
        System.out.println("\n╔════════════════ CATÁLOGO ═══════════════════╗");
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.printf("  %2d. %s%n", (i + 1), catalogo.get(i).toString());
        }
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ════════════════════════════════════════════════════════════
    //  UTILITARIOS
    // ════════════════════════════════════════════════════════════

    /**
     * Lee un número entero del teclado de forma segura.
     * Si el usuario escribe algo que no es número, devuelve -1.
     */
    static int leerInt() {
        try {
            String linea = sc.nextLine().trim();
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
