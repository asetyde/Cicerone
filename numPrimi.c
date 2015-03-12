#include <stdio.h>

int check = 8;

void isPrimo(){


	if(check == 2){
		printf("il numero è primo\n");
		return;
	}
	int condition = check /2;
	
	while(condition > 1){
		float temp = check / condition;
		int cast = temp;
		temp = temp - cast;
		if(temp == 0){
		printf("%d non è primo poichè divisibile per %d\n", check, condition);
		return;
		}
		
		
		condition--;
	}

	printf("%d è un numero primo\n", check);

}


int main(){

isPrimo();



return 0;
}
