package Cells;

public class Cell {
    public int x;
    public int y;
    public CellEnum type;
    public CellElement element;
    public boolean visited;

    public Cell(int x, int y, CellEnum type, CellElement element, boolean visited) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.element = element;
        this.visited = visited;
    }
}
