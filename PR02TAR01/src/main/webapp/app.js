// app.js

class Producto {
    constructor(id, nombre, descripcion, precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    validar() {
        if (!this.nombre || this.nombre.trim() === "") {
            return "El nombre no puede estar vacío.";
        }
        if (isNaN(this.precio) || Number(this.precio) <= 0) {
            return "El precio debe ser numérico y mayor a 0.";
        }
        return null;
    }
}

const mostrarMensaje = (msg, exito = true) => {
    const div = document.getElementById('resultados');
    div.textContent = msg;
    div.style.color = exito ? 'green' : 'red';
};

const limpiarFormulario = () => {
    document.getElementById('id').value = '';
    document.getElementById('nombre').value = '';
    document.getElementById('descripcion').value = '';
    document.getElementById('precio').value = '';
};

const cargarProductos = () => {
    fetch('MostrarProductosServlet')
        .then(r => r.json())
        .then(productos => {
            const tbody = document.querySelector('#tablaProductos tbody');
            tbody.innerHTML = '';
            productos.forEach(p => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td><input type="radio" name="selectProducto" value="${p.id}"></td>
                    <td>${p.id}</td>
                    <td>${p.nombre}</td>
                    <td>${p.descripcion}</td>
                    <td>${p.precio}</td>
                `;
                tbody.appendChild(tr);
            });
        });
};

document.getElementById('btnMostrar').onclick = e => {
    e.preventDefault();
    cargarProductos();
};

document.getElementById('btnIngresar').onclick = e => {
    e.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const descripcion = document.getElementById('descripcion').value;
    const precio = document.getElementById('precio').value;
    const producto = new Producto(null, nombre, descripcion, precio);
    const error = producto.validar();
    if (error) {
        mostrarMensaje(error, false);
        return;
    }
    const formData = new URLSearchParams();
    formData.append('nombre', nombre);
    formData.append('descripcion', descripcion);
    formData.append('precio', precio);
    fetch('InsertarProductoServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    })
    .then(r => r.json())
    .then(res => {
        mostrarMensaje(res.mensaje, res.exito);
        if (res.exito) {
            limpiarFormulario();
            cargarProductos();
        }
    });
};

document.getElementById('btnEditar').onclick = e => {
    e.preventDefault();
    const id = document.getElementById('id').value;
    const nombre = document.getElementById('nombre').value;
    const descripcion = document.getElementById('descripcion').value;
    const precio = document.getElementById('precio').value;
    const producto = new Producto(id, nombre, descripcion, precio);
    const error = producto.validar();
    if (!id) {
        mostrarMensaje('Selecciona un producto para editar.', false);
        return;
    }
    if (error) {
        mostrarMensaje(error, false);
        return;
    }
    const formData = new URLSearchParams();
    formData.append('id', id);
    formData.append('nombre', nombre);
    formData.append('descripcion', descripcion);
    formData.append('precio', precio);
    fetch('EditarProductoServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    })
    .then(r => r.json())
    .then(res => {
        mostrarMensaje(res.mensaje, res.exito);
        if (res.exito) {
            limpiarFormulario();
            cargarProductos();
        }
    });
};

document.getElementById('btnEliminar').onclick = e => {
    e.preventDefault();
    const id = document.getElementById('id').value;
    if (!id) {
        mostrarMensaje('Selecciona un producto para eliminar.', false);
        return;
    }
    const formData = new URLSearchParams();
    formData.append('id', id);
    fetch('EliminarProductoServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData
    })
    .then(r => r.json())
    .then(res => {
        mostrarMensaje(res.mensaje, res.exito);
        if (res.exito) {
            limpiarFormulario();
            cargarProductos();
        }
    });
};

document.querySelector('#tablaProductos tbody').onclick = e => {
    if (e.target.name === 'selectProducto') {
        const tr = e.target.closest('tr');
        document.getElementById('id').value = tr.children[1].textContent;
        document.getElementById('nombre').value = tr.children[2].textContent;
        document.getElementById('descripcion').value = tr.children[3].textContent;
        document.getElementById('precio').value = tr.children[4].textContent;
    }
};

// Cargar productos al inicio
window.onload = cargarProductos;
