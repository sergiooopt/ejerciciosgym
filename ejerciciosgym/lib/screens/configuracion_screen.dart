import 'package:ejerciciosgym/providers/configuracion_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ConfiguracionScreen extends StatefulWidget {
  const ConfiguracionScreen({super.key});

  @override
  State<ConfiguracionScreen> createState() => _ConfiguracionScreenState();
}

class _ConfiguracionScreenState extends State<ConfiguracionScreen> {
  final _formKey = GlobalKey<FormState>();
  String _servidor = '';

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<ConfiguracionProvider>(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Configuración')),
      body: Form(
        key: _formKey,
        child: ListView(
          children: [
            SizedBox(height: 20),
            Padding(
              padding: const EdgeInsets.only(right: 8, left: 8),
              child: TextFormField(
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  hintText: 'Dirección IP del servidor',
                  helperText: 'IP actual ${provider.ipServidor}',
                  suffixIcon: Icon(Icons.computer_sharp),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                ),
                onChanged: (value) => setState(() => _servidor = value),
                validator: (value) {
                  final regexp = RegExp(
                    '^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}\$',
                  );
                  if (!regexp.hasMatch(value as String)) return 'El servidor debe ser una IP';
                  return null;
                },
              ),
            ),
            SizedBox(height: 20),
            Padding(
              padding: const EdgeInsets.only(right: 16, left: 16),
              child: ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    provider.setIpServidor(_servidor);
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(
                        content: Text('Servidor guardado correctamente'),
                      ),
                    );
                  }
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.blue,
                  foregroundColor: Colors.white,
                ),
                child: Text('Guardar'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
