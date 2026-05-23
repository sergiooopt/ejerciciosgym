import 'dart:convert';
import 'dart:typed_data';

import 'package:ejerciciosgym/models/ejercicio_model.dart';
import 'package:ejerciciosgym/models/ejercicio_musculos_model.dart';
import 'package:ejerciciosgym/models/musculo_model.dart';
import 'package:http/http.dart' as http;

class EjerciciosService {
  final String servidor;

  EjerciciosService(this.servidor);

  Future<List<EjercicioModel>> getAllEjercicios() async {
    final response = await http.get(
      Uri.parse('http://$servidor:8080/wsejerciciosgym/api/ejercicios'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => EjercicioModel.fromMap(map: e)).toList();
    } else {
      throw Exception(response.statusCode);
    }
  }

  Future<List<EjercicioModel>> getAllEjerciciosByName(String nombre) async {
    final response = await http.get(
      Uri.parse(
        'http://$servidor:8080/wsejerciciosgym/api/ejercicios?nombre=$nombre',
      ),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => EjercicioModel.fromMap(map: e)).toList();
    } else {
      throw Exception(response.statusCode);
    }
  }

  Future<Uint8List> getEjercicioImagen(int id) async {
    final response = await http.get(
      Uri.parse(
        'http://$servidor:8080/wsejerciciosgym/api/ejercicios/$id/imagen',
      ),
    );

    if (response.statusCode == 200) {
      final data = response.bodyBytes;
      return data;
    } else {
      throw Exception(response.statusCode);
    }
  }

  Future<MusculoModel> getMusculoById(int id) async {
    final response = await http.get(
      Uri.parse('http://$servidor:8080/wsejerciciosgym/api/musculos/$id'),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return MusculoModel.fromMap(map: data);
    } else {
      throw Exception(response.statusCode);
    }
  }

  Future<List<EjercicioMusculosModel>> getAllMusculosInvolucrados(
    int idEjercicio,
  ) async {
    final response = await http.get(
      Uri.parse(
        'http://$servidor:8080/wsejerciciosgym/api/ejerciciomusculo/$idEjercicio',
      ),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => EjercicioMusculosModel.fromMap(map: e)).toList();
    } else {
      throw Exception(response.statusCode);
    }
  }
}
