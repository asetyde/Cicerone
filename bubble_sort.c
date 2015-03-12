#include <stdio.h>

void bubblesort(int v[], int n) {
int i,k;
int temp;
for(i = 0; i<n-1; i++) {
 for(k = 0; k<n-1-i; k++) {
         if(v[k] > v[k+1]) {
          temp = v[k];
          v[k] = v[k+1];
          v[k+1] = temp;
         }
 }
}
}


void printAll(int array[]){
	int i;
	for(i=0; i < 10; i++){
		printf("array=[%d] = %d\n", i, array[i]);
}
if(i ==10)
printf("\n\n");
}


int main(){
	int array[10];
	int i;
	
	for(i=0; i<10; i++) /*inizialize*/
		array[i] = 10 -i;


	printAll(array);
	bubblesort(array, 10);
	printAll(array);
	
	
	return 0;
}
