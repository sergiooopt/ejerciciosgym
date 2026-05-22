import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:ejerciciosgym/providers/configuracion_provider.dart';
import 'package:ejerciciosgym/services/ejercicios_service.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class EjerciciosProvider with ChangeNotifier {
  Future<List<EjercicioImagenModel>> cargarEjercicios(
    BuildContext context,
  ) async {
    final provider = Provider.of<ConfiguracionProvider>(context, listen: false);
    final service = EjerciciosService(provider.servidor);

    final ejercicios = await service.getAll();
    final resultados = await Future.wait(
      ejercicios.map((e) async {
        try {
          final imagen = await service.getImage(e.id);
          return EjercicioImagenModel(ejercicio: e, imagen: imagen);
        } catch (_) {
          return null;
        }
      }),
    );
    
    return resultados.whereType<EjercicioImagenModel>().toList();
  }

  Future<List<EjercicioImagenModel>> cargarEjerciciosPorNombre(
    BuildContext context,
    String nombre,
  ) async {
    final provider = Provider.of<ConfiguracionProvider>(context, listen: false);
    final service = EjerciciosService(provider.servidor);

    final ejercicios = await service.getAllByName(nombre);
    final resultados = await Future.wait(
      ejercicios.map((e) async {
        try {
          final imagen = await service.getImage(e.id);
          return EjercicioImagenModel(ejercicio: e, imagen: imagen);
        } catch (_) {
          return null;
        }
      }),
    );

    return resultados.whereType<EjercicioImagenModel>().toList();
  }
}
