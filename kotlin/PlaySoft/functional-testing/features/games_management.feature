#encoding: utf-8

Feature: Game management
	I can create games
	I can edit games
	I can delete games

	Scenario: Add a game
		When I insert a game with start time "23-01-2015T10:30:00Z" and location "La padure"
		Then I have 1 game
		Then the game has location "La padure"
		Then the game has start time "23-01-2015T10:30:00Z"

