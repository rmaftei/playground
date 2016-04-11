module Chess where

data PColor = White | Black deriving (Show)
data PType = Pawn | Knight | Bishop | Rook | Queen | King deriving (Show)
data Piece = Piece PColor PType deriving (Show)

type Square = Maybe Piece

type Board = [[Square]]

showPiece :: Piece -> Char
showPiece (Piece White Pawn)   = 'P'
showPiece (Piece White Knight) = 'N'
showPiece (Piece White Bishop) = 'B'
showPiece (Piece White Rook)   = 'R'
showPiece (Piece White Queen)  = 'Q'
showPiece (Piece White King)   = 'K'
showPiece (Piece Black Pawn)   = 'p'
showPiece (Piece Black Knight) = 'n'
showPiece (Piece Black Bishop) = 'b'
showPiece (Piece Black Rook)   = 'r'
showPiece (Piece Black Queen)  = 'q'
showPiece (Piece Black King)   = 'k'

readPiece :: Char -> Piece
readPiece 'P' = Piece White Pawn
readPiece 'N' = Piece White Knight
readPiece 'B' = Piece White Bishop
readPiece 'R' = Piece White Rook
readPiece 'Q' = Piece White Queen
readPiece 'K' = Piece White King
readPiece 'p' = Piece Black Pawn
readPiece 'n' = Piece Black Knight
readPiece 'b' = Piece Black Bishop
readPiece 'r' = Piece Black Rook
readPiece 'q' = Piece Black Queen
readPiece 'k' = Piece Black King

