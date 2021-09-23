package game;

import board.Board;
import board.Tile;
import move.Move;
import piece.Piece;
import player.Player;

import java.util.List;

public class Game {
    private Player p1, p2;
    private Board board;
    private Player currentTurn_player;
    private Status status;
    private List<Move> game_moves;

    public void initialize (Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        board.resertBoard();

        if (p1.isWhiteSide()){
            this.currentTurn_player = p1;
        }else{
            this.currentTurn_player = p2;
        }

        game_moves.clear();
    }

    public boolean endGame(){
        return  this.status != Status.ACTIVE;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus (Status status){
        this.status = status;
    }

    public boolean player_move (Player player, int start_x, int start_y, int end_x, int end_y) throws Exception {
        Tile start = board.getTile(start_x, start_y);
        Tile end   = board.getTile(end_x, end_y);
        Move m = new Move(player, start, end);
        return  this.make_move(m, player);
    }

    private boolean make_move (Move m, Player player){
        Piece moved_piece = m.getStart_tile().getPiece();
        if (moved_piece == null)
            return  false;

        //miramos si el jugador esta en su turno
        if (player != currentTurn_player)
            return  false;

        //miramos si es nuestra pieze
        if (moved_piece.isWhite() != player.isWhiteSide())
            return  false;

        //movimiento valido
        if (!moved_piece.canMove(board, m.getStart_tile(), m.getEnd_tile()))
            return false;

        //miramos si nos podemos comer una pieza


        return  true;
    }
}
