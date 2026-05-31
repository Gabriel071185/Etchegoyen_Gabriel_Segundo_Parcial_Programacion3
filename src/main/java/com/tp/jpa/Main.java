package com.tp.jpa;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.*;
import com.tp.jpa.model.*;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import org.h2.tools.Server;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerInt("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuReportes();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
        JPAUtil.close(); // Cerramos el EntityManagerFactory
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTION ===");
        System.out.println("1. Gestionar Categorias");
        System.out.println("2. Gestionar Productos");
        System.out.println("3. Reportes");
        System.out.println("0. Salir");
    }

    // ==================== MENÚ CATEGORÍAS ====================

    private static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CATEGORIAS ---");
            System.out.println("1. Alta de categoria");
            System.out.println("2. Baja de categoria");
            System.out.println("3. Modificacion de categoría");
            System.out.println("4. Listar categorias activas");
            System.out.println("0. Volver");

            opcion = leerInt("Opcion: ");

            switch (opcion) {
                case 1:
                    altaCategoria();
                    break;
                case 2:
                    bajaCategoria();
                    break;
                case 3:
                    modificacionCategoria();
                    break;
                case 4:
                    listarCategorias();
                    break;
            }
        } while (opcion != 0);
    }

    // ==================== MENÚ PRODUCTOS ====================

    private static void menuProductos() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRODUCTOS ---");
            System.out.println("1. Alta de producto");
            System.out.println("2. Baja de producto");
            System.out.println("3. Modificacion de producto");
            System.out.println("4. Listar productos activos");
            System.out.println("0. Volver");

            opcion = leerInt("Opcion: ");

            switch (opcion) {
                case 1:
                    altaProducto();
                    break;
                case 2:
                    bajaProducto();
                    break;
                case 3:
                    modificacionProducto();
                    break;
                case 4:
                    listarProductos();
                    break;
            }
        } while (opcion != 0);
    }

    // ==================== MENÚ REPORTES ====================

    private static void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ REPORTES ---");
            System.out.println("1. Productos por categoria");
            System.out.println("0. Volver");

            opcion = leerInt("Opcion: ");

            switch (opcion) {
                case 1:
                    productosPorCategoria();
                    break;
            }
        } while (opcion != 0);
    }

    // ==================== CATEGORÍAS: Alta/Baja/Modificación/Listado ====================

    private static void altaCategoria() {
        System.out.println("\n--- ALTA DE CATEGORÍA ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("ERROR: El nombre no puede estar vacio.");
            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine().trim();

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        Categoria guardada = categoriaRepo.guardar(categoria);
        System.out.println("✓ Categoria creada exitosamente con ID: " + guardada.getId());
    }

    // ==================== HU-05: Baja de categoría ====================

    /**
     * HU-05: Dar de baja una categoría
     *
     * Criterios de aceptación:
     * 1. Baja lógica: eliminado = true
     * 2. Si el ID no existe o ya está dado de baja, informa error
     * 3. La categoría no aparece en listados activos
     * 4. Confirma la operación mostrando el nombre
     */
    private static void bajaCategoria() {
        System.out.println("\n--- BAJA DE CATEGORIA ---");

        // Mostrar categorías activas antes de pedir ID
        listarCategorias();

        Long id = leerLong("Ingrese el ID de la categoria a dar de baja: ");

        // Buscar la categoría
        Optional<Categoria> categoriaOpt = categoriaRepo.buscarPorId(id);

        // Validar que exista
        if (categoriaOpt.isEmpty()) {
            System.out.println("ERROR: No existe una categoria con ID " + id);
            return;
        }

        Categoria categoria = categoriaOpt.get();

        // Validar que no esté ya dada de baja
        if (categoria.getEliminado()) {
            System.out.println("ERROR: La categoria '" + categoria.getNombre() + "' ya esta dada de baja.");
            return;
        }

        // Realizar baja lógica
        boolean exito = categoriaRepo.eliminarLogico(id);

        if (exito) {
            System.out.println("✓ Categoria '" + categoria.getNombre() + "' dada de baja correctamente.");
        } else {
            System.out.println("ERROR: No se pudo realizar la baja.");
        }
    }

    // ==================== HU-04: Modificación de categoría ====================

    /**
     * HU-04: Modificar una categoría existente
     *
     * Criterios de aceptación:
     * 1. Lista las categorías activas antes de pedir ID
     * 2. Si el ID no corresponde a una categoría activa, muestra error
     * 3. Muestra los valores actuales antes de pedir los nuevos
     * 4. Dejar un campo en blanco mantiene el valor anterior
     * 5. Los cambios se persisten correctamente
     */
    private static void modificacionCategoria() {
        System.out.println("\n--- MODIFICACION DE CATEGORIA ---");

        // Listar categorías activas antes de pedir ID
        listarCategorias();

        Long id = leerLong("Ingrese el ID de la categoria a modificar: ");

        // Buscar la categoría
        Optional<Categoria> categoriaOpt = categoriaRepo.buscarPorId(id);

        // Validar que exista y esté activa
        if (categoriaOpt.isEmpty()) {
            System.out.println("ERROR: No existe una categoria con ID " + id);
            return;
        }

        Categoria categoria = categoriaOpt.get();

        if (categoria.getEliminado()) {
            System.out.println("ERROR: No se puede modificar una categoria dada de baja.");
            return;
        }

        // Mostrar valores actuales
        System.out.println("\n--- VALORES ACTUALES ---");
        System.out.println("  Nombre: " + categoria.getNombre());
        System.out.println("  Descripcion: " + categoria.getDescripcion());

        // Solicitar nuevos valores (dejar en blanco mantiene el anterior)
        System.out.println("\n--- NUEVOS VALORES (ENTER para mantener) ---");
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            categoria.setNombre(nuevoNombre);
        }

        System.out.print("Nueva descripcion: ");
        String nuevaDescripcion = scanner.nextLine().trim();
        if (!nuevaDescripcion.isEmpty()) {
            categoria.setDescripcion(nuevaDescripcion);
        }

        // Persistir cambios
        categoriaRepo.guardar(categoria);
        System.out.println("✓ Categoria modificada correctamente.");
    }

    // ==================== Listar categorías ====================

    /**
     * Lista todas las categorías activas
     * Muestra ID, nombre y descripción
     */
    private static void listarCategorias() {
        List<Categoria> categorias = categoriaRepo.listarActivos();

        System.out.println("\n--- CATEGORIAS ACTIVAS ---");

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
        } else {
            System.out.println("ID | NOMBRE | DESCRIPCIÓN");
            System.out.println("---|--------|-------------");
            for (Categoria c : categorias) {
                System.out.printf("%-3d| %-7s| %s%n",
                        c.getId(),
                        truncar(c.getNombre(), 7),
                        c.getDescripcion());
            }
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private static int leerInt(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor invalido. " + mensaje);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private static long leerLong(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextLong()) {
            System.out.print("Valor inválido. " + mensaje);
            scanner.next();
        }
        long valor = scanner.nextLong();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private static Double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Valor inválido. " + mensaje);
            scanner.next();
        }
        Double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private static String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 3) + "...";
    }

    // ==================== PRODUCTOS: Alta/Baja/Modificación/Listado ====================

    private static void altaProducto() {
        System.out.println("\n--- ALTA DE PRODUCTO ---");

        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas. Cree una categoría primero.");
            return;
        }

        System.out.println("Categorias disponibles:");
        for (Categoria c : categorias) {
            System.out.printf("%d - %s\n", c.getId(), c.getNombre());
        }

        Long categoriaId = leerLong("Ingrese el ID de la categoría para el producto: ");
        Optional<Categoria> catOpt = categoriaRepo.buscarPorId(categoriaId);
        if (catOpt.isEmpty() || catOpt.get().getEliminado()) {
            System.out.println("ERROR: Categoría inválida.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("ERROR: El nombre no puede estar vacío.");
            return;
        }

        Double precio = leerDouble("Precio: ");

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();

        int stock = leerInt("Stock: ");

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setDescripcion(descripcion);
        p.setStock(stock);
        p.setCategoria(catOpt.get());

        Producto guardado = productoRepo.guardar(p);
        System.out.println("0Producto creado con ID: " + guardado.getId());
    }

    private static void bajaProducto() {
        System.out.println("\n--- BAJA DE PRODUCTO ---");

        listarProductos();

        Long id = leerLong("Ingrese el ID del producto a dar de baja: ");
        Optional<Producto> prodOpt = productoRepo.buscarPorId(id);
        if (prodOpt.isEmpty()) {
            System.out.println("ERROR: No existe un producto con ID " + id);
            return;
        }

        Producto p = prodOpt.get();
        if (p.getEliminado()) {
            System.out.println("ERROR: El producto ya está dado de baja.");
            return;
        }

        boolean exito = productoRepo.eliminarLogico(id);
        if (exito) {
            System.out.println("Producto '" + p.getNombre() + "' dado de baja correctamente.");
        } else {
            System.out.println("ERROR: No se pudo dar de baja el producto.");
        }
    }

    private static void modificacionProducto() {
        System.out.println("\n--- MODIFICACIÓN DE PRODUCTO ---");

        listarProductos();

        Long id = leerLong("Ingrese el ID del producto a modificar: ");
        Optional<Producto> prodOpt = productoRepo.buscarPorId(id);
        if (prodOpt.isEmpty()) {
            System.out.println("ERROR: No existe un producto con ID " + id);
            return;
        }

        Producto p = prodOpt.get();
        if (p.getEliminado()) {
            System.out.println("ERROR: No se puede modificar un producto dado de baja.");
            return;
        }

        System.out.println("\n--- VALORES ACTUALES ---");
        System.out.println("  Nombre: " + p.getNombre());
        System.out.println("  Precio: " + p.getPrecio());
        System.out.println("  Stock: " + p.getStock());

        System.out.println("\n--- NUEVOS VALORES (ENTER para mantener) ---");
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) p.setNombre(nuevoNombre);

        System.out.print("Nuevo precio: ");
        String precioLine = scanner.nextLine().trim();
        if (!precioLine.isEmpty()) {
            try {
                Double nuevoPrecio = Double.parseDouble(precioLine);
                p.setPrecio(nuevoPrecio);
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido. Se mantiene el anterior.");
            }
        }

        System.out.print("Nuevo stock: ");
        String stockLine = scanner.nextLine().trim();
        if (!stockLine.isEmpty()) {
            try {
                int nuevoStock = Integer.parseInt(stockLine);
                p.setStock(nuevoStock);
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido. Se mantiene el anterior.");
            }
        }

        productoRepo.guardar(p);
        System.out.println("✓ Producto modificado correctamente.");
    }

    private static void listarProductos() {
        List<Producto> productos = productoRepo.listarActivos();

        System.out.println("\n--- PRODUCTOS ACTIVOS ---");

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos.");
        } else {
            System.out.println("ID | NOMBRE | PRECIO | STOCK | CATEGORIA");
            System.out.println("---|--------|--------|-------|----------");
            for (Producto p : productos) {
                String nombreCat = p.getCategoria() != null ? p.getCategoria().getNombre() : "-";
                System.out.printf("%-3d| %-15s| %-7.2f| %-5d| %s%n",
                        p.getId(),
                        truncar(p.getNombre(), 15),
                        p.getPrecio(),
                        p.getStock(),
                        nombreCat);
            }
        }
    }

    // ==================== REPORTES ====================

    private static void productosPorCategoria() {
        System.out.println("\n--- PRODUCTOS POR CATEGORÍA ---");

        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas.");
            return;
        }

        System.out.println("Categorias disponibles:");
        for (Categoria c : categorias) {
            System.out.printf("%d - %s\n", c.getId(), c.getNombre());
        }

        Long categoriaId = leerLong("Ingrese el ID de la categoría para listar productos: ");

        List<Producto> productos = productoRepo.buscarPorCategoria(categoriaId);

        if (productos.isEmpty()) {
            System.out.println("No hay productos activos en esa categoría.");
            return;
        }

        System.out.println("ID | NOMBRE | PRECIO | STOCK");
        System.out.println("---|--------|--------|------");
        for (Producto p : productos) {
            System.out.printf("%-3d| %-20s| %-7.2f| %-5d%n",
                    p.getId(),
                    truncar(p.getNombre(), 20),
                    p.getPrecio(),
                    p.getStock());
        }
    }

}






