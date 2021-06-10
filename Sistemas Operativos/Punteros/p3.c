#include <stdio.h>
#include <string.h>

#define LONGITUD 20

int main()
{
  
  char buffer[LONGITUD];
  char *p_cadena;
  int i, j;

  j = 0;
  for (i=0; i<20; i++)
  {
    ++j;
    if (j>9) j=1; /*Vuelve a 1 tras el 9 */
    buffer[i] = j + 0x30; /* Código ASCII del dígito */

    /* OJO: El buffer no contiene todavía una cadena; no está terminada en 0!! */
   }

   p_cadena = buffer;
   
   buffer[9] = 0;  /* Introducimos el terminador de cadena tras el primer 9 */

   printf("\n");
   printf("La longitud del buffer es %d \n", LONGITUD);
   printf("La cadena más larga que puede albergar este buffer es, por tanto, de %d caracteres \n\n", LONGITUD - 1);
   printf("Iterando con el puntero, el contenido del buffer es =");
   for (i=0; i<20; i++)
   {
     /* Imprimimos en pantalla el contenido del buffer */
     printf("%c",*(p_cadena++));
   }
   printf("\n");
   printf("Iterando sobre el array, el contenido del buffer es =");
   for (i=0; i<20; i++)
   {
     /* Imprimimos en pantalla el contenido del buffer */
     printf("%c", buffer[i]);
   }
   
   printf("\n\n");
   printf("Ahora, intentamos imprimir como cadena el contenido del buffer\n");
   printf("El contenido del buffer es =");
   j = printf("%s", buffer);
   printf("\n");
   printf("printf reporta haber impreso %d caracteres \n\n", j);
 }
