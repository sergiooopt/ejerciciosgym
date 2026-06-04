import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:flutter/material.dart';

class ElementoEjercicioWidget extends StatelessWidget {
  const ElementoEjercicioWidget({super.key, required this.ejercicioImagen});

  final EjercicioImagenModel ejercicioImagen;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => Navigator.of(
        context,
      ).pushNamed(App.informacionEjercicio, arguments: ejercicioImagen),
      child: Card(
        margin: EdgeInsets.symmetric(vertical: 4, horizontal: 5),
        child: Padding(
          padding: const EdgeInsets.all(10),
          child: ClipRRect(
            borderRadius: BorderRadiusGeometry.circular(10),
            child: Row(
              children: [
                Expanded(
                  flex: 1,
                  child: AspectRatio(
                    // este widget limita el tamaño dinamicamente
                    aspectRatio: 1.4,
                    child: ClipRRect(
                      borderRadius: BorderRadiusGeometry.circular(5),
                      child: FadeInImage(
                        placeholder: AssetImage('assets/loading.gif'),
                        image: MemoryImage(ejercicioImagen.imagen),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                ),
                SizedBox(width: 20),
                Expanded(
                  flex: 2,
                  child: Padding(
                    padding: const EdgeInsets.only(right: 20),
                    child: Text(
                      ejercicioImagen.ejercicio.nombre,
                      overflow: TextOverflow.ellipsis,
                    ),
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
