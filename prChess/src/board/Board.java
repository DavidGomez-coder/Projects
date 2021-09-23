package board;


import piece.*;

/**
 *  REPRESENTACION DEL TABLERO
 *
 *  Piezas blancas -> filas 0 y 1
 *  Piezas negras  -> filas 6 y 7
 *
 *    0    1   2   3   4   5  6   7
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  0
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  1
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  2
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  3
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  4
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  5
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  6
 *  ---------------------------------
 *  |   |   |   |   |   |   |   |   |  7
 *  ---------------------------------
 */
public class Board {

    Tile [][] board;

    public Board (){
        this.resertBoard();
    }

    public Tile getTile(int x, int y) throws Exception {
        //comprobamos si la casilla es correcta
        if (x<0 ||x>7 || y<0 || y>7)
            throw new Exception(x + "-" + y + " is an illegal tile");
        return board[x][y];
    }

    public void resertBoard(){
        //inicializamos las piezas blancas
        board[0][0] = new Tile(0,0, new Rook(true));
        board[0][1] = new Tile(0,1, new Knight(true));
        board[0][2] = new Tile(0,2, new Bishop(true));
        board[0][3] = new Tile(0,3, new King(true));
        board[0][4] = new Tile(0,4, new Queen(true));
        board[0][5] = new Tile(0,5, new Bishop(true));
        board[0][6] = new Tile(0,6, new Knight(true));
        board[0][7] = new Tile(0,7, new Rook(true));

        for (int j=0;j<8;j++){
            board[1][j] = new Tile(1, j, new Pawn(true));
        }

        //inicializamos las piezas negras
        board[7][0] = new Tile(7,0, new Rook(false));
        board[7][1] = new Tile(7,1, new Knight(false));
        board[7][2] = new Tile(7,2, new Bishop(false));
        board[7][3] = new Tile(7,3, new King(false));
        board[7][4] = new Tile(7,4, new Queen(false));
        board[7][5] = new Tile(7,5, new Bishop(false));
        board[7][6] = new Tile(7,6, new Knight(false));
        board[7][7] = new Tile(7,7, new Rook(false));

        for (int j = 0; j<8; j++){
            board[6][j] = new Tile(6, j, new Pawn(false));
        }

        //resto de casillas
        for (int i=2;i<6;i++){
            for (int j=0;j<8;j++){
                board[i][j] = new Tile(i, j, null);
            }
        }
    }
}
