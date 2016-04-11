data BookInfo = Book Int String [String]
  deriving (Show)

type CustomerID = String

data BookReview = BookReview BookInfo CustomerID String
  deriving (Show)

data MagazineInfo = Magazine Int String [String]
  deriving (Show)

myInfo = Book 9797 "Algebra" ["Ionel", "Gigel"]

type CardHolder = String
type CardNumber = String
type Address = [String]

data BilingInfo = CreditCard CardNumber CardHolder Address
  | CashOnDelivery
  | Invoice CustomerID
  deriving (Show, Read)


lungime :: (Num b) => [a] -> b
lungime [] = 0
lungime (_:xs) = 1 + lungime xs

display :: BilingInfo -> String
display (CreditCard number owner _) = "This is " ++ number ++ " with owner " ++ owner
display (Invoice customerID) = "This is invoide with customer ID " ++ customerID

type PhoneBook = [(String, String)]

pbook = [("Ionel","123"),("Gigi","50 de bani"), ("Gigi","51 de bani")]
--lookup :: Eq a => a
--  -> [(a,b)]
--  -> Maybe b

-- lookup name pbook = map ( name == (fst) ) pbook
-- sau
-- let f (x:xs) name = if (fst x) == name then snd x else f xs name

-- find recursive
f :: String -> [(String, String)] -> [String]
f _ [] = []
f name (x:xs)
  | fst x == name = snd x : f name xs
  | otherwise = f name xs

-- find with fold
ff :: String -> [(String, String)] -> [String]
ff _ [] = []
ff name book  = foldr (\x acc -> if (fst x) == name then snd x : acc else acc) [] book

-- find using list comprehension
fl :: String -> [(String, String)] -> [String]
fl _ [] = []
fl name xs = [b | (a, b) <- xs , a == name]

zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' _ [] _ = []
zipWith' _ _ [] = []
zipWith' f (x:xs) (y: ys) =  f x y : zipWith' f xs ys
