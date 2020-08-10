package br.com.sic.enumeradores;

public enum TipoUsuario {

	ADMINISTRADOR, FUNCIONARIO, SUPORTE;

	@Override
	public String toString() {
		switch (this) {
		case ADMINISTRADOR:
			return "Administrador";
		case FUNCIONARIO:
			return "Funcionario";
		case SUPORTE:
			return "Suporte";
		default:
			return null;
		}
	}

}
