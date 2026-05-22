import 'dart:typed_data';

import 'package:ejerciciosgym/models/ejercicio_model.dart';

class EjercicioImagenModel {
  EjercicioModel ejercicio;
  Uint8List imagen;

  EjercicioImagenModel({required this.ejercicio, required this.imagen});
}
