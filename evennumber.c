#include <stdio.h>

int evenNumber[10];

void printAll(){
	int i;

	for(i=0; i < 10; i++){
		printf("evenNumber[%d] = %d\n", i,evenNumber[i]);
	}

}


int main(){
	int i;

	for(i=0; i < 10; i++){
		evenNumber[i] = i * 2;
	}
	printAll();

	return 0;
}
