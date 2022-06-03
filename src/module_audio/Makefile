test_audio : histogramme.c histogramme.h wav_file_helper.c wav_file_helper.h descripteur.c descripteur.h pile_dynamique.h pile_dynamique.c base_descripteur.h base_descripteur.c test_audio.c
	gcc -c histogramme.c -W -Wall
	gcc -c wav_file_helper.c -W -Wall
	gcc -c descripteur.c -W -Wall
	gcc -c base_descripteur.c -W -Wall
	gcc -c pile_dynamique.c -W -Wall
	gcc -c test_audio.c -W -Wall
	gcc -o test_audio.out histogramme.o wav_file_helper.o descripteur.o pile_dynamique.o base_descripteur.o test_audio.o -lm -W -Wall
	#./test_audio.out

main_histo : histogramme.c histogramme.h main_histo.c
	gcc -c histogramme.c
	gcc -c main_histo.c
	gcc -o main_histo.out histogramme.o main_histo.o -lm

test_wav_helper : wav_file_helper.c wav_file_helper.h test_wav_helper.c
	gcc -c wav_file_helper.c
	gcc -c test_wav_helper.c
	gcc -o test_wav_helper.out wav_file_helper.o test_wav_helper.o
	./test_wav_helper.out

test_evaluation : histogramme.c histogramme.h wav_file_helper.c wav_file_helper.h descripteur.c descripteur.h pile_dynamique.h pile_dynamique.c base_descripteur.h base_descripteur.c test_evaluation.c
	gcc -c histogramme.c
	gcc -c wav_file_helper.c
	gcc -c descripteur.c
	gcc -c base_descripteur.c
	gcc -c pile_dynamique.c
	gcc -c test_evaluation.c
	gcc -o test_evaluation.out histogramme.o wav_file_helper.o descripteur.o pile_dynamique.o base_descripteur.o test_evaluation.o -lm
	./test_evaluation.out

test_recherche : histogramme.c histogramme.h wav_file_helper.c wav_file_helper.h descripteur.c descripteur.h pile_dynamique.h pile_dynamique.c base_descripteur.h base_descripteur.c test_recherche.c
	gcc -c histogramme.c
	gcc -c wav_file_helper.c
	gcc -c descripteur.c
	gcc -c base_descripteur.c
	gcc -c pile_dynamique.c
	gcc -c test_recherche.c
	gcc -o test_recherche.out histogramme.o wav_file_helper.o descripteur.o pile_dynamique.o base_descripteur.o test_recherche.o -lm
	./test_recherche.out TEST_SON/jingle_fi.wav

everything : histogramme.c histogramme.h wav_file_helper.c wav_file_helper.h descripteur.c descripteur.h pile_dynamique.h pile_dynamique.c base_descripteur.h base_descripteur.c
	gcc -c histogramme.c -W -Wall
	gcc -c wav_file_helper.c -W -Wall
	gcc -c descripteur.c -W -Wall
	gcc -c base_descripteur.c -W -Wall
	gcc -c pile_dynamique.c -W -Wall

clean:
	rm -f *.o *.out
	