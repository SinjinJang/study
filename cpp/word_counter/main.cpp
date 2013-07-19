#include <iostream>
#include <fstream>
#include <cstdlib>

#include "WordCounter.h"

void usages(int argc, char **argv) {
	std::cout << "Usages : " << argv[0] << " [fileName to check] " << std::endl;
	exit(EXIT_SUCCESS);
}

void print(WordCounter wc) {
	wc.showResult();
}

void print2(WordCounter & wc) {
	wc.showResult();
}

int main(int argc, char **argv) {
	if(argc != 2 || NULL == argv[1]) {
		usages(argc, argv);
	}

	std::cout << " << Word Counter : " << argv[1] << " >>" << std::endl;
	
	
	/*
	// 기본 사용
	WordCounter wc;
	wc.parse(argv[1]);
	wc.showResult();
	*/
	
	// 대입연산자 체크
	WordCounter wc;
	WordCounter wc_temp;
	wc.parse(argv[1]);
	wc_temp = wc;
	wc_temp.showResult();
	
	// 복사생성자 체크
	print(wc_temp);
	
	// 참조로 넘김.
	print2(wc);
    
	return 0;
}
