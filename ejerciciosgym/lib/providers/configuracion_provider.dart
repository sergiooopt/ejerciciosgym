import 'package:flutter/material.dart';

class ConfiguracionProvider with ChangeNotifier {
  String _servidor = '10.0.2.2';

  String get servidor => _servidor;

  void setServidor(String servidor) {
    _servidor = servidor;
    notifyListeners();
  }
}