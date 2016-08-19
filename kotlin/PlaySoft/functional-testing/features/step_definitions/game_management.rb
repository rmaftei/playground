#encoding: utf-8

require 'rest-client'
require 'json'
require 'rspec'
:

When(/^I access authenticate as "([^\"]*)\/([^\"]*)"$/) do |username, password|
	begin
	    response = RestClient.get "http://localhost:8080/v1/games"
	    expect(response.code).to eq(200)
	rescue RestClient::InternalServerError => e
        STDERR.puts (e.methods)
        throw e
    end
end

When(/^I insert a game with start time "([^"]*)" and location "([^"]*)"$/) do |arg1, arg2|
	begin
	    response = RestClient.get "http://localhost:8080/v1/games"
	    expect(response.code).to eq(200)
	rescue RestClient::InternalServerError => e
        STDERR.puts (e.methods)
        throw e
    end
end

Then(/^I have (\d+) game$/) do |arg1|
	expect(true).to eq(false)
end

Then(/^the game has location "([^"]*)"$/) do |arg1|
	expect(true).to eq(false)
end

Then(/^the game has start time "([^"]*)"$/) do |arg1|
	expect(true).to eq(false)
end

