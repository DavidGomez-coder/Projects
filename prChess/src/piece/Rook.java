package piece;

import board.Board;
import board.Tile;

public class Rook extends Piece{

    public Rook(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }
}
