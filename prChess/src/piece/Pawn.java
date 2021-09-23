package piece;

import board.Board;
import board.Tile;

public class Pawn extends  Piece{

    public Pawn (boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }
}
