package piece;

import board.Board;
import board.Tile;

public class Bishop extends Piece{


    public Bishop (boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }
}
