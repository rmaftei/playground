data Shape = Circle Float Float Float | Rectangle Float Float Float Float

surface :: Shape -> Float
surface (Circle _ _ r) = pi * r ^ 2
surface (Rectangle x1 y1 x2 y2) = (abs $ x2 - x1) * (abs $ y2 - y1)

-- default sintax
data Person = Person String String Int deriving (Show)

firstName :: Person -> String
firstName (Person firstName _ _) = firstName

age :: Person -> Int
age (Person _ _ age) = age

-- record sintax
-- this is genrating the methods for property access
data Persoana = Persoana { pfName :: String,
                           plName :: String,
                           pAge :: Int
                         } deriving (Show)

-- type parameters

data Car a b c = Car { company :: a,
                       model :: b,
                       year :: c
                      } deriving (Show)

tellCar :: (Show a) => Car String String a -> String
tellCar (Car {company = c, model = m, year = y}) = "This " ++ c ++ " " ++ m ++ " was made in " ++ show y

data Vector a = Vector a a a deriving (Show)
vplus :: (Num t) => Vector t -> Vector t -> Vector t
(Vector i j k) `vplus` (Vector l m n) = Vector (i + l) (j + m) (k + n)
