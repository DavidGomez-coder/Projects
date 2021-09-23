package piece;

import board.Board;
import board.Tile;

public class King extends  Piece{

    public King (boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }
}
