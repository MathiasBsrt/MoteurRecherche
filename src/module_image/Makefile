indexation : pile_dynamique.c pile_dynamique.h descripteur.c descripteur.h controle_descripteur.c controle_descripteur.h
	gcc -c -Wall pile_dynamique.c -lm -g
	gcc -c -Wall descripteur.c -lm -g
	gcc -c -Wall controle_descripteur.c -lm -g
	gcc -o indexation.out pile_dynamique.o controle_descripteur.o descripteur.o

test : pile_dynamique.c pile_dynamique.h descripteur.c descripteur.h controle_descripteur.c controle_descripteur.h test.c
	gcc -c -Wall pile_dynamique.c -lm -g
	gcc -c -Wall descripteur.c -lm -g
	gcc -c -Wall controle_descripteur.c -lm -g
	gcc -c -Wall test.c -lm
	gcc -o test.out pile_dynamique.o controle_descripteur.o descripteur.o test.o

