import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:ejerciciosgym/models/ejercicio_musculos_model.dart';
import 'package:ejerciciosgym/models/musculo_model.dart';
import 'package:ejerciciosgym/providers/configuracion_provider.dart';
import 'package:ejerciciosgym/services/ejercicios_service.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class EjerciciosProvider with ChangeNotifier {
  
  // Se recorre lista devuelta por API
  // Si hay algun error se convierte en null, solo se devuelven los objetos deseados gracias a *whereType*

  Future<List<EjercicioImagenModel>> cargarEjercicios(
    BuildContext context,
  ) async {
    final provider = Provider.of<ConfiguracionProvider>(context, listen: false);
    final service = EjerciciosService(provider.servidor);

    final ejercicios = await service.getAllEjercicios();
    final resultados = await Future.wait(
      ejercicios.map((e) async {
        try {
          final imagen = await service.getEjercicioImagen(e.id);
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

    final ejercicios = await service.getAllEjerciciosByName(nombre);
    final resultados = await Future.wait(
      ejercicios.map((e) async {
        try {
          final imagen = await service.getEjercicioImagen(e.id);
          return EjercicioImagenModel(ejercicio: e, imagen: imagen);
        } catch (_) {
          return null;
        }
      }),
    );

    return resultados.whereType<EjercicioImagenModel>().toList();
  }

  Future<MusculoModel> cargarMusculoPorId(BuildContext context, int id) async {
    final provider = Provider.of<ConfiguracionProvider>(context, listen: false);
    final service = EjerciciosService(provider.servidor);
    return await service.getMusculoById(id);
  }

  Future<List<EjercicioMusculosModel>> cargarMusculosInvolucrados(
    BuildContext context,
    int idEjercicio,
  ) async {
    final provider = Provider.of<ConfiguracionProvider>(context, listen: false);
    final service = EjerciciosService(provider.servidor);
    return await service.getAllMusculosInvolucrados(idEjercicio);
  }
}
