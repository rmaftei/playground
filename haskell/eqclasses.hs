
class BasicEq a where
  isEqual :: a -> a -> Bool

instance BasicEq Bool where
  isEqual True True = True
  isEqual False False = True
  isEqual _ _ = False

data Persoana = Persoana {nume :: String, varsta :: Int} deriving (Show)

class ToString a where
  toString :: a -> String

instance ToString Persoana where
  toString (Persoana a b) = "| " ++ a ++ " " ++ show b ++ " |" 
  
