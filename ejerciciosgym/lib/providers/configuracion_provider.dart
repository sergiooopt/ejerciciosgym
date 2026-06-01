import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ConfiguracionProvider with ChangeNotifier {
  static const _claveServidor = 'servidor.ip';
  String _servidor;

  ConfiguracionProvider({String? servidor})
    : _servidor = servidor ?? '10.0.2.2';

  String get servidor => _servidor;

  Future<void> setIpServidor(String servidor) async {
    _servidor = servidor;
    final preferences = await SharedPreferences.getInstance();
    await preferences.setString(_claveServidor, _servidor);
    notifyListeners();
  }
}
