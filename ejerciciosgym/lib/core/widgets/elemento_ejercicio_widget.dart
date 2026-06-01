import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:flutter/material.dart';

class ElementoEjercicioWidget extends StatelessWidget {
  const ElementoEjercicioWidget({super.key, required this.ejercicioImagen});

  final EjercicioImagenModel ejercicioImagen;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return InkWell(
      onTap: () => Navigator.of(
        context,
      ).pushNamed(App.informacionEjercicio, arguments: ejercicioImagen),
      child: Padding(
        padding: const EdgeInsetsGeometry.only(top: 8),
        child: Card(
          margin: EdgeInsets.symmetric(vertical: 4, horizontal: 5),
          child: ClipRRect(
            borderRadius: BorderRadiusGeometry.circular(10),
            child: Row(
              children: [
                FadeInImage(
                  placeholder: AssetImage('assets/loading.gif'),
                  image: MemoryImage(ejercicioImagen.imagen),
                  fit: BoxFit.fill,
                  width: size.width * 0.3,
                  height: size.height * 0.1,
                ),
                SizedBox(width: 20),
                Expanded(
                  child: Text(
                    ejercicioImagen.ejercicio.nombre,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
