package piece;

import board.Board;
import board.Tile;

public class Queen extends Piece{

    public Queen (boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean canMove(Board board, Tile start, Tile end) {
        return true;
    }
}
