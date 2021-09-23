package piece;

import board.Board;
import board.Tile;

public class Knight extends Piece{

    public Knight (boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }

}
