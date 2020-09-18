package toker.panel.util;

public enum Languages {
    COMMON(1),
    VAEGIR(2),
    NORDIC(3),
    SARRANID(4),
    KHERGIT(5),
    GEROIAN(6),
    BALIONESE(7),

    DEFAULT(COMMON.getId());

    private final int id;

    Languages(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
