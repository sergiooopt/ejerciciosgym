import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ConfiguracionProvider with ChangeNotifier {
  static const _claveIpServidor = 'servidor.ip';
  String _ipServidor;

  ConfiguracionProvider({String? ipServidor})
    : _ipServidor = ipServidor ?? '10.0.2.2';

  String get ipServidor => _ipServidor;

  Future<void> setIpServidor(String ipServidor) async {
    _ipServidor = ipServidor;
    final preferences = await SharedPreferences.getInstance();
    await preferences.setString(_claveIpServidor, _ipServidor);
    notifyListeners();
  }
}
