class MusculoModel {
  int id;
  String nombre;
  String descripcion;
  String zona;
  String grupo;

  MusculoModel({
    required this.id,
    required this.nombre,
    required this.descripcion,
    required this.zona,
    required this.grupo,
  });

  factory MusculoModel.fromMap({required Map<String, dynamic> map}) =>
      MusculoModel(
        id: map['id_musculo'],
        nombre: map['nombre'],
        descripcion: map['descripcion'],
        zona: map['zona'],
        grupo: map['grupo'],
      );

  Map<String, dynamic> toMap() => {
    'id_musculo': id,
    'nombre': nombre,
    'descripcion': descripcion,
    'zona': zona,
    'grupo': grupo,
  };
}
