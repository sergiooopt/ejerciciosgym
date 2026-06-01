import 'package:ejerciciosgym/core/providers.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  // Cargar ip de servidor
  WidgetsFlutterBinding.ensureInitialized();
  final preferences = await SharedPreferences.getInstance();
  final ip = preferences.getString('servidor.ip');

  // Cargar providers con ip
  runApp(Providers(ipServidor: ip));
}
