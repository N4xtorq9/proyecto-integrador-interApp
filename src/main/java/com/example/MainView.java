package com.example;

import java.time.LocalDateTime;

import com.example.model.Administrador;
import com.example.model.Categoria;
import com.example.model.Producto;
import com.example.model.Usuario;
import com.example.model.intercambio;
import com.example.service.CrudService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Gestión CRUD - Sistema de Inventario")
@Route("")
public class MainView extends VerticalLayout {


    private final CrudService crudService;

    public MainView(CrudService crudService) {
        this.crudService = crudService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabs = new TabSheet();
        tabs.setWidthFull();
        tabs.add("Administradores", crearSeccionAdministradores1());
        tabs.add("Productos", crearSeccionProductos());
        tabs.add("Categorías", crearSeccionCategorias());
        tabs.add("Intercambios", crearSeccionIntercambios());
        tabs.add("Usuarios", crearSeccionUsuarios());
        tabs.setSelectedIndex(1);

        add(new H2("Sistema de Inventario - CRUD"), tabs);
    }

    // ==========================================
    // 0. ADMINISTRADORES
    // ==========================================
    private Component crearSeccionAdministradores1() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField contactoField = new TextField("Contacto");
        TextField telefonoField = new TextField("Teléfono");
        TextField emailField = new TextField("Email");
        TextField direccionField = new TextField("Dirección");
        TextField rolField = new TextField("Rol");
        TextField estadoField = new TextField("Estado");
        TextField fechaField = new TextField("Fecha registro");
        TextField claveField = new TextField("Clave");

        FormLayout form = new FormLayout(idField, nombreField, contactoField, telefonoField,
                emailField, direccionField, rolField, estadoField, fechaField, claveField);

        Grid<Administrador> grid = new Grid<>(Administrador.class, false);
        grid.addColumn(Administrador::getIdAdministrador).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Administrador::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Administrador::getContacto).setHeader("Contacto").setAutoWidth(true);
        grid.addColumn(Administrador::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Administrador::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Administrador::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Administrador::getRol).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(Administrador::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Administrador::getFecharegistro).setHeader("Fecha registro").setAutoWidth(true);
        grid.addColumn(Administrador::getClave).setHeader("Clave").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Administrador.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Administrador a = e.getValue();
            if (a != null) {
                idField.setValue(a.getIdAdministrador() != null ? a.getIdAdministrador().toString() : "");
                nombreField.setValue(a.getNombre() != null ? a.getNombre() : "");
                contactoField.setValue(a.getContacto() != null ? a.getContacto() : "");
                telefonoField.setValue(a.getTelefono() != null ? a.getTelefono() : "");
                emailField.setValue(a.getEmail() != null ? a.getEmail() : "");
                direccionField.setValue(a.getDireccion() != null ? a.getDireccion() : "");
                rolField.setValue(a.getRol() != null ? a.getRol() : "");
                estadoField.setValue(a.getEstado() != null ? a.getEstado() : "");
                fechaField.setValue(a.getFecharegistro() != null ? a.getFecharegistro() : "");
                claveField.setValue(a.getClave() != null ? a.getClave() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Administrador a = new Administrador(
                nombreField.getValue(),
                contactoField.getValue(),
                telefonoField.getValue(),
                emailField.getValue(),
                direccionField.getValue()
            );
            a.setRol(rolField.getValue());
            a.setEstado(estadoField.getValue());
            a.setFecharegistro(fechaField.getValue());
            a.setClave(claveField.getValue());

            crudService.guardar(a);
            refrescar.run();
            idField.setValue(a.getIdAdministrador() != null ? a.getIdAdministrador().toString() : "");
            Notification.show("Administrador guardado con ID: " + a.getIdAdministrador());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Administrador a = crudService.buscarPorId(Administrador.class, parseId(idField));
            if (a != null) {
                grid.asSingleSelect().setValue(a);
                Notification.show("Encontrado: " + a.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Administrador a = crudService.buscarPorId(Administrador.class, parseId(idField));
            if (a != null) {
                a.setNombre(nombreField.getValue());
                a.setContacto(contactoField.getValue());
                a.setTelefono(telefonoField.getValue());
                a.setEmail(emailField.getValue());
                a.setDireccion(direccionField.getValue());
                a.setRol(rolField.getValue());
                a.setEstado(estadoField.getValue());
                a.setFecharegistro(fechaField.getValue());
                a.setClave(claveField.getValue());

                crudService.actualizar(a);
                refrescar.run();
                Notification.show("Administrador actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Administrador.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, contactoField, telefonoField, emailField,
                    direccionField, rolField, estadoField, fechaField, claveField);
            Notification.show("Administrador eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, contactoField, telefonoField, emailField,
                    direccionField, rolField, estadoField, fechaField, claveField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 1. PRODUCTOS
    // ==========================================
    private Component crearSeccionProductos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField codigoField = new TextField("Código");
        TextField nombreField = new TextField("Nombre");
        TextField categoriaField = new TextField("Categoría");
        TextField precioField = new TextField("Precio");
        TextField stockField = new TextField("Stock");

        FormLayout form = new FormLayout(idField, codigoField, nombreField, categoriaField, precioField, stockField);

        Grid<Producto> grid = new Grid<>(Producto.class, false);
        grid.addColumn(Producto::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Producto::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Producto::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getCategoria).setHeader("Categoría").setAutoWidth(true);
        grid.addColumn(Producto::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Producto::getStock).setHeader("Stock").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Producto.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Producto p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                codigoField.setValue(p.getCodigo() != null ? p.getCodigo() : "");
                nombreField.setValue(p.getNombre() != null ? p.getNombre() : "");
                categoriaField.setValue(p.getCategoria() != null ? p.getCategoria() : "");
                precioField.setValue(p.getPrecio() != null ? p.getPrecio().toString() : "");
                stockField.setValue(p.getStock() != null ? p.getStock().toString() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Producto p = new Producto(
                codigoField.getValue(),
                nombreField.getValue(),
                categoriaField.getValue(),
                parseDouble(precioField.getValue()),
                parseInt(stockField.getValue())
            );
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Producto guardado con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Producto p = crudService.buscarPorId(Producto.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Producto p = crudService.buscarPorId(Producto.class, parseId(idField));
            if (p != null) {
                p.setCodigo(codigoField.getValue());
                p.setNombre(nombreField.getValue());
                p.setCategoria(categoriaField.getValue());
                p.setPrecio(parseDouble(precioField.getValue()));
                p.setStock(parseInt(stockField.getValue()));
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Producto actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Producto.class, parseId(idField));
            refrescar.run();
            limpiar(idField, codigoField, nombreField, categoriaField, precioField, stockField);
            Notification.show("Producto eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, codigoField, nombreField, categoriaField, precioField, stockField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 2. CATEGORÍAS
    // ==========================================
    private Component crearSeccionCategorias() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField descripcionField = new TextField("Descripción");

        FormLayout form = new FormLayout(idField, nombreField, descripcionField);

        Grid<Categoria> grid = new Grid<>(Categoria.class, false);
        grid.addColumn(Categoria::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Categoria::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Categoria::getDescripcion).setHeader("Descripción").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Categoria.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Categoria c = e.getValue();
            if (c != null) {
                idField.setValue(c.getId() != null ? c.getId().toString() : "");
                nombreField.setValue(c.getNombre() != null ? c.getNombre() : "");
                descripcionField.setValue(c.getDescripcion() != null ? c.getDescripcion() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Categoria c = new Categoria(nombreField.getValue(), descripcionField.getValue());
            crudService.guardar(c);
            refrescar.run();
            idField.setValue(c.getId() != null ? c.getId().toString() : "");
            Notification.show("Categoría guardada con ID: " + c.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Categoria c = crudService.buscarPorId(Categoria.class, parseId(idField));
            if (c != null) {
                grid.asSingleSelect().setValue(c);
                Notification.show("Encontrada: " + c.getNombre());
            } else {
                Notification.show("No encontrada");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Categoria c = crudService.buscarPorId(Categoria.class, parseId(idField));
            if (c != null) {
                c.setNombre(nombreField.getValue());
                c.setDescripcion(descripcionField.getValue());
                crudService.actualizar(c);
                refrescar.run();
                Notification.show("Categoría actualizada");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Categoria.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, descripcionField);
            Notification.show("Categoría eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, descripcionField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 3. INTERCAMBIOS
    // ==========================================
    private Component crearSeccionIntercambios() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField contactoField = new TextField("Contacto");
        TextField telefonoField = new TextField("Teléfono");
        TextField emailField = new TextField("Email");
        TextField direccionField = new TextField("Dirección");

        FormLayout form = new FormLayout(idField, nombreField, contactoField, telefonoField, emailField, direccionField);

        Grid<intercambio> grid = new Grid<>(intercambio.class, false);
        grid.addColumn(intercambio::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(intercambio::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(intercambio::getContacto).setHeader("Contacto").setAutoWidth(true);
        grid.addColumn(intercambio::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(intercambio::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(intercambio::getDireccion).setHeader("Dirección").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(intercambio.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            intercambio p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                nombreField.setValue(p.getNombre() != null ? p.getNombre() : "");
                contactoField.setValue(p.getContacto() != null ? p.getContacto() : "");
                telefonoField.setValue(p.getTelefono() != null ? p.getTelefono() : "");
                emailField.setValue(p.getEmail() != null ? p.getEmail() : "");
                direccionField.setValue(p.getDireccion() != null ? p.getDireccion() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            intercambio p = new intercambio(
                nombreField.getValue(),
                contactoField.getValue(),
                telefonoField.getValue(),
                emailField.getValue(),
                direccionField.getValue()
            );
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Proveedor guardado con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            intercambio p = crudService.buscarPorId(intercambio.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            intercambio p = crudService.buscarPorId(intercambio.class, parseId(idField));
            if (p != null) {
                p.setNombre(nombreField.getValue());
                p.setContacto(contactoField.getValue());
                p.setTelefono(telefonoField.getValue());
                p.setEmail(emailField.getValue());
                p.setDireccion(direccionField.getValue());
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Proveedor actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(intercambio.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, contactoField, telefonoField, emailField, direccionField);
            Notification.show("Proveedor eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, contactoField, telefonoField, emailField, direccionField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // 4. USUARIOS
    // ==========================================
    private Component crearSeccionUsuarios() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField apellidoField = new TextField("Apellido");
        TextField emailField = new TextField("Email");
        TextField telefonoField = new TextField("Teléfono");
        TextField direccionField = new TextField("Dirección");
        TextField rolField = new TextField("Rol");
        TextField estadoField = new TextField("Estado");

        FormLayout form = new FormLayout(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);

        Grid<Usuario> grid = new Grid<>(Usuario.class, false);
        grid.addColumn(Usuario::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Usuario::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Usuario::getRol).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(Usuario::getEstado).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Usuario.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Usuario u = e.getValue();
            if (u != null) {
                idField.setValue(u.getId() != null ? u.getId().toString() : "");
                nombreField.setValue(u.getNombre() != null ? u.getNombre() : "");
                apellidoField.setValue(u.getApellido() != null ? u.getApellido() : "");
                emailField.setValue(u.getEmail() != null ? u.getEmail() : "");
                telefonoField.setValue(u.getTelefono() != null ? u.getTelefono() : "");
                direccionField.setValue(u.getDireccion() != null ? u.getDireccion() : "");
                rolField.setValue(u.getRol() != null ? u.getRol() : "");
                estadoField.setValue(u.getEstado() != null ? u.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Usuario u = new Usuario(
                nombreField.getValue(),
                apellidoField.getValue(),
                emailField.getValue(),
                telefonoField.getValue(),
                direccionField.getValue(),
                rolField.getValue(),
                estadoField.getValue(),
                LocalDateTime.now()
            );
            crudService.guardar(u);
            refrescar.run();
            idField.setValue(u.getId() != null ? u.getId().toString() : "");
            Notification.show("Usuario guardado con ID: " + u.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                grid.asSingleSelect().setValue(u);
                Notification.show("Encontrado: " + u.getNombre() + " " + u.getApellido());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                u.setNombre(nombreField.getValue());
                u.setApellido(apellidoField.getValue());
                u.setEmail(emailField.getValue());
                u.setTelefono(telefonoField.getValue());
                u.setDireccion(direccionField.getValue());
                u.setRol(rolField.getValue());
                u.setEstado(estadoField.getValue());
                crudService.actualizar(u);
                refrescar.run();
                Notification.show("Usuario actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Usuario.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);
            Notification.show("Usuario eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    private Long parseId(TextField field) {
        try {
            return Long.parseLong(field.getValue().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String val) {
        try {
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Integer parseInt(String val) {
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void limpiar(TextField... campos) {
        for (TextField c : campos) {
            c.clear();
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {
        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");
        return layout;
    }
}
