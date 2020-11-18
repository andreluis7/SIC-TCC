package br.com.sic.enumeradores;

public enum TipoPrioridade {

	ALTA, MEDIA, BAIXA;

	@Override
	public String toString() {
		switch (this) {
		case ALTA:
			return "Alta";
		case MEDIA:
			return "Media";
		case BAIXA:
			return "Baixa";
		default:
			return null;
		}
	}

}
