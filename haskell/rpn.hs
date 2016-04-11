import Data.List

solveRPN  :: String -> Float
solveRPN = head . foldl rpnFunction [] . words
  where rpnFunction (x:y:ys) "*" = (x * y):ys
        rpnFunction (x:y:ys) "+" = (x + y):ys
        rpnFunction (x:y:ys) "-" = (y - x):ys
        rpnFunction (x:y:ys) "/" = (y / x):ys
        rpnFunction xs element = read element:xs
