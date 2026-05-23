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
          return Card(
            margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 5),
            child: Text('Error al cargar músculo'),
          );
        }

        if (snapshot.connectionState == ConnectionState.waiting) {
          return Card(
            margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 5),
            child: Center(child: CircularProgressIndicator(strokeWidth: 2)),
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
        padding: const EdgeInsets.all(12),
        child: Row(
          children: [
            Icon(
              Icons.check_circle,
              color: esDirecto ? Colors.green : Colors.orange.shade400,
              size: 28,
            ),
            SizedBox(width: 10),
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
                            backgroundColor: Colors.grey.shade200,
                            valueColor: AlwaysStoppedAnimation(
                              esDirecto ? Colors.green : Colors.orange,
                            ),
                            minHeight: 6,
                          ),
                        ),
                      ),
                      SizedBox(width: 5),
                      Text(
                        '$porcentaje%',
                        style: TextStyle(
                          fontWeight: FontWeight.w600,
                          fontSize: 13,
                          color: Colors.grey.shade700,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 4),
                  Text(
                    musculoInvolucrado.descripcion,
                    style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
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
