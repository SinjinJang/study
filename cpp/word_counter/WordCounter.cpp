/*
    WordCounter - to count characters, words, lines and so on.
    Copyright (C) 2013  Sinjin Jang <secky2@hanmail.net>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

#include <iostream>
#include <fstream>
#include <cassert>

#include "WordCounter.h"

WordCounter::WordCounter()
{
	parse_result.char_count = 0;
	parse_result.word_count = 0;
	parse_result.line_count = 0;
	
	std::cout << "생성자" << std::endl;
}

WordCounter::WordCounter(const WordCounter& other)
{
 	parse_result.char_count = other.parse_result.char_count;
 	parse_result.word_count = other.parse_result.word_count;
 	parse_result.line_count = other.parse_result.line_count;
	
	std::cout << "복사 생성자" << std::endl;
}

WordCounter::~WordCounter()
{
	// Nothing to do
}

WordCounter& WordCounter::operator=(const WordCounter& other)
{
	parse_result.char_count = other.parse_result.char_count;
	parse_result.word_count = other.parse_result.word_count;
	parse_result.line_count = other.parse_result.line_count;

	std::cout << "대입 연산자" << std::endl;
	return *this;
}

bool WordCounter::parse(char* fileName) {
	assert(NULL != fileName);
	
	input.open(fileName, std::ifstream::in);

	char c = input.get();

	while (input.good()) {
		//std::cout << c;
		c = input.get();
		
		// Increase character counter.
		parse_result.char_count++;
		
		// Increase word counter;
		// TODO : Improve to check multiple spaces between words.
		if(' ' == c || '\n' == c) {
			parse_result.word_count++;
		}

		// Increase line counter.
		if('\n' == c) {
			parse_result.line_count++;
		}
	}

	input.close();
	
}

void WordCounter::showResult()
{
	std::cout << "========================================" << std::endl;
	std::cout << "Charactors\t: " << parse_result.char_count << std::endl;
	std::cout << "Word\t\t: " << parse_result.word_count << std::endl;
	std::cout << "Line\t\t: " << parse_result.line_count << std::endl;
	std::cout << "========================================" << std::endl;
}

