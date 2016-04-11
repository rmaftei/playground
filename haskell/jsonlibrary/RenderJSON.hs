module RenderJSON( printJSON ) where

import Data.List (intercalate)
import SimpleJSON


printJSON = putStrLn . renderJValue

renderJValue :: JValue -> String

renderJValue (JString s)   = show s

renderJValue (JNumber n)   = show n

renderJValue (JBool True)  = "true"

renderJValue (JBool False) = "false"

renderJValue JNull       = "null"

renderJValue (JObject o) = "{" ++ pairs o ++ "}"
  where pairs [] = ""
        pairs xs = intercalate "," (map renderPair xs)
        renderPair (k, v) = show k ++ ":" ++ renderJValue v

renderJValue (JArray a) = "[" ++ renderArray a ++ "]"
  where renderArray [] = ""
        renderArray xs = intercalate "," (map renderJValue xs)

