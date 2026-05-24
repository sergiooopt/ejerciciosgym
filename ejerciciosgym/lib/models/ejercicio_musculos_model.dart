class EjercicioMusculosModel {
  int idEjercicio;
  int idMusculo;
  String descripcion;
  bool esDirecto;
  int porcentajeActivacion;

  EjercicioMusculosModel({
    required this.idEjercicio,
    required this.idMusculo,
    required this.descripcion,
    required this.esDirecto,
    required this.porcentajeActivacion,
  });

  factory EjercicioMusculosModel.fromMap({required Map<String, dynamic> map}) =>
      EjercicioMusculosModel(
        idEjercicio: map['id_ejercicio'],
        idMusculo: map['id_musculo'],
        descripcion: map['descripcion'],
        esDirecto: map['es_directo'],
        porcentajeActivacion: map['porcentaje_activacion'],
      );

  Map<String, dynamic> toMap() => {
    "id_ejercicio": idEjercicio,
    "id_musculo": idMusculo,
    "descripcion": descripcion,
    "es_directo": esDirecto,
    "porcentaje_activacion": porcentajeActivacion,
  };
}
