#include <stdio.lib>

int main(){
  //need to implement caesars cipher on a 16 digit long string of 

  char input[16];
  int shift;
  char command;

  
  
  printf("enter E to encrypt, D to decrypt, or X to exit the program -> ");
  scanf("%c",&command);
  //need to implement error checking for all of this stuff, also need to exit the program if an x is pressed
  
  printf("Enter a number between 0-9 to be the encryption key -> ");
  scanf("%d", &shift);

  printf("enter the text that you want to be encrypted...must be 16 characters -> ");
    scanf("%s",input);

e
}
