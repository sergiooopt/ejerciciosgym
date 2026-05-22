import 'dart:convert';
import 'dart:typed_data';

import 'package:ejerciciosgym/models/ejercicio_model.dart';
import 'package:http/http.dart' as http;

class EjerciciosService {
  final String servidor;

  EjerciciosService(this.servidor);

  Future<List<EjercicioModel>> getAll() async {
    final response = await http.get(
      Uri.parse('http://$servidor:8080/wsejerciciosgym/api/ejercicios'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => EjercicioModel.fromMap(map: e)).toList();
    } else {
      throw Exception(
        "Error al obtener lista de ejercicios, status code ${response.statusCode}",
      );
    }
  }

  Future<List<EjercicioModel>> getAllByName(String nombre) async {
    final response = await http.get(
      Uri.parse(
        'http://$servidor:8080/wsejerciciosgym/api/ejercicios?nombre=$nombre',
      ),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => EjercicioModel.fromMap(map: e)).toList();
    } else {
      throw Exception(
        "Error al obtener lista de ejercicios, status code ${response.statusCode}",
      );
    }
  }

  Future<Uint8List> getImage(int id) async {
    final response = await http.get(
      Uri.parse(
        'http://$servidor:8080/wsejerciciosgym/api/ejercicios/$id/imagen',
      ),
    );

    if (response.statusCode == 200) {
      final data = response.bodyBytes;
      return data;
    } else {
      throw Exception(
        "Error al obtener la imagen del ejercicio, status code ${response.statusCode}",
      );
    }
  }
}
