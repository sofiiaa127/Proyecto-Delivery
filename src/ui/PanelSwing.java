package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Line2D;

import model.Pedido;
import model.Repartidor;
import model.Restaurante;
import servicio.CambioEstado;
import servicio.DeliveryService;
import servicio.ReporteService;
import servicio.ResultadoCiclo;
import estructuras.grafo.Grafo;
import estructuras.grafo.Vertice;
import estructuras.grafo.Arista;
import estructuras.grafo.ResultadoDijkstra;
import utils.GeneradorDatos;

/**
 * Interfaz grafica (Swing) del simulador de delivery.
 *
 * Muestra en un solo tablero:
 *   - El mapa de zonas como grafo ponderado, con la ruta mas corta (Dijkstra) resaltada.
 *   - Las colas de cocina (normal y VIP), repartidores, ranking de restaurantes (arbol)
 *     y el historial de cambios de estado (pila).
 */
public class PanelSwing extends JFrame {

    // ----- Paleta de colores (estilo aplicacion oscura) -----
    private static final Color FONDO        = new Color(0x101622);
    private static final Color PANEL        = new Color(0x18203033 & 0xFFFFFF);
    private static final Color TARJETA      = new Color(0x1B2433);
    private static final Color BORDE        = new Color(0x2A3650);
    private static final Color TEXTO        = new Color(0xE8EDF6);
    private static final Color TEXTO_TENUE  = new Color(0x9AA7BD);
    private static final Color ACENTO       = new Color(0xFF6B35);  
    private static final Color VERDE        = new Color(0x2DD4A8);
    private static final Color AMARILLO     = new Color(0xF7C948);
    private static final Color AZUL         = new Color(0x4F9CF9);
    private static final Color ROJO         = new Color(0xEF5A6F);

    private final DeliveryService servicio = new DeliveryService();
    private final GeneradorDatos generador = new GeneradorDatos();

    private GrafoPanel grafoPanel;
    private DefaultListModel<String> modeloNormal = new DefaultListModel<>();
    private DefaultListModel<String> modeloVip    = new DefaultListModel<>();
    private DefaultListModel<String> modeloReps   = new DefaultListModel<>();
    private DefaultListModel<String> modeloTop    = new DefaultListModel<>();
    private DefaultListModel<String> modeloHist   = new DefaultListModel<>();

    private JLabel lblPendientes;
    private JLabel lblTotal;
    private JLabel lblEstado;
    private JComboBox<String> comboDestino;

    public PanelSwing() {
        super("Simulador de Delivery - Estructuras de Datos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 720));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(12, 12));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(14, 14, 14, 14));

        add(crearCabecera(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearLateralDerecho(), BorderLayout.EAST);

        refrescar();
    }


    //  CABECERA
    private JComponent crearCabecera() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(PANEL); 

        JLabel titulo = new JLabel("  Delivery Express");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel sub = new JLabel("   Colas - Prioridad - Hash - Pila - Grafo (Dijkstra)");
        sub.setForeground(TEXTO_TENUE);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izq.setBackground(FONDO);
        izq.add(titulo);
        izq.add(sub);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        stats.setBackground(FONDO);
        lblTotal = chip("Pedidos: 0", AZUL);
        lblPendientes = chip("En cocina: 0", AMARILLO);
        stats.add(lblTotal);
        stats.add(lblPendientes);

        barra.add(izq, BorderLayout.WEST);
        barra.add(stats, BorderLayout.EAST);
        return barra;
    }

    private JLabel chip(String texto, Color color) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(TARJETA);
        l.setForeground(color);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                new EmptyBorder(6, 14, 6, 14)));
        return l;
    }

    //  CENTRO: mapa (grafo) + controles
    private JComponent crearCentro() {
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(FONDO);

        grafoPanel = new GrafoPanel(servicio.getGrafo());
        JPanel cont = tarjeta("Mapa de zonas  -  Ruta mas corta (Dijkstra)");
        cont.add(grafoPanel, BorderLayout.CENTER);
        centro.add(cont, BorderLayout.CENTER);

        centro.add(crearControles(), BorderLayout.SOUTH);
        return centro;
    }

    private JComponent crearControles() {
        JPanel panel = new JPanel(new BorderLayout(10, 8));
        panel.setBackground(FONDO);

        // Fila 1: acciones de simulacion
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila1.setBackground(FONDO);
        fila1.add(boton("Generar datos", AZUL, e -> generarDatos()));
        fila1.add(boton("Nuevo pedido", VERDE, e -> nuevoPedidoDialogo()));
        fila1.add(boton("Procesar siguiente", ACENTO, e -> procesarUno()));
        fila1.add(boton("Procesar todos", ACENTO, e -> procesarTodos()));
        fila1.add(boton("Buscar por ID (hash)", AMARILLO, e -> buscarPorId()));
        fila1.add(boton("Reporte .txt", TEXTO_TENUE, e -> generarReporte()));

        // Fila 2: calculo de ruta interactivo
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila2.setBackground(FONDO);
        JLabel l = new JLabel("Ruta desde Centro hasta:");
        l.setForeground(TEXTO_TENUE);
        comboDestino = new JComboBox<>(nombresZonas());
        comboDestino.setBackground(TARJETA);
        comboDestino.setForeground(TEXTO);
        fila2.add(l);
        fila2.add(comboDestino);
        fila2.add(boton("Calcular ruta", AZUL, e -> calcularRutaManual()));
        lblEstado = new JLabel("Listo.");
        lblEstado.setForeground(VERDE);
        fila2.add(lblEstado);

        panel.add(fila1, BorderLayout.NORTH);
        panel.add(fila2, BorderLayout.SOUTH);
        return panel;
    }


    //  LATERAL DERECHO: listas
    private JComponent crearLateralDerecho() {
        JPanel lateral = new JPanel(new GridLayout(5, 1, 0, 10));
        lateral.setBackground(FONDO);
        lateral.setPreferredSize(new Dimension(340, 0));

        lateral.add(panelLista("Cola cocina (normal - FIFO)", modeloNormal, AZUL));
        lateral.add(panelLista("Cola VIP (prioridad - heap)", modeloVip, ACENTO));
        lateral.add(panelLista("Repartidores (lista enlazada)", modeloReps, VERDE));
        lateral.add(panelLista("Top 5 restaurantes (arbol)", modeloTop, AMARILLO));
        lateral.add(panelLista("Historial de estados (pila)", modeloHist, TEXTO_TENUE));
        return lateral;
    }

    private JComponent panelLista(String titulo, DefaultListModel<String> modelo, Color acento) {
        JPanel card = tarjeta(titulo);
        JList<String> lista = new JList<>(modelo);
        lista.setBackground(TARJETA);
        lista.setForeground(TEXTO);
        lista.setSelectionBackground(BORDE);
        lista.setSelectionForeground(TEXTO);
        lista.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lista.setBorder(new EmptyBorder(4, 6, 4, 6));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(TARJETA);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private JPanel tarjeta(String titulo) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(TARJETA);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                new EmptyBorder(10, 12, 12, 12)));
        JLabel t = new JLabel(titulo);
        t.setForeground(TEXTO);
        t.setFont(new Font("SansSerif", Font.BOLD, 13));
        card.add(t, BorderLayout.NORTH);
        return card;
    }

    private JButton boton(String texto, Color color, java.awt.event.ActionListener accion) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setForeground(FONDO);
        b.setBackground(color);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(new EmptyBorder(8, 14, 8, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(accion);
        return b;
    }

    private String[] nombresZonas() {
        Grafo g = servicio.getGrafo();
        Vertice[] zonas = g.getZonas();
        String[] nombres = new String[g.getCantidadZonas()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = zonas[i].getNombreZona();
        }
        return nombres;
    }

    //  ACCIONES
    private void generarDatos() {
        for (Repartidor r : generador.generarRepartidores(4)) servicio.agregarRepartidor(r);
        for (Restaurante r : generador.generarRestaurantes())  servicio.agregarRestaurante(r);
        for (Pedido p : generador.generarPedidos(6))           servicio.registrarPedido(p);
        lblEstado.setForeground(VERDE);
        lblEstado.setText("Datos generados.");
        refrescar();
    }

    private void nuevoPedidoDialogo() {
        JTextField descripcion = new JTextField("Pedido manual");
        JSpinner prioridad = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        JComboBox<String> zona = new JComboBox<>(nombresZonas());
        JCheckBox vip = new JCheckBox("Pedido VIP");

        JPanel form = new JPanel(new GridLayout(0, 1, 4, 4));
        form.add(new JLabel("Descripcion:")); form.add(descripcion);
        form.add(new JLabel("Prioridad (1-5):")); form.add(prioridad);
        form.add(new JLabel("Zona destino:")); form.add(zona);
        form.add(vip);

        int op = JOptionPane.showConfirmDialog(this, form, "Nuevo pedido",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (op == JOptionPane.OK_OPTION) {
            int id = servicio.obtenerTotalPedidos() + 1;
            String hora = String.format("%02d:%02d",
                    java.time.LocalTime.now().getHour(), java.time.LocalTime.now().getMinute());
            Pedido p = new Pedido(id, descripcion.getText(), (int) prioridad.getValue(),
                    (String) zona.getSelectedItem(), hora, vip.isSelected());
            servicio.registrarPedido(p);
            lblEstado.setForeground(VERDE);
            lblEstado.setText("Pedido #" + id + " registrado.");
            refrescar();
        }
    }

    private void procesarUno() {
        ResultadoCiclo r = servicio.procesarSiguientePedido();
        if (r.isExito()) {
            grafoPanel.setRuta(r.getRuta());
            lblEstado.setForeground(VERDE);
            lblEstado.setText(r.getMensaje() + "  Repartidor: " + r.getRepartidor().getNombre()
                    + "  (" + r.getRuta().getMinutosTotales() + " min)");
        } else {
            lblEstado.setForeground(ROJO);
            lblEstado.setText(r.getMensaje());
        }
        refrescar();
    }

    private void procesarTodos() {
        int n = 0;
        ResultadoCiclo r;
        while ((r = servicio.procesarSiguientePedido()).isExito()) {
            grafoPanel.setRuta(r.getRuta());
            n++;
        }
        lblEstado.setForeground(n > 0 ? VERDE : ROJO);
        lblEstado.setText(n > 0 ? (n + " pedidos entregados.") : r.getMensaje());
        refrescar();
    }

    private void buscarPorId() {
        String entrada = JOptionPane.showInputDialog(this, "ID del pedido a buscar:");
        if (entrada == null || entrada.trim().isEmpty()) return;
        try {
            Pedido p = servicio.buscarPedidoPorId(Integer.parseInt(entrada.trim()));
            if (p == null) {
                JOptionPane.showMessageDialog(this, "No existe un pedido con ese ID.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pedido #" + p.getId() + "\nDescripcion: " + p.getDescripcion()
                        + "\nPrioridad: " + p.getPrioridad() + (p.isEsVip() ? " (VIP)" : "")
                        + "\nZona: " + p.getZonaDestino() + "\nEstado: " + p.getEstado()
                        + (p.getRepartidor() != null ? "\nRepartidor: " + p.getRepartidor().getNombre() : ""),
                        "Resultado de la busqueda (hash O(1))", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un numero.");
        }
    }

    private void calcularRutaManual() {
        String destino = (String) comboDestino.getSelectedItem();
        ResultadoDijkstra r = servicio.calcularRuta(servicio.getZonaCocina(), destino);
        if (r.existeRuta()) {
            grafoPanel.setRuta(r);
            lblEstado.setForeground(AZUL);
            lblEstado.setText("Ruta a " + destino + ": " + r.getMinutosTotales() + " minutos.");
        } else {
            lblEstado.setForeground(ROJO);
            lblEstado.setText("No hay ruta hacia " + destino + ".");
        }
    }

    private void generarReporte() {
        new java.io.File("reportes").mkdirs();
        String ruta = "reportes/pedidos_dia.txt";
        boolean ok = new ReporteService().generarReporteDia(
                servicio.obtenerTodosPedidos(), servicio.obtenerTotalPedidos(), ruta);
        lblEstado.setForeground(ok ? VERDE : ROJO);
        lblEstado.setText(ok ? "Reporte generado en " + ruta : "No hay pedidos para el reporte.");
    }


    //  REFRESCO DE DATOS
    private void refrescar() {
        cargar(modeloNormal, servicio.obtenerColaNormal(), true);
        cargar(modeloVip, servicio.obtenerColaVip(), true);
        cargarReps(servicio.obtenerRepartidores());
        modeloTop.clear();
        for (String s : servicio.obtenerTop5Restaurantes()) modeloTop.addElement("  " + s);
        cargarHist(servicio.obtenerHistorial());

        lblTotal.setText("Pedidos: " + servicio.obtenerTotalPedidos());
        lblPendientes.setText("En cocina: " + servicio.obtenerPedidosPendientes());
        grafoPanel.repaint();
    }

    private void cargar(DefaultListModel<String> modelo, Object[] datos, boolean esPedido) {
        modelo.clear();
        if (datos.length == 0) { modelo.addElement("  (vacio)"); return; }
        for (Object o : datos) {
            Pedido p = (Pedido) o;
            modelo.addElement("  #" + p.getId() + "  " + p.getDescripcion()
                    + "  [P" + p.getPrioridad() + (p.isEsVip() ? " VIP" : "") + "]  -> " + p.getZonaDestino());
        }
    }

    private void cargarReps(Object[] datos) {
        modeloReps.clear();
        if (datos.length == 0) { modeloReps.addElement("  (sin repartidores)"); return; }
        for (Object o : datos) {
            Repartidor r = (Repartidor) o;
            modeloReps.addElement("  " + r.getNombre() + "  -  " + (r.isLibre() ? "LIBRE" : "OCUPADO")
                    + "  (" + r.getZonaActual() + ")");
        }
    }

    private void cargarHist(Object[] datos) {
        modeloHist.clear();
        if (datos.length == 0) { modeloHist.addElement("  (sin cambios)"); return; }
        int max = Math.min(datos.length, 10); // ultimos 10
        for (int i = 0; i < max; i++) {
            modeloHist.addElement("  " + ((CambioEstado) datos[i]).toString());
        }
    }

    //  PANEL DEL GRAFO (dibujo del mapa con la ruta resaltada)
    private static class GrafoPanel extends JPanel {

        private final Grafo grafo;
        private int[] rutaActual = new int[0];

        // Posiciones relativas (0..1) de cada zona en el orden del grafo.
        private final double[][] pos = {
            {0.50, 0.50}, // 0 Centro
            {0.50, 0.12}, // 1 Norte
            {0.50, 0.88}, // 2 Barrio Bolivar
            {0.86, 0.50}, // 3 Chapinero
            {0.14, 0.50}, // 4 La 20 
            {0.70, 0.26}, // 5 El obando
            {0.30, 0.80}, // 6 Bella Vista
            {0.30, 0.18}, // 7 El Cadillall
            {0.80, 0.16}, // 8 Las Americas
            {0.18, 0.82}  // 9 La Ladera
        };

        GrafoPanel(Grafo grafo) {
            this.grafo = grafo;
            setBackground(new Color(0x12192A));
        }

        void setRuta(ResultadoDijkstra r) {
            this.rutaActual = (r != null && r.existeRuta()) ? r.getRuta() : new int[0];
            repaint();
        }

        private boolean enRuta(int a, int b) {
            for (int i = 0; i < rutaActual.length - 1; i++) {
                if ((rutaActual[i] == a && rutaActual[i + 1] == b)
                        || (rutaActual[i] == b && rutaActual[i + 1] == a)) {
                    return true;
                }
            }
            return false;
        }

        private boolean nodoEnRuta(int idx) {
            for (int z : rutaActual) if (z == idx) return true;
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int n = grafo.getCantidadZonas();
            if (n == 0) {
                g2.setColor(new Color(0x9AA7BD));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g2.drawString("Pulsa 'Generar datos' para iniciar.", 20, 30);
                return;
            }

            int w = getWidth(), h = getHeight();
            int margen = 50;
            int[] xs = new int[n], ys = new int[n];
            for (int i = 0; i < n; i++) {
                xs[i] = margen + (int) (pos[i][0] * (w - 2 * margen));
                ys[i] = margen + (int) (pos[i][1] * (h - 2 * margen));
            }

            Vertice[] zonas = grafo.getZonas();

            // 1) Aristas
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            for (int i = 0; i < n; i++) {
                Arista a = zonas[i].getPrimeraArista();
                while (a != null) {
                    int j = indiceDe(zonas, a.getDestino(), n);
                    if (j > i) { // dibujar cada arista una sola vez
                        boolean ruta = enRuta(i, j);
                        g2.setStroke(new BasicStroke(ruta ? 4.5f : 1.6f));
                        g2.setColor(ruta ? new Color(0xFF6B35) : new Color(0x2A3650));
                        g2.draw(new Line2D.Double(xs[i], ys[i], xs[j], ys[j]));

                        int mx = (xs[i] + xs[j]) / 2, my = (ys[i] + ys[j]) / 2;
                        g2.setColor(ruta ? new Color(0xF7C948) : new Color(0x6B7A99));
                        g2.drawString(a.getPesoMinutos() + "m", mx - 8, my - 4);
                    }
                    a = a.getSiguiente();
                }
            }

            // 2) Nodos
            for (int i = 0; i < n; i++) {
                boolean ruta = nodoEnRuta(i);
                boolean cocina = i == 0;
                int radio = cocina ? 16 : 13;
                Color relleno = cocina ? new Color(0x2DD4A8)
                        : ruta ? new Color(0xFF6B35) : new Color(0x1B2433);
                g2.setColor(relleno);
                g2.fillOval(xs[i] - radio, ys[i] - radio, radio * 2, radio * 2);
                g2.setColor(ruta || cocina ? Color.WHITE : new Color(0x4F9CF9));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(xs[i] - radio, ys[i] - radio, radio * 2, radio * 2);

                g2.setColor(new Color(0xE8EDF6));
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                String nombre = zonas[i].getNombreZona() + (cocina ? " (cocina)" : "");
                g2.drawString(nombre, xs[i] + radio + 4, ys[i] + 4);
            }
        }

        private int indiceDe(Vertice[] zonas, Vertice v, int n) {
            for (int i = 0; i < n; i++) if (zonas[i] == v) return i;
            return -1;
        }
    }
}
