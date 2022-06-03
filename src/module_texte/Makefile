test1 : mot/mot.c pile/pile_dynamique.c xml_tokenizer.c Header.h
	gcc -c mot/mot.c
	gcc -c pile/pile_dynamique.c
	gcc -c xml_tokenizer.c
	gcc -o test1.out mot.o pile_dynamique.o xml_tokenizer.o
	rm -f *.o core
	./test1.out

test2 : xml_cleaner.c Header.h
	gcc -c xml_cleaner.c 
	gcc -o test2.out xml_cleaner.o
	rm -f *.o core
	./test2.out
	
test3 : MOT/mot.c index/index.c pile/pile_dynamique.c outils.c xml_cleaner.c xml_tokenizer.c xml_index.c Header.h
	gcc -c -g MOT/mot.c
	gcc -c -g index/index.c
	gcc -c -g pile/pile_dynamique.c
	gcc -c -g outils.c
	gcc -c -g xml_cleaner.c
	gcc -c -g xml_tokenizer.c
	gcc -c -g xml_index.c 
	gcc -o test3.out mot.o index.o pile_dynamique.o outils.o xml_cleaner.o xml_tokenizer.o xml_index.o
	rm -f *.o core
	./test3.out

recherche : recherche.c MOT/mot.c index/index.c pile/pile_dynamique.c outils.c xml_cleaner.c xml_tokenizer.c xml_index.c Header.h
	gcc -c -g -Wall MOT/mot.c
	gcc -c -g -Wall index/index.c
	gcc -c -g -Wall pile/pile_dynamique.c
	gcc -c -g -Wall outils.c
	gcc -c -g -Wall xml_cleaner.c
	gcc -c -g -Wall xml_tokenizer.c
	gcc -c -g -Wall xml_index.c 
	gcc -c -g -Wall recherche.c
	gcc -o recherche.out mot.o index.o pile_dynamique.o outils.o xml_cleaner.o xml_tokenizer.o xml_index.o recherche.o
	rm -f *.o core

clean:
	rm -f *.out core

