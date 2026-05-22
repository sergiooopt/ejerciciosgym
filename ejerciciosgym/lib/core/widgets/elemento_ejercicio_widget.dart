import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:flutter/material.dart';

class ElementoEjercicioWidget extends StatelessWidget {
  const ElementoEjercicioWidget({super.key, required this.ejercicio});

  final EjercicioImagenModel ejercicio;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return InkWell(
      onTap: () => Navigator.of(context).pushNamed(
        App.informacionEjercicio,
        arguments: ejercicio.ejercicio.nombre,
      ),
      child: ListTile(
        leading: FadeInImage(
          placeholder: AssetImage('assets/imagenes/cargando.gif'),
          image: MemoryImage(ejercicio.imagen),
          fit: BoxFit.fill,
          width: size.width * 0.2,
          height: size.height * 0.4,
        ),
        title: Text(ejercicio.ejercicio.nombre),
      ),
    );
  }
}
