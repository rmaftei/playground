import System.IO

main = do
  withFile "lines" ReadMode (\handle -> do
    contents <- hGetContents handle
    putStr contents)
