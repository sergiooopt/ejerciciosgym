import 'dart:typed_data';

import 'package:ejerciciosgym/models/ejercicio_model.dart';
import 'package:ejerciciosgym/services/ejercicios_service.dart';
import 'package:flutter/material.dart';

class EjerciciosProvider with ChangeNotifier {
  List<EjercicioModel> _ejercicios = [];

  List<EjercicioModel> get ejercicios => _ejercicios;

  Future<void> cargar() async {
    _ejercicios = await EjerciciosService.getAll();
    notifyListeners();
  }

  Future<void> cargarPorNombre(String nombre) async {
    _ejercicios = await EjerciciosService.getAllByName(nombre);
    notifyListeners();
  }

  Future<Uint8List> getImagen(int idEjercicio) async {
    return await EjerciciosService.getImage(idEjercicio);
  }
}