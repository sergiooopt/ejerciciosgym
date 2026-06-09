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
          // Se devuelve card respetando margin y padding para visualizar bien el circulo de carga
          return Card(
            margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
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
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
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
                          borderRadius: BorderRadius.circular(5),
                          child: LinearProgressIndicator(
                            value:
                                musculoInvolucrado.porcentajeActivacion / 100,
                            backgroundColor: Colors.grey,
                            valueColor: AlwaysStoppedAnimation(
                              musculoInvolucrado.esDirecto
                                  ? Colors.green
                                  : Colors.orange,
                            ),
                            minHeight: 6,
                          ),
                        ),
                      ),
                      SizedBox(width: 6),
                      Text(
                        '${musculoInvolucrado.porcentajeActivacion}%',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 12,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 4),
                  Text(
                    '${musculoInvolucrado.descripcion} (${musculo.zona}, ${musculo.grupo})',
                    style: TextStyle(fontSize: 12),
                    textAlign: TextAlign.center,
                  ),
                  SizedBox(height: 4),
                  Text(
                    'Descripción: ${musculo.descripcion}',
                    style: TextStyle(fontSize: 12),
                    textAlign: TextAlign.center,
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
