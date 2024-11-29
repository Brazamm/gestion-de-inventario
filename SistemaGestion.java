import java.util.*;

// Clase Producto para el Inventario
class Producto {
    String nombre;
    int cantidad;

    Producto(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    void actualizarCantidad(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
    }

    @Override
    public String toString() {
        return nombre + " - Cantidad: " + cantidad;
    }
}

// Clase Orden para las órdenes de clientes
class Orden {
    String cliente;
    String producto;
    int cantidad;
    boolean urgente;

    Orden(String cliente, String producto, int cantidad, boolean urgente) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.urgente = urgente;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + ", Producto: " + producto + ", Cantidad: " + cantidad + ", Urgente: " + urgente;
    }
}

// Clase principal del sistema
public class SistemaGestion {
    // Inventario como arreglo
    Producto[] inventario;

    // Lista dinámica de órdenes
    ArrayList<Orden> listaOrdenes = new ArrayList<>();

    // Pila para almacenamiento físico
    ArrayDeque<String> pilaAlmacen = new ArrayDeque<>();

    // Cola para órdenes regulares
    LinkedList<Orden> colaOrdenes = new LinkedList<>();

    // Cola priorizada para órdenes urgentes
    PriorityQueue<Orden> colaUrgente = new PriorityQueue<>(Comparator.comparing(o -> !o.urgente));

    // Inicialización del inventario
    public void inicializarInventario() {
        inventario = new Producto[]{
                new Producto("Producto A", 50),
                new Producto("Producto B", 30),
                new Producto("Producto C", 20)
        };
    }

    // Mostrar inventario
    public void mostrarInventario() {
        System.out.println("\nInventario:");
        for (Producto producto : inventario) {
            System.out.println(producto);
        }
    }

    // Actualizar inventario
    public void actualizarInventario(String nombre, int nuevaCantidad) {
        for (Producto producto : inventario) {
            if (producto.nombre.equalsIgnoreCase(nombre)) {
                producto.actualizarCantidad(nuevaCantidad);
                System.out.println("Inventario actualizado: " + producto);
                return;
            }
        }
        System.out.println("Producto no encontrado.");
    }

    // Agregar una orden
    public void agregarOrden(String cliente, String producto, int cantidad, boolean urgente) {
        Orden orden = new Orden(cliente, producto, cantidad, urgente);
        listaOrdenes.add(orden);
        if (urgente) {
            colaUrgente.add(orden);
        } else {
            colaOrdenes.add(orden);
        }
        System.out.println("Orden agregada: " + orden);
    }

    // Modificar una orden
    public void modificarOrden(int indice, String cliente, String producto, int cantidad, boolean urgente) {
        if (indice >= 0 && indice < listaOrdenes.size()) {
            Orden orden = listaOrdenes.get(indice);
            orden.cliente = cliente;
            orden.producto = producto;
            orden.cantidad = cantidad;
            orden.urgente = urgente;
            System.out.println("Orden modificada: " + orden);
        } else {
            System.out.println("Índice de orden no válido.");
        }
    }

    // Eliminar una orden
    public void eliminarOrden(int indice) {
        if (indice >= 0 && indice < listaOrdenes.size()) {
            Orden orden = listaOrdenes.remove(indice);
            colaOrdenes.remove(orden);
            colaUrgente.remove(orden);
            System.out.println("Orden eliminada: " + orden);
        } else {
            System.out.println("Índice de orden no válido.");
        }
    }

    // Procesar órdenes regulares
    public void procesarOrdenes() {
        if (!colaOrdenes.isEmpty()) {
            Orden orden = colaOrdenes.poll();
            System.out.println("Procesando orden: " + orden);
        } else {
            System.out.println("No hay órdenes regulares para procesar.");
        }
    }

    // Procesar órdenes urgentes
    public void procesarOrdenesUrgentes() {
        if (!colaUrgente.isEmpty()) {
            Orden orden = colaUrgente.poll();
            System.out.println("Procesando orden urgente: " + orden);
        } else {
            System.out.println("No hay órdenes urgentes para procesar.");
        }
    }

    // Almacén físico
    public void apilarProducto(String producto) {
        pilaAlmacen.push(producto);
        System.out.println("Producto apilado: " + producto);
    }

    public void desapilarProducto() {
        if (!pilaAlmacen.isEmpty()) {
            String producto = pilaAlmacen.pop();
            System.out.println("Producto desapilado: " + producto);
        } else {
            System.out.println("No hay productos en la pila.");
        }
    }

    // Menú interactivo
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== Menú de Gestión ===");
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Actualizar inventario");
            System.out.println("3. Agregar orden");
            System.out.println("4. Modificar orden");
            System.out.println("5. Eliminar orden");
            System.out.println("6. Procesar orden regular");
            System.out.println("7. Procesar orden urgente");
            System.out.println("8. Apilar producto");
            System.out.println("9. Desapilar producto");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    mostrarInventario();
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la nueva cantidad: ");
                    int cantidad = scanner.nextInt();
                    actualizarInventario(nombre, cantidad);
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del cliente: ");
                    String cliente = scanner.nextLine();
                    System.out.print("Ingrese el producto solicitado: ");
                    String producto = scanner.nextLine();
                    System.out.print("Ingrese la cantidad solicitada: ");
                    int cant = scanner.nextInt();
                    System.out.print("¿Es urgente? (true/false): ");
                    boolean urgente = scanner.nextBoolean();
                    agregarOrden(cliente, producto, cant, urgente);
                    break;
                case 4:
                    System.out.print("Ingrese el índice de la orden a modificar: ");
                    int indiceModificar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nuevo cliente: ");
                    cliente = scanner.nextLine();
                    System.out.print("Ingrese el nuevo producto solicitado: ");
                    producto = scanner.nextLine();
                    System.out.print("Ingrese la nueva cantidad solicitada: ");
                    cant = scanner.nextInt();
                    System.out.print("¿Es urgente? (true/false): ");
                    urgente = scanner.nextBoolean();
                    modificarOrden(indiceModificar, cliente, producto, cant, urgente);
                    break;
                case 5:
                    System.out.print("Ingrese el índice de la orden a eliminar: ");
                    int indiceEliminar = scanner.nextInt();
                    eliminarOrden(indiceEliminar);
                    break;
                case 6:
                    procesarOrdenes();
                    break;
                case 7:
                    procesarOrdenesUrgentes();
                    break;
                case 8:
                    System.out.print("Ingrese el producto a apilar: ");
                    String prodApilar = scanner.nextLine();
                    apilarProducto(prodApilar);
                    break;
                case 9:
                    desapilarProducto();
                    break;
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 10);
        scanner.close();
    }

    public static void main(String[] args) {
        SistemaGestion sistema = new SistemaGestion();
        sistema.inicializarInventario();
        sistema.mostrarMenu();
    }
}
