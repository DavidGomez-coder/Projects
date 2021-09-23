package move;

import board.Tile;
import piece.Piece;
import player.Player;

public class Move {

    private Player player;
    private Tile start_tile;
    private Tile end_tile;
    private Piece moved_piece;
    private Piece killed_piece;
    private boolean castling_move = false;

    public Move (Player player, Tile start_tile, Tile end_tile){
        this.player = player;
        this.start_tile = start_tile;
        this.end_tile = end_tile;
        this.moved_piece = start_tile.getPiece();
        this.killed_piece = end_tile.getPiece();
    }

    public boolean isCastling_move(){
        return this.castling_move;
    }

    public void setCastling_move(boolean castling_move){
        this.castling_move = castling_move;
    }

    public Tile getStart_tile(){
        return this.start_tile;
    }

    public Tile getEnd_tile(){
        return this.end_tile;
    }


}
