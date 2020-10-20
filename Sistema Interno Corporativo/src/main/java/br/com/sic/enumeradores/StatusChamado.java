package br.com.sic.enumeradores;

public enum StatusChamado {

	ABERTO, ANDAMENTO, FECHADO;

	@Override
	public String toString() {
		switch (this) {
		case ABERTO:
			return "Aberto";
		case ANDAMENTO:
			return "Andamento";
		case FECHADO:
			return "Fechado";
		default:
			return null;
		}
	}

}
