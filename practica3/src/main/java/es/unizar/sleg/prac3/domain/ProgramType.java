package es.unizar.sleg.prac3.domain;

public enum ProgramType {
    NONE,
    ARCADE,
    CONVERSACIONAL,
    ESTRATEGIA,
    JUEGO_DE_MESA,
    S_DEPORTIVO,
    SIMULADOR,
    UTILIDAD,
    VIDEOAVENTURA;

    @Override
    public String toString() {
        switch (this) {
            case NONE: return "---";
            case ARCADE: return "ARCADE";
            case CONVERSACIONAL: return "CONVERSACIONAL";
            case ESTRATEGIA: return "ESTRATEGIA";
            case JUEGO_DE_MESA: return "JUEGO DE MESA";
            case S_DEPORTIVO: return "S. DEPORTIVO";
            case SIMULADOR: return "SIMULADOR";
            case UTILIDAD: return "UTILIDAD";
            case VIDEOAVENTURA: return "VIDEOAVENTURA";
            default: return null;
        }
    }

    public static ProgramType parse(String str) {
        if (str == null) return null;
        switch (str) {
            case "---": return NONE;
            case "ARCADE": return ARCADE;
            case "CONVERSACIONAL": return CONVERSACIONAL;
            case "ESTRATEGIA": return ESTRATEGIA;
            case "JUEGO DE MESA": return JUEGO_DE_MESA;
            case "S. DEPORTIVO": return S_DEPORTIVO;
            case "SIMULADOR": return SIMULADOR;
            case "UTILIDAD": return UTILIDAD;
            case "VIDEOAVENTURA": return VIDEOAVENTURA;
            default: return null;
        }
    }
}
