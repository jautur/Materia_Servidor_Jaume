package com.ejemplo.gestor.memoria;

import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemoriaProyecto {
    private final List<Proyecto> proyectos = new ArrayList<>();
    private final List<Tarea> tareas = new ArrayList<>();

    public List<Proyecto> getProyectos() { return proyectos; }
    public List<Tarea> getTareas() { return tareas; }
}
/*
Reuniremos las dos listas en una clase MemoriaProyecto marcada con la anotación @Component. Ese mecanismo no es nuevo, aunque la anotación sí lo sea. Desde la UD1 tus controladores atienden peticiones sin que nadie los construya desde main: @SpringBootApplication activa el escaneo de componentes, Spring recorre el paquete principal y sus subpaquetes, localiza las clases marcadas, crea una instancia de cada una y la conserva mientras la aplicación se ejecuta. @RestController es una especialización de @Component, de modo que tus controladores ya eran clases gestionadas por Spring. Lo único nuevo hoy es que marcas tú una clase propia que no atiende rutas.

Una vez registrada, cada controlador declara que la necesita escribiéndola como parámetro de su constructor. Spring llama a ese constructor al arrancar y le entrega la instancia que ya tiene creada. Esta forma de recibir un objeto necesario en lugar de construirlo se denomina inyección de dependencias, y como la instancia es única, ambos controladores reciben exactamente la misma. En la UD4 estudiarás el contenedor que hace ese trabajo y cómo organizar las responsabilidades entre capas; hoy este mecanismo sirve únicamente para compartir los datos existentes.

De ahí que escribir new MemoriaProyecto() dentro de un controlador anule el propósito del cambio: cada new produce un objeto distinto, con sus dos listas recién creadas y vacías, que es el problema que la clase pretende resolver. La instancia compartida solo llega por el constructor.

Queda un detalle de Java, ajeno a Spring, sobre el que se apoya toda la sesión. Cuando el controlador guarda this.tareas = memoria.getTareas(), no copia la lista: almacena una referencia al mismo objeto ArrayList. Por eso un add ejecutado desde TareaController resulta visible después desde ProyectoController. Si el método devolviera una copia, cada controlador volvería a trabajar sobre datos propios y el código compilaría y respondería igual, ocultando el fallo.

La memoria sigue siendo temporal y se vacía al reiniciar. Tiene además una segunda limitación: al existir una sola instancia compartida por todas las peticiones, y atender Tomcat cada petición en un hilo distinto, dos escrituras simultáneas operan a la vez sobre la misma lista. ArrayList no es thread-safe, por lo que esta versión no garantiza escrituras concurrentes correctas. Resulta suficiente para aprender el contrato HTTP; la persistencia llegará en la UD5.
*/

