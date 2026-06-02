import 'package:ejerciciosgym/models/ejercicio_musculos_model.dart';
import 'package:ejerciciosgym/models/musculo_model.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ElementoMusculoWidget extends StatelessWidget {
  const ElementoMusculoWidget({super.key, required this.musculoInvolucrado});

  final EjercicioMusculosModel musculoInvolucrado;

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return FutureBuilder(
      future: provider.cargarMusculoPorId(
        context,
        musculoInvolucrado.idMusculo,
      ),
      builder: (context, snapshot) {
        if (snapshot.hasError) {
          return Center(child: Text('Error al cargar músculo'));
        }

        if (snapshot.connectionState == ConnectionState.waiting) {
          // Se devuelve card respetando margenes y forma para visualizar bien el circulo de carga
          return Card(
            margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 5),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadiusGeometry.circular(10),
            ),
            child: Padding(
              padding: const EdgeInsets.all(12),
              child: Center(child: CircularProgressIndicator()),
            ),
          );
        }

        final musculo = snapshot.data!;
        return _elementoFinal(musculo);
      },
    );
  }

  Widget _elementoFinal(MusculoModel musculo) {
    final porcentaje = musculoInvolucrado.porcentajeActivacion;
    final esDirecto = musculoInvolucrado.esDirecto;

    return Card(
      margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 5),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
      child: Padding(
        padding: const EdgeInsets.all(10),
        child: Row(
          children: [
            Expanded(
              child: Column(
                children: [
                  Text(
                    musculo.nombre,
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
                  ),
                  Row(
                    children: [
                      Expanded(
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(4),
                          child: LinearProgressIndicator(
                            value: porcentaje / 100,
                            backgroundColor: Colors.grey,
                            valueColor: AlwaysStoppedAnimation(
                              esDirecto ? Colors.green : Colors.orange,
                            ),
                            minHeight: 6,
                          ),
                        ),
                      ),
                      SizedBox(width: 6),
                      Text(
                        '$porcentaje%',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 13,
                          color: Colors.grey,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 4),
                  Text(
                    musculoInvolucrado.descripcion,
                    style: TextStyle(fontSize: 12),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
