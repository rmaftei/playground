module Main where

import SimpleJSON
import RenderJSON

main = printJSON $ JObject [("Horia", JNumber 23), ("numbers", JArray [JString "510117", JString "212496"])]

