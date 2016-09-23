-- check in list if already exists

--type Name = String
--type Description = String

--type Category = (Name, Description)

import Data.List
import Data.Maybe

data Category = Category {
    name:: String,
    description:: String
} deriving (Show, Eq)

type Categories = [Category]

categories = [
    Category "t1" "d1", 
    Category "t2" "d2", 
    Category "t3" "d3"
    ]


nume :: Category -> String
nume = name

validToUpdate :: Category -> Category -> Categories -> Bool
validToUpdate _ _ [] = False

validToUpdate oldCategory category categories
    | name oldCategory == name category = isJust (Just category)
    | otherwise = isNothing $ find (\c -> name c == name category) categories 
